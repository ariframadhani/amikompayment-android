<?php

namespace App\Http\Controllers;

use App\TransactBarang;
use App\Barang;
use Illuminate\Http\Request;
use App\Http\Resources\TransactBarangResource;
use Illuminate\Support\Facades\Auth;

class TransactBarangController extends Controller
{

    private $invoice;

    public function getInvoice()
    {
      return $this->invoice;
    }

    public function setInvooice($invoice)
    {
      return $this->invoice = $invoice;
    }

    public function __construct()
    {
      $this->middleware('auth:api');
    }

    public function hasAuth()
    {
      return Auth::guard()->user()->username;
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return TransactBarangResource::collection(TransactBarang::where('username', $this->hasAuth())->get());
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request, TransactBarang $transactBarang)
    {
      $transactBarang->invoice = $request->input('invoice');
      $transactBarang->username = $this->hasAuth();

      if ($transactBarang->save()) return new TransactBarangResource($transactBarang);

    }

    /**
     * Display the specified resource.
     *
     * @param  \App\TransactBarang  $transactBarang
     * @return \Illuminate\Http\Response
     */
     public function show($kategori, $invoice)
     {

       if ($kategori == "citra-mart") {
        $status = "=";
        return $this->byKategori($invoice, $status);

       }else if($kategori == "kantin"){
         $status = "!=";
         return $this->byKategori($invoice, $status);
       }

    }

    public function byKategori($invoice, $equal)
    {
      $transact = TransactBarang::where('invoice', $invoice)
                 ->where('username', $equal, 'citraMart')
                 ->first();

     return !$transact ? response()->json(['status' => 'no record found'], 404) : new TransactBarangResource($transact);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\TransactBarang  $transactBarang
     * @return \Illuminate\Http\Response
     */
    public function destroy(TransactBarang $transactBarang)
    {
      $transact = TransactBarang::where('invoice', $invoice)
      ->where('username', $this->hasAuth())
      ->first();

      if (!$transact->delete()) {
        return response()->json(['status'=>'error while deleting record'], 404);
      }

      return new TransactBarangResource($transact);
    }
}
