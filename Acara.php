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

  public function kategori()
  {
    return $this->belongsTo(KategoriAcara::class, 'kategori_acara', 'nama');
  }

  public function transact_ukm()
  {
    return $this->hasMany(TransactUKM::class, 'token_acara', 'token');
  }
}
