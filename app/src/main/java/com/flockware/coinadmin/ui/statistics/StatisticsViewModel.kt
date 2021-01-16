package com.flockware.coinadmin.ui.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flockware.coinadmin.data.AppResult
import com.flockware.coinadmin.data.models.Category
import com.flockware.coinadmin.data.models.Transaction
import com.flockware.coinadmin.data.repositories.CategoryRepository
import com.flockware.coinadmin.data.repositories.TransactionRepository
import com.flockware.coinadmin.utils.*
import kotlinx.coroutines.launch
import java.util.*

class StatisticsViewModel(
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val today: Date = DateUtils.today()
    var currentPeriod: Date? = null

    private var categorySelected: Boolean = false
    private var category: Category? = null

    val periodsList = mutableListOf<Date>()
    val periodsListUpdated: MutableLiveData<Boolean> = MutableLiveData()

    val transactionsList = mutableListOf<Transaction>()
    val transactionsListUpdated: MutableLiveData<Boolean> = MutableLiveData()

    var categoriesList = mutableListOf<Category>()
    val showCategory: MutableLiveData<Boolean> = MutableLiveData()

    fun initMonthlyView() {
        periodsList.add(today.minusMonths(2))
        periodsList.add(today.previousMonth())
        periodsList.add(today)
        periodsList.add(today.nextMonth())
        periodsList.add(today.plusMonths(2))

        periodsListUpdated.value = true

        viewModelScope.launch {
            loadTransactions(today)
        }
    }

    fun selectMonth(position: Int) {
        if (position < periodsList.size && currentPeriod == periodsList[position])
            return

        val period = periodsList[position]
        when {
            position <= 2 -> {
                val currentFirstMonth = periodsList.first()
                periodsList.add(0, currentFirstMonth.previousMonth())
                periodsListUpdated.value = true
            }
            position >= periodsList.size - 2 -> {
                val currentLastMonth = periodsList.last()
                periodsList.add(currentLastMonth.nextMonth())
                periodsListUpdated.value = true
            }
            else -> {
                periodsListUpdated.value = false
            }
        }

        viewModelScope.launch {
            loadTransactions(period)
        }
    }

    private suspend fun loadTransactions(date: Date? = currentPeriod) {
        date ?: return
        if (currentPeriod != date) currentPeriod = date
        val result = transactionRepository.getTransactionsByMonth(date)
        when (result) {
            is AppResult.Success -> {
                transactionsList.clear()
                transactionsList.addAll( result.successData.filter { if (categorySelected) it.category == category else true }.sortedByDescending { it.date })
                transactionsListUpdated.value = true
            }
            is AppResult.Error -> {
                // todo
                transactionsListUpdated.value = false
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            val data = categoryRepository.getAllCategories()
            categoriesList.clear()
            categoriesList.addAll(data)
            showCategory.value = true
        }
    }

    fun selectCategory(category: Category?) {
        this.category = category
        this.categorySelected = true
        viewModelScope.launch {
            loadTransactions(today)
        }
    }

    fun unselectCategory() {
        this.categorySelected = false
        this.category = null
        viewModelScope.launch {
            loadTransactions(today)
        }
    }


}