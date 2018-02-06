<?php

namespace App\Http\Controllers;

use App\KategoriAcara;
use Illuminate\Http\Request;
use App\Http\Resources\KategoriAcaraResource;

class KategoriAcaraController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return KategoriAcaraResource::collection(KategoriAcara::all());
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\KategoriAcara  $kategoriAcara
     * @return \Illuminate\Http\Response
     */
    public function show(KategoriAcara $kategoriAcara, $nama)
    {
        return new KategoriAcaraResource($kategoriAcara::where('nama',$nama)->first());
    }
    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request, KategoriAcara $kategoriAcara)
    {
      $kategoriAcara->nama = str_slug($request->input('nama'));
      $kategoriAcara->informasi = "UKM ".$request->input('nama');

      if ($kategoriAcara->save()) return new KategoriAcaraResource($kategoriAcara);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\KategoriAcara  $kategoriAcara
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request)
    {
      $kategori = KategoriAcara::where('nama', $request->nama)->first();

      $kategori->nama = $request->input('nama');
      $kategori->informasi = $request->input('informasi');

      if ($kategori->save()) return new KategoriUserResource($kategori);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\KategoriAcara  $kategoriAcara
     * @return \Illuminate\Http\Response
     */
    public function destroy($nama)
    {
      $kategori = KategoriAcara::where('nama', $request->nama)->first();

      if ($kategori->delete()) return new KategoriUserResource($kategori);
    }
}
