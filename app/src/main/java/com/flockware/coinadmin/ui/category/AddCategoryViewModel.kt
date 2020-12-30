package com.flockware.coinadmin.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flockware.coinadmin.data.AppResult
import com.flockware.coinadmin.data.models.Category
import com.flockware.coinadmin.data.repositories.CategoryRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class AddCategoryViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    var color: String? = null

    val categorySaved: MutableLiveData<AppResult<Boolean>> = MutableLiveData()

    fun saveCategory(name: String?) {
        if (name.isNullOrEmpty()) {
            categorySaved.value = AppResult.Error(Exception("Devi inserire un nome."))
            return
        }

        val category = Category(
            name = name,
            color = color
        )

        viewModelScope.launch {
            addCategory(category)
        }
    }

    private suspend fun addCategory(category: Category) {
        categoryRepository.addCategory(category)
        categorySaved.value = AppResult.Success(true)
    }
}