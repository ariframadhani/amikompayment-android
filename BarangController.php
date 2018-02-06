<?php

namespace App\Http\Controllers;

use App\Barang;
use App\User;
use Illuminate\Http\Request;
use App\Http\Resources\BarangResource;

class BarangController extends Controller
{

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
      return BarangResource::collection(Barang::all());
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Barang  $barang
     * @return \Illuminate\Http\Response
     */
    public function show($kode)
    {
        return new BarangResource(Barang::where('kode_barang',$kode)->first());
    }


    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request, Barang $barang)
    {
      $user_food = User::where('username', $request->input('user'))
                        ->where('kategori_user', 'makanan-minuman')->first();

      $barang->username = $user_food->username;
      $barang->nama_barang = $request->input('nama_barang');
      $barang->kode_barang = str_random(8);
      $barang->harga_barang = $request->input('harga');

      if ($barang->save()) return new BarangResource($barang);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Barang  $barang
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request)
    {
      $barang = Barang::where('kode_barang', $request->input('kode'));

      $barang->nama_barang = $request->input('nama') ?? $barang->nama_barang;
      $barang->harga_barang = $request->input('harga') ?? $barang->harga_barang;

      if ($barang->save()) return new BarangResource($barang);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Barang  $barang
     * @return \Illuminate\Http\Response
     */
    public function destroy(Barang $barang)
    {
      $barang = Barang::where('kode_barang', $request->input('kode'));

      if ($barang->delete()) return new BarangResource($barang);

    }
}
