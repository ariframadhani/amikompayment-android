<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\Resource;

class DetailTransactBarangResource extends Resource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
      // return parent::toArray($request);

      return [
        "invoice"=> $this->invoice,
        "kode_barang"=> $this->kode_barang,
        "tempat"=> $this->barang->username,
        "nama_barang"=> $this->barang->nama_barang,
        "harga_barang"=> $this->harga_barang,
        "created_at"=> $this->created_at,
      ];
    }
}
