<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\Resource;

class BarangResource extends Resource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        return [
          "id" => $this->id,
          "milik" => $this->user->nama,
          "kode_barang" => $this->kode_barang,
          "nama_barang" => $this->nama_barang,
          "harga_barang" => $this->harga_barang,
          "updated_at" => $this->updated_at
        ];
    }
}
