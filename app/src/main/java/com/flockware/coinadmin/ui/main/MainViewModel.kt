package com.flockware.coinadmin.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flockware.coinadmin.data.AppResult
import com.flockware.coinadmin.data.models.Transaction
import com.flockware.coinadmin.data.repositories.TransactionRepository
import com.flockware.coinadmin.utils.*
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
        private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val today: Date = DateUtils.today()
    private var currentMonth: Date? = null

    val monthsList = mutableListOf<Date>()
    val monthsListUpdated: MutableLiveData<Boolean> = MutableLiveData()

    val transactionsList = mutableListOf<Transaction>()
    val transactionsListUpdated: MutableLiveData<Boolean> = MutableLiveData()

    val transactionsLive: LiveData<List<Transaction>> = transactionRepository.getTransactionsLive()

    val paidInTotal: MutableLiveData<Float> = MutableLiveData()
    val paidOutTotal: MutableLiveData<Float> = MutableLiveData()
    val paidDifferenceTotal: MutableLiveData<Float> = MutableLiveData()

    val snackbarMessage: MutableLiveData<String> = MutableLiveData()

    fun initMonthsList() {
        monthsList.add(today.minusMonths(2))
        monthsList.add(today.previousMonth())
        monthsList.add(today)
        monthsList.add(today.nextMonth())
        monthsList.add(today.plusMonths(2))

        monthsListUpdated.value = true

        viewModelScope.launch {
            loadTransactions(today)
        }
    }

    fun getTodayDatePosition(): Int {
        return monthsList.indexOf(today)
    }

    fun selectMonth(position: Int) {
        val currentMonth = monthsList[position]
        when {
            position <= 2 -> {
                val currentFirstMonth = monthsList.first()
                monthsList.add(0, currentFirstMonth.previousMonth())
                monthsListUpdated.value = true
            }
            position >= monthsList.size - 2 -> {
                val currentLastMonth = monthsList.last()
                monthsList.add(currentLastMonth.nextMonth())
                monthsListUpdated.value = true
            }
            else -> {
                monthsListUpdated.value = false
            }
        }

        viewModelScope.launch {
            loadTransactions(currentMonth)
        }
    }

    fun reloadTransactions() {
        viewModelScope.launch {
            loadTransactions()
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionRepository.deleteTransaction(transaction)
            snackbarMessage.value = "Movimento eliminato"
        }
    }

    private suspend fun loadTransactions(date: Date? = currentMonth) {
        date ?: return
        if (currentMonth != date) currentMonth = date
        val result = transactionRepository.getTransactionsByMonth(date)
        when (result) {
            is AppResult.Success -> {
                transactionsList.clear()
                transactionsList.addAll(result.successData.sortedByDescending { it.date })
                transactionsListUpdated.value = true
                calculateTotals()
            }
            is AppResult.Error -> {
                // todo
                transactionsListUpdated.value = false
            }
        }
    }

    private fun calculateTotals() {
        val pit = transactionsList.filter { it.type == Transaction.TransactionType.PAID_IN }.sumOf { it.amount.toDouble() }.toFloat()
        val pot = transactionsList.filter { it.type == Transaction.TransactionType.PAID_OUT }.sumOf { it.amount.toDouble() }.toFloat() * -1
        paidInTotal.value = pit
        paidOutTotal.value = pot
        paidDifferenceTotal.value = pit + pot
    }
}