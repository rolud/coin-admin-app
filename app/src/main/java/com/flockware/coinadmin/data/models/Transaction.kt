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
        @Embedded(prefix = "category_") var category: Category? = null,
        var desc: String,
        var amount: Float,
        var date: Date,
        var type: TransactionType,
        var paymentMethod: PaymentMethod
) : Parcelable {
    enum class PaymentMethod {
        CASH, DIGITAL
    }
    enum class TransactionType {
        PAID_IN, PAID_OUT
    }
}
