<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class TransactUKM extends Model
{

  use SoftDeletes;
  protected $table = 'transact_ukm';

  protected $fillable = [
    'invoice', 'token_acara', 'username', 'total_bayar'
  ];

  protected $hidden = [
    'updated_at', 'id'
  ];

  public function user()
  {
    return $this->belongsTo(User::class, 'username', 'username');
  }

  public function acara()
  {
    return $this->belongsTo(Acara::class, 'token_acara', 'token');
  }
}
