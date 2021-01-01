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

    var editMode: Boolean = false
    val transactionForEdit: MutableLiveData<Transaction> = MutableLiveData()

    fun getCategories() {
        viewModelScope.launch {
            val data = categoryRepository.getAllCategories()
            categoriesList.clear()
            categoriesList.addAll(data)
            showCategory.value = true
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


        if (editMode) {
            val transaction = transactionForEdit.value!!
            transaction.apply {
                this.desc = desc
                this.amount = amount
                this.category = this@AddTransactionViewModel.category
                this.date = this@AddTransactionViewModel.transactionDate!!
                this.type = this@AddTransactionViewModel.transactionType!!
                this.paymentMethod = this@AddTransactionViewModel.paymentMethod!!
            }

            viewModelScope.launch {
                updateTransaction(transaction)
            }
        } else {
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
    }

    private suspend fun addTransaction(transaction: Transaction) {
        transactionRepository.addTransaction(transaction)
        transactionSaved.value = AppResult.Success(true)
    }

    private suspend fun updateTransaction(transaction: Transaction) {
        transactionRepository.updateTransaction(transaction)
        transactionSaved.value = AppResult.Success(true)
    }

    fun loadTransactionForEdit(transactionId: Long) {
        editMode = true
        viewModelScope.launch {
            val result = transactionRepository.getTransaction(transactionId)
            when (result) {
                is AppResult.Success -> {
                    category = result.successData.category
                    transactionDate = result.successData.date
                    transactionForEdit.value = result.successData
                }
                is AppResult.Error -> transactionForEdit.value = null
            }
        }
    }

}