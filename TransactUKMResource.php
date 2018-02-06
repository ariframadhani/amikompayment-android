<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\Resource;

class TransactUKMResource extends Resource
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
          'invoice'  => $this->invoice,
          'token_acara' => $this->token_acara,
          'nama_acara' => $this->acara->nama_acara,
          'dibayar_oleh' => $this->username,
          'total_bayar' => $this->total_bayar,
          'created_at' => $this->created_at,
          'deleted_at' => $this->deleted_at,
        ];

    }
}
