package com.flockware.coinadmin.data.repositories

import androidx.lifecycle.LiveData
import com.flockware.coinadmin.data.AppResult
import com.flockware.coinadmin.data.database.CoinAdminDatabase
import com.flockware.coinadmin.data.models.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

interface CategoryRepository {
    fun getCategoriesLiveData() : LiveData<List<Category>>
    suspend fun addCategory(category: Category)
    suspend fun updateCategory(category: Category)
    suspend fun getCategory(categoryId: Long): AppResult<Category>
    suspend fun getAllCategories(): List<Category>
    suspend fun deleteCategory(category: Category)
}

class CategoryRepositoryImpl(
        private val database: CoinAdminDatabase
) : CategoryRepository {
    override fun getCategoriesLiveData(): LiveData<List<Category>> {
        return database.categoryDao.getAllCategoriesLiveData()
    }
    override suspend fun addCategory(category: Category) {
        withContext(Dispatchers.IO) {
            database.categoryDao.insertCategory(category)
        }
    }

    override suspend fun updateCategory(category: Category) {
        withContext(Dispatchers.IO) {
            database.categoryDao.insertCategory(category)
        }
    }

    override suspend fun getCategory(categoryId: Long): AppResult<Category> {
        val data = withContext(Dispatchers.IO) {
            database.categoryDao.getCategory(categoryId)
        }
        return if (data != null) AppResult.Success(data) else AppResult.Error(Exception("Not found"))
    }

    override suspend fun getAllCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            database.categoryDao.getAllCategories()
        }
    }

    override suspend fun deleteCategory(category: Category) {
        withContext(Dispatchers.IO) {
            database.categoryDao.deleteCategory(category)
        }
    }

}

fun getCategoryRepository(database: CoinAdminDatabase): CategoryRepository = CategoryRepositoryImpl(database)

class CategoryIsUsedException(message: String? = null): Exception(message)