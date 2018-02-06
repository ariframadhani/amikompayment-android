<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class PaymentBarang extends Model
{
    protected $table = "payment_barang";

    public function invoice()
    {
      return $this->belongsTo(TransactBarang::class, 'invoice_tr_barang', 'invoice');
    }

    public function user()
    {
      return $this->belongsTo(User::class, 'username', 'username');
    }
}
