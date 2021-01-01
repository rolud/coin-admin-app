package com.flockware.coinadmin.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flockware.coinadmin.data.AppResult
import com.flockware.coinadmin.data.models.Category
import com.flockware.coinadmin.data.repositories.CategoryRepository
import com.flockware.coinadmin.data.repositories.TransactionRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class AddCategoryViewModel(
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    var color: String? = null

    val categorySaved: MutableLiveData<AppResult<Boolean>> = MutableLiveData()

    var editMode: Boolean = false
    val categoryForEdit: MutableLiveData<Category> = MutableLiveData()
    fun saveCategory(name: String?) {
        if (name.isNullOrEmpty()) {
            categorySaved.value = AppResult.Error(Exception("Devi inserire un nome."))
            return
        }

        if (editMode) {
            val category = categoryForEdit.value!!
            category.name = name
            category.color = color
            viewModelScope.launch {
                updateCategory(category)
            }
        } else {
            val category = Category(
                name = name,
                color = color
            )

            viewModelScope.launch {
                addCategory(category)
            }
        }
    }

    fun loadCategoryForEdit(categoryId: Long) {
        editMode = true
        viewModelScope.launch {
            val result = categoryRepository.getCategory(categoryId)
            when (result) {
                is AppResult.Success -> {
                    color = result.successData.color
                    categoryForEdit.value = result.successData
                }
                is AppResult.Error -> categoryForEdit.value = null
            }
        }
    }

    private suspend fun addCategory(category: Category) {
        categoryRepository.addCategory(category)
        categorySaved.value = AppResult.Success(true)
    }

    private suspend fun updateCategory(category: Category) {
        categoryRepository.updateCategory(category)
        transactionRepository.getAllTransactions()
            .filter { it.category?.id == category.id }
            .forEach { transaction ->
                transaction.category = category
                transactionRepository.updateTransaction(transaction)
            }
        categorySaved.value = AppResult.Success(true)
    }
}