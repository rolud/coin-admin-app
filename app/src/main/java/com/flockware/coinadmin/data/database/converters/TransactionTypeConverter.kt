package com.flockware.coinadmin.data.database.converters

import androidx.room.TypeConverter
import com.flockware.coinadmin.data.models.Transaction

class TransactionTypeConverter {

    @TypeConverter
    fun toInt(value: String?): Transaction.TransactionType? {
        return if (value == null) null else Transaction.TransactionType.valueOf(value)
    }

    @TypeConverter
    fun toString(value: Transaction.TransactionType?): String? {
        return value?.name
    }

}