<?php

namespace App\Http\Controllers;

use App\Barang;
use App\TransactBarang;
use App\DetailTransactBarang;
use Illuminate\Http\Request;
use App\Http\Resources\DetailTransactBarangResource;

class DetailTransactBarangController extends Controller
{

    private $invoice;

    public function __construct()
    {
      $this->middleware('auth:api');
    }

    public function hasAuth()
    {
      return Auth::guard()->user()->username;
    }

    public function getInvoice()
    {
      return $this->invoice;
    }

    public function setInvoice($invoice)
    {
      $this->invoice = $invoice;
      return $this->invoice;
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return DetailTransactBarangResource::collection(DetailTransactBarang::all());
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request, DetailTransactBarang $detailTransactBarang)
    {
      $barang = Barang::where('kode_barang',$request->input('kode_barang'))->first();

      $detailTransactBarang->invoice = $request->input('invoice');
      $detailTransactBarang->kode_barang = $barang->kode_barang;
      $detailTransactBarang->harga_barang = $barang->harga_barang;

      $this->setInvoice($request->input('invoice'));
      $this->updateTotalHargaBelanja(); // on transact barang

      if ($detailTransactBarang->save()) return new DetailTransactBarangResource($detailTransactBarang);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\DetailTransactBarang  $detailTransactBarang
     * @return \Illuminate\Http\Response
     */
    public function show($invoice)
    {
      return DetailTransactBarangResource::collection(DetailTransactBarang::where('invoice',$invoice)->get());
    }

    /**
     *
     * @return  Update Trasact Barang on total_harga
    */
    public function updateTotalHargaBelanja()
    {
      $invoice = $this->getInvoice();

      return $this->_hitungTotalHarga($invoice);
    }

    public function _hitungTotalHarga($invoice)
    {
      $total = DetailTransactBarang::where('invoice', $invoice)
                                    ->sum('harga_barang');

      $update = TransactBarang::where('invoice', $invoice)
                                ->update(['total_harga' => $total]);

      return $update;
    }

}
