package com.flockware.coinadmin.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.flockware.coinadmin.data.models.Transaction

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: Transaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransactions(transactions: List<Transaction>)

    @Update
    fun updateTransaction(transaction: Transaction)

    @Query("SELECT * FROM `Transaction` WHERE id = :transactionId")
    fun getTransaction(transactionId: Long): Transaction?


    @Query("SELECT * FROM `Transaction`")
    fun getAllTransactions(): List<Transaction>

    @Query("SELECT * FROM `Transaction`")
    fun getAllTransactionsLiveData(): LiveData<List<Transaction>>

    @Query("DELETE FROM `Transaction`")
    fun deleteAllTransactions()

    @Delete
    fun deleteTransaction(transaction: Transaction)
}