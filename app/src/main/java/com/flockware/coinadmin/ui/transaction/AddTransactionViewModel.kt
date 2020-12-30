package com.flockware.coinadmin.ui.transaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flockware.coinadmin.data.AppResult
import com.flockware.coinadmin.data.models.Category
import com.flockware.coinadmin.data.models.Transaction
import com.flockware.coinadmin.data.repositories.CategoryRepository
import com.flockware.coinadmin.data.repositories.TransactionRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class AddTransactionViewModel(
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private var category: Category? = null

    var paymentMethod: Transaction.PaymentMethod? = null
    var transactionType: Transaction.TransactionType? = null
    var transactionDate: Date? = null

    var categoriesList = mutableListOf<Category>()
    val showCategory: MutableLiveData<Boolean> = MutableLiveData()

    val transactionSaved: MutableLiveData<AppResult<Boolean>> = MutableLiveData()

    fun getCategories() {
        viewModelScope.launch {
            val result = categoryRepository.getAllCategories()
            when (result) {
                is AppResult.Success -> {
                    categoriesList.clear()
                    categoriesList.addAll(result.successData)
                    showCategory.value = true
                }
                is AppResult.Error -> {
                    // TODO()
                    showCategory.value = false
                }
            }
        }
    }

    fun selectCategory(category: Category) {
        this.category = category
    }

    fun saveTransaction(
        desc: String?,
        amount: Float?
    ) {
        if (desc.isNullOrEmpty()) {
            transactionSaved.value = AppResult.Error(Exception("Aggiungi descrizione"))
            return
        }

        if (amount == null) {
            transactionSaved.value = AppResult.Error(Exception("Aggiungi un importo"))
            return
        }

        if (transactionDate == null) {
            transactionSaved.value = AppResult.Error(Exception("Aggiungi una data"))
            return
        }

        if (transactionType == null) {
            transactionSaved.value = AppResult.Error(Exception("Seleziona il tipo di movimento"))
            return
        }

        if (paymentMethod == null) {
            transactionSaved.value = AppResult.Error(Exception("Seleziona il tipo di pagamento"))
            return
        }


        val transaction = Transaction(
            category = category,
            desc = desc,
            amount = amount,
            date = transactionDate!!,
            type = transactionType!!,
            paymentMethod = paymentMethod!!
        )

        viewModelScope.launch {
            addTransaction(transaction)
        }
    }

    private suspend fun addTransaction(transaction: Transaction) {
        transactionRepository.addTransaction(transaction)
        transactionSaved.value = AppResult.Success(true)
    }

}