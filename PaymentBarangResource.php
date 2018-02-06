<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\Resource;

class PaymentBarangResource extends Resource
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
          'invoice' => $this->invoice,
          'nomor_pembayaran' => $this->invoice_tr_barang,
          'pembayar' => $this->username,
          'total_bayar' => $this->total_bayar,
          'updated_at' => $this->updated_at
        ];
    }
}
