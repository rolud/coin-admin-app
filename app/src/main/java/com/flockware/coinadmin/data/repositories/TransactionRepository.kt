package com.flockware.coinadmin.data.repositories

import androidx.lifecycle.LiveData
import com.flockware.coinadmin.data.AppResult
import com.flockware.coinadmin.data.database.CoinAdminDatabase
import com.flockware.coinadmin.data.models.Transaction
import com.flockware.coinadmin.utils.DatePattern
import com.flockware.coinadmin.utils.pattern
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*

interface TransactionRepository {
    fun getTransactionsLive(): LiveData<List<Transaction>>
    suspend fun addTransaction(transaction: Transaction)
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun getTransaction(transactionId: Long): AppResult<Transaction>
    suspend fun getTransactionsByMonth(date: Date): AppResult<List<Transaction>>
    suspend fun deleteTransaction(transaction: Transaction)
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

    override suspend fun updateTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            database.transactionDao.updateTransaction(transaction)
        }
    }

    override suspend fun getTransaction(transactionId: Long): AppResult<Transaction> {
        val data = withContext(Dispatchers.IO) {
            database.transactionDao.getTransaction(transactionId)
        }
        return if (data != null) AppResult.Success(data) else AppResult.Error(Exception("Not found"))
    }

    override suspend fun getTransactionsByMonth(date: Date): AppResult<List<Transaction>> {
        val data = withContext(Dispatchers.IO) {
            database.transactionDao.getAllTransactions().filter {
                it.date.pattern(DatePattern.EXPLICIT_MONTH_YEAR) == date.pattern(DatePattern.EXPLICIT_MONTH_YEAR)
            }
        }
        return AppResult.Success(data)
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            database.transactionDao.deleteTransaction(transaction)
        }
    }
}

fun getTransactionRepository(database: CoinAdminDatabase): TransactionRepository = TransactionRepositoryImpl(database)