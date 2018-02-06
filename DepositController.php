<?php

namespace App\Http\Controllers;

use App\Deposit;
use App\User;
use Illuminate\Http\Request;
use App\Http\Requests\DepositRequest;
use App\Http\Requests\PasswordRequest;
use App\Http\Resources\DepositResource;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;

class DepositController extends Controller
{

    private $nominal;
    private $password;

    public function __construct()
    {
      $this->middleware('auth:api');
    }

    public function hasAuth()
    {
      return Auth::guard()->user()->username;
    }

    public function setNominal($nominal)
    {
      return $this->nominal = $nominal;
    }

    public function getNominal()
    {
      return $this->nominal;
    }

    public function setPassword($password)
    {
      return $this->password = $password;
    }

    public function getPassword()
    {
      return $this->password;
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

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
      if (!Auth::guard()->user()->isAdmin) {
        return response()->json(['info' => 'method not allowed'], 403);
      }

      return DepositResource::collection(Deposit::all());
    }

    public function validInvoice($invoice)
    {
      return Deposit::where('invoice', $invoice)
      ->count() > 1 ? $invoice : $invoice = 'DP-'.str_random(4);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(DepositRequest $request, Deposit $deposit, PasswordRequest $password)
    {
      $deposit->invoice = $this->validInvoice('DP-'.str_random(4));
      $deposit->username = $this->hasAuth();
      $deposit->nominal = $request->input('nominal');

      $this->setPassword($password->input('password'));

      if(!$this->checkPassword()){
          return response()->json(['info' => 'Password anda salah mohon ulangi'], 422);
      }else{
          $this->setNominal($request->input('nominal'));
          $this->updateSaldoUser();

          return $deposit->save() ? new DepositResource ($deposit) : response()->json(['info' => 'deposit on pending'], 500);
      }
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Deposit  $deposit
     * @return \Illuminate\Http\Response
     */
    public function show($invoice)
    {
      return new DepositResource(Deposit::where('invoice', $invoice)
      ->where('username', $this->hasAuth())
      ->first());
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Deposit  $deposit
     * @return \Illuminate\Http\Response
     */
    public function updateSaldo()
    {
      $user = new User;
      $update = $user::where('username', $this->hasAuth())->first();
      $update->saldo += $this->getNominal();

      return $update->save();
    }

    public function updateSaldoUser()
    {
      return $this->updateSaldo();
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Deposit  $deposit
     * @return \Illuminate\Http\Response
     */
    public function destroy($invoice)
    {
      $deposit = Deposit::where('invoice', $invoice)
      ->where('username', $this->hasAuth())
      ->first();

      if (!$deposit) return response()->json(['info' => 'invoice not found'], 404);

      return $deposit->delete() ? response()->json(['info' => 'invoice deposit: '.$invoice.' deleted'], 200) : response()->json(['info' => 'failed to delete deposit'], 406);
    }
}
