package com.flockware.coinadmin.data.repositories

import com.flockware.coinadmin.data.AppResult
import com.flockware.coinadmin.data.database.CoinAdminDatabase
import com.flockware.coinadmin.data.models.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface CategoryRepository {
    suspend fun addCategory(category: Category)
    suspend fun updateCategory(category: Category)
    suspend fun getAllCategories(): AppResult<List<Category>>
}

class CategoryRepositoryImpl(
        private val database: CoinAdminDatabase
) : CategoryRepository {
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

    override suspend fun getAllCategories(): AppResult<List<Category>> {
        val data = withContext(Dispatchers.IO) {
            database.categoryDao.getAllCategories()
        }
        return AppResult.Success(data)
    }

}

fun getCategoryRepository(database: CoinAdminDatabase): CategoryRepository = CategoryRepositoryImpl(database)