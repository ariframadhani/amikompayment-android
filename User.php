<?php

namespace App;

use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;

class User extends Authenticatable
{
    use Notifiable;

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'nama', 'username', 'password','rekening', 'phone','saldo','isOfficial'
      ];


    public function kategori_user()
    {
      return $this->belongsTo(KategoriUser::class, 'nama');
    }

    public function acara()
    {
      return $this->hasMany(Acara::class, 'penyelengara', 'username');
    }

    public function transact_ukm()
    {
      return $this->hasMany(TransactUKM::class, 'username', 'username');
    }

    public function barang()
    {
      return $this->hasMany(Barang::class, 'username', 'username');
    }

    public function transact_barang()
    {
      return $this->hasMany(TransactBarang::class, 'username', 'username');
    }

}
