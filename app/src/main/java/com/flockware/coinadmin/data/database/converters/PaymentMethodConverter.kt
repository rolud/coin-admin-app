package com.flockware.coinadmin.data.database.converters

import androidx.room.TypeConverter
import com.flockware.coinadmin.data.models.Transaction

class PaymentMethodConverter {
    @TypeConverter
    fun toInt(value: String?): Transaction.PaymentMethod? {
        return if (value == null) null else Transaction.PaymentMethod.valueOf(value)
    }

    @TypeConverter
    fun toString(value: Transaction.PaymentMethod?): String? {
        return value?.name
    }

}