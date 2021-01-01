package com.flockware.coinadmin.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flockware.coinadmin.data.AppResult
import com.flockware.coinadmin.data.models.Category
import com.flockware.coinadmin.data.repositories.CategoryIsUsedException
import com.flockware.coinadmin.data.repositories.CategoryRepository
import com.flockware.coinadmin.data.repositories.TransactionRepository
import com.flockware.coinadmin.utils.LoggerCompat
import kotlinx.coroutines.launch
import java.lang.Exception

class CategoriesViewModel(
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val categoriesList: MutableList<Category> = mutableListOf()
    val categoriesUpdated: MutableLiveData<Boolean> = MutableLiveData()

    val categoriesLiveData: LiveData<List<Category>> = categoryRepository.getCategoriesLiveData()

    val categoryDeleted: MutableLiveData<AppResult<Boolean>> = MutableLiveData()

    fun loadCategories() {
        viewModelScope.launch {
            val data = categoryRepository.getAllCategories()
            categoriesList.clear()
            categoriesList.addAll(data)
            categoriesUpdated.value = true
            LoggerCompat.verbose(data, "Categories")
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            val transactions = transactionRepository.getAllTransactions()
            val isCategoryUsed = transactions.any { it.category == category }
            if (isCategoryUsed) {
                // not allowed
                categoryDeleted.value = AppResult.Error(CategoryIsUsedException())
            } else {
                categoryRepository.deleteCategory(category)
                categoryDeleted.value = AppResult.Success(true)
            }
        }
    }
}