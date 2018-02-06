<?php

namespace App\Http\Controllers;

use App\TransactUKM;
use App\Acara;
use App\User;
use Illuminate\Http\Request;
use App\Http\Requests\TransactUKMRequest;
use App\Http\Resources\TransactUKMResource;
use App\Http\Requests\PasswordRequest;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;

class TransactUKMController extends Controller
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
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
      return TransactUKMResource::collection(TransactUKM::where('username', $this->hasAuth())->get());
    }

    public function show($invoice)
    {
        $transact = TransactUKM::where('invoice',$invoice)
        ->where('username', $this->hasAuth())
        ->first();

        if (!$transact) {
          return response()->json(['status' => 'no record found'], 404);
        }

        return new TransactUKMResource($transact);
    }

    public function validInvoice($invoice)
    {
      return TransactUKM::where('invoice', $invoice)
            ->count() > 1 ? $invoice : $invoice = 'UKM-'.str_random(4);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function doTransactOnUkm(TransactUKMRequest $request, PasswordRequest $password, TransactUKM $transact)
    {
      $acara = Acara::where('token', $request->input('token'))->first();

      $this->setPassword($password->input('password'));

      if(Auth::guard()->user()->saldo < $acara->harga_acara){
        return response()->json(['info' => 'Saldo anda tidak cukup'], 422);
      }
      elseif(!$this->checkPassword()){
        return response()->json(['info' => 'Password anda salah mohon ulangi'], 422);
      }else
      {
        $transact = $acara->transact_ukm()->create([
          'invoice' => $this->validInvoice('UKM-'.str_random(4)),
          'token_acara' => $acara->token,
          'username' => $this->hasAuth(),
          'total_bayar' => $acara->harga_acara
        ]);

        $this->setHarga($acara->harga_acara);
        $this->updateSaldoUser();

        return $transact ?  new TransactUKMResource($transact) : response()->json(['info' => 'pembayaran terjadi pending'], 500);

      }

    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\TransactUKM  $transactUKM
     * @return \Illuminate\Http\Response
     */
    public function destroy($invoice)
    {
        $transact = TransactUKM::where('invoice',$invoice)
        ->where('username', $this->hasAuth())
        ->first();

        if ($transact->delete()) return new TransactUKMResource($transact);
    }
}
