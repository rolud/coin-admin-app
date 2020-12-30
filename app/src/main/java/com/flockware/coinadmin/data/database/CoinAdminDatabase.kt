package com.flockware.coinadmin.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.flockware.coinadmin.data.database.converters.DateConverter
import com.flockware.coinadmin.data.database.converters.PaymentMethodConverter
import com.flockware.coinadmin.data.database.converters.TransactionTypeConverter
import com.flockware.coinadmin.data.models.Category
import com.flockware.coinadmin.data.models.Transaction
import com.flockware.coinadmin.data.database.dao.CategoryDao
import com.flockware.coinadmin.data.database.dao.TransactionDao

@Database(
        entities = [
            Transaction::class,
            Category::class
        ],
        version = 1,
        exportSchema = false
)
@TypeConverters(
    DateConverter::class,
    TransactionTypeConverter::class,
    PaymentMethodConverter::class
)
abstract class CoinAdminDatabase : RoomDatabase() {
    abstract val categoryDao:CategoryDao
    abstract val transactionDao: TransactionDao
}

fun getDatabase(application: Application): CoinAdminDatabase =
        Room.databaseBuilder(application, CoinAdminDatabase::class.java, "coin_admin_db")
                .build()