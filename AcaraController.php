<?php

namespace App\Http\Controllers;

use App\Acara;
use Illuminate\Http\Request;
use App\Http\Resources\AcaraResource;

class AcaraController extends Controller
{

  public function __construct()
  {
    $this->middleware('auth:api');
  }
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return AcaraResource::collection(Acara::all());
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request, Acara $acara)
    {

      $acara->nama_acara = $request->input('nama_acara');
      $acara->token = str_random(6);
      $acara->penyelengara = $request->input('ukm');
      $acara->kategori_acara = $request->input('kategori');
      $acara->harga_acara = $request->input('harga');

      if ($acara->save()) return new AcaraResource($acara);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Acara  $acara
     * @return \Illuminate\Http\Response
     */
    public function show(Acara $acara, $kategori, $token)
    {
        $show = $acara::where('token',$token)
        ->where('kategori_acara', $kategori)
        ->first();
        
        return (!$show) ? response()->json(['info' => 'Token tidak terdaftar'], 404) : new AcaraResource($show);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Acara  $acara
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request)
    {
        $acara = Acara::where('token',$request->input('token'))->first();

        $acara->nama_acara = $request->input('nama_acara') ?? $acara->nama_acara;
        $acara->penyelengara = $request->input('ukm') ?? $acara->penyelengara;
        $acara->kategori_acara = $request->input('kategori') ?? $acara->kategori_acara;
        $acara->harga_acara = $request->input('harga') ?? $acara->harga_acara;

        if ($acara->save()) return new AcaraResource($acara);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Acara  $acara
     * @return \Illuminate\Http\Response
     */
    public function destroy($token)
    {
        $acara = Acara::where('token',$request->token)->first();

        if ($acara->delete()) return new AcaraResource($acara);
    }
}
