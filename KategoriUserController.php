<?php

namespace App\Http\Controllers;

use App\KategoriUser;
use Illuminate\Http\Request;
use App\Http\Resources\KategoriUserResource;

class KategoriUserController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return KategoriUserResource::collection(KategoriUser::all());
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\KategoriUser  $kategoriUser
     * @return \Illuminate\Http\Response
     */
    public function show(KategoriUser $kategoriUser, $nama)
    {
        return new KategoriUserResource($kategoriUser::where('nama',$nama)->first());
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request, KategoriUser $kategori)
    {

      $kategori->nama = str_slug($request->input('nama'));
      $kategori->informasi = $request->input('nama');

      if ($kategori->save()) return new KategoriUserResource($kategori);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\KategoriUser  $kategoriUser
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request)
    {
      $kategori = KategoriUser::where('nama', $request->nama)->first();

      $kategori->nama = $request->input('nama');
      $kategori->informasi = $request->input('informasi');

      if ($kategori->save()) return new KategoriUserResource($kategori);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\KategoriUser  $kategoriUser
     * @return \Illuminate\Http\Response
     */
    public function destroy($nama)
    {
        $kategori = KategoriUser::where('nama', $request->nama)->first();

        if ($kategori->delete()) return new KategoriUserResource($kategori);
    }
}
