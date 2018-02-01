<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Acara extends Model
{
  protected $table = 'acara';

  public function user()
  {
    return $this->belongsTo(User::class, 'penyelengara', 'username');
  }

  public function kategori_acara()
  {
    return $this->belongsTo(KategoriAcara::class, 'nama');
  }

  public function transact_ukm()
  {
    return $this->hasMany(TransactUKM::class, 'token_acara', 'token');
  }
}
