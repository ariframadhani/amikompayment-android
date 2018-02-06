<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\Resource;

class AcaraResource extends Resource
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
          'nama_acara' => $this->nama_acara,
          'penyelengara' => $this->user->nama,
          'token_acara' => $this->token,
          'kategori_acara' => $this->kategori->informasi,
          'harga_acara' => $this->harga_acara,
          'created_at' => $this->updated_at
        ];
    }
}
