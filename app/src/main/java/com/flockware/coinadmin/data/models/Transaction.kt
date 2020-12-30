package com.flockware.coinadmin.data.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity
@Parcelize
data class Transaction(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        @Embedded(prefix = "category_") val category: Category? = null,
        val desc: String,
        val amount: Float,
        val date: Date,
        val type: TransactionType,
        val paymentMethod: PaymentMethod
) : Parcelable {
    enum class PaymentMethod {
        CASH, DIGITAL
    }
    enum class TransactionType {
        PAID_IN, PAID_OUT
    }
}
