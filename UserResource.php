<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\Resource;

class UserResource extends Resource
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
          "nama_lengkap" => $this->nama,
          "username" => $this->username,
          "phone" => $this->phone,
          "saldo" => $this->saldo,
          "api_token" => $this->api_token,
          "token" => $this->rekening,
          "user_is" => $this->kategori_user,
          "history_deposit" => $this->deposit,
          "history_transaksi_ukm" => $this->transact_ukm,
          "history_transaksi_barang" => $this->transact_barang,
          "acara" => $this->acara,
          "is_official" => ($this->isOfficial) ? 'true' : 'false' ,
          "created_at" => $this->updated_at,
        ];
    }

}
