<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class KategoriUser extends Model
{
    protected $table = 'kategori_user';

    protected $fillable = [
      'nama', 'informasi'
    ];
    protected $hidden = [
      'id', 'created_at'
    ];

    public function user()
    {
      return $this->hasMany(User::class, 'nama');
    }
}
