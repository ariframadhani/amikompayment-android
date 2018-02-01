<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class DetailTransactBarang extends Model
{
  protected $table = 'detail_transact_barang';

  protected $fillable = [
    'invoice', 'kode_barang', 'harga_barang'
  ];

  protected $hidden = [
    'id', 'created_at', 'deleted_at', 'updated_at'
  ];

  public function barang()
  {
    return $this->belongsTo(Barang::class, 'kode_barang', 'kode_barang');
  }

  public function transact_barang()
  {
    return $this->belongsTo(TransactBarang::class, 'invoice', 'invoice');
  }
}
