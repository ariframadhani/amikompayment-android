<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Barang extends Model
{
  protected $table = 'barang';

  public function user()
  {
    return $this->belongsTo(User::class, 'username', 'username');
  }

  public function detail_transact_barang()
  {
    return $this->hasMany(DetailTransactBarang::class, 'kode_barang', 'kode_barang');
  }
}
