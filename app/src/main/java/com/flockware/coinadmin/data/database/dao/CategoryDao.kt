package com.flockware.coinadmin.data.database.dao

import androidx.room.*
import com.flockware.coinadmin.data.models.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(categories: List<Category>)

    @Update
    fun updateCategory(category: Category)

    @Query("SELECT * FROM Category")
    fun getAllCategories(): List<Category>

    @Query("DELETE FROM Category")
    fun deleteAllCategories()

    @Delete
    fun deleteCategory(category: Category)

}