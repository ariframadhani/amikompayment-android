<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\Resource;

class TransactBarangResource extends Resource
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
          "invoice_transact" => $this->invoice,
          "id_tempat" => $this->user->username,
          "tempat" => $this->user->nama,
          "detail_belanja" => $this->detail_transact,
          "total_harga" => $this->total_harga,
          "updated_at" => $this->updated_at,
        ];
    }
}
