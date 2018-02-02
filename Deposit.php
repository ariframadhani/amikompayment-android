<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Deposit extends Model
{

    use SoftDeletes;
    protected $table = 'deposit';

    protected $hidden = [
      'created_at', 'id',
    ];

    public function user()
    {
      return $this->belongsTo(User::class, 'username', 'username');
    }
}
