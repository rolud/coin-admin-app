package com.flockware.coinadmin.data.repositories

import androidx.lifecycle.LiveData
import com.flockware.coinadmin.data.AppResult
import com.flockware.coinadmin.data.database.CoinAdminDatabase
import com.flockware.coinadmin.data.models.Transaction
import com.flockware.coinadmin.utils.DatePattern
import com.flockware.coinadmin.utils.pattern
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

interface TransactionRepository {
    fun getTransactionsLive(): LiveData<List<Transaction>>
    suspend fun addTransaction(transaction: Transaction)
    suspend fun getTransactionsByMonth(date: Date): AppResult<List<Transaction>>
}

class TransactionRepositoryImpl(
        private val database: CoinAdminDatabase
) : TransactionRepository {

    override fun getTransactionsLive(): LiveData<List<Transaction>> {
        return database.transactionDao.getAllTransactionsLiveData()
    }

    override suspend fun addTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            database.transactionDao.insertTransaction(transaction)
        }
    }

    override suspend fun getTransactionsByMonth(date: Date): AppResult<List<Transaction>> {
        val data = withContext(Dispatchers.IO) {
            database.transactionDao.getAllTransactions().filter {
                it.date.pattern(DatePattern.EXPLICIT_MONTH_YEAR) == date.pattern(DatePattern.EXPLICIT_MONTH_YEAR)
            }
        }
        return AppResult.Success(data)
    }

}

fun getTransactionRepository(database: CoinAdminDatabase): TransactionRepository = TransactionRepositoryImpl(database)