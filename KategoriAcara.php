<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class KategoriAcara extends Model
{

  protected $table = 'kategori_acara';

  protected $hidden = [
    'id'
  ];

  public function acara()
  {
    return $this->hasMany(Acara::class, 'nama');
  }
}
