<?php

namespace App\Http\Controllers;

use App\User;
use App\TransactBarang;
use App\PaymentBarang;
use Illuminate\Http\Request;
use App\Http\Resources\PaymentBarangResource;
use App\Http\Requests\PasswordRequest;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;

class PaymentBarangController extends Controller
{

    private $password;
    private $harga;

    public function __construct()
    {
      $this->middleware('auth:api');
    }

    public function setHarga($harga)
    {
      return $this->harga = $harga;
    }

    public function getHarga()
    {
      return $this->harga;
    }

    public function setPassword($password)
    {
      return $this->password = $password;
    }

    public function getPassword()
    {
      return $this->password;
    }

    public function hasAuth()
    {
      return Auth::guard()->user()->username;
    }

    public function checkPasswordBeforeTransaction($password)
    {
      $password = $this->getPassword();
      $hashedPassword = Auth::guard()->user()->password;

      return Hash::check($password, $hashedPassword);

    }

    public function checkPassword()
    {
      return $this->checkPasswordBeforeTransaction($this->getPassword());
    }

    public function _updateSaldoUser($harga)
    {
      $user = User::where('username', $this->hasAuth())->first();
      $user->saldo -= $harga;

      return $user->save();
    }

    public function updateSaldoUser()
    {
      return $this->_updateSaldoUser($this->getHarga());
    }

    public function validInvoice($invoice)
    {
      return PaymentBarang::where('invoice', $invoice)
            ->count() > 1 ? $invoice : $invoice = 'BMM-'.str_random(4);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request, PaymentBarang $paymentBarang, PasswordRequest $password)
    {
      $trBarang = TransactBarang::where('invoice', $request->input('invoice'))->first();
      $this->setPassword($password->input('password'));

      if(Auth::guard()->user()->saldo < $trBarang->total_harga){
        return response()->json(['info' => 'Saldo anda tidak cukup'], 422);

      }elseif(!$this->checkPassword()){
        return response()->json(['info' => 'Password anda salah mohon ulangi'], 422);

      }else{

        $paymentBarang->invoice = $this->validInvoice("BMM-".str_random(4));
        $paymentBarang->invoice_tr_barang = $trBarang->invoice;
        $paymentBarang->username = $this->hasAuth();
        $paymentBarang->total_bayar = $trBarang->total_harga;

        $this->setHarga($trBarang->total_harga);
        $this->updateSaldoUser();

        return $paymentBarang->save() ? new PaymentBarangResource($paymentBarang) : response()->json(['info' => 'Pembayaran sedang terjadi pending'], 500);
      }

    }

    /**
     * Display the specified resource.
     *
     * @param  \App\PaymentBarang  $paymentBarang
     * @return \Illuminate\Http\Response
     */
    public function show($invoice, PaymentBarang $paymentBarang)
    {
        $payment = $paymentBarang::where('invoice', $invoice)
        ->where('username', $this->hasAuth())
        ->first();

        return $payment ? new PaymentBarangResource($payment) : response()->json(['status' => 'no record found'], 404);

    }
}
