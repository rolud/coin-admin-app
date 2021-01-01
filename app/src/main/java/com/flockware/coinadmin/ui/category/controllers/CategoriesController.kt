package com.flockware.coinadmin.ui.category.controllers

import com.airbnb.epoxy.EpoxyController
import com.flockware.coinadmin.data.models.Category
import com.flockware.coinadmin.ui.category.models.categoryModel
import com.flockware.coinadmin.utils.LoggerCompat

class CategoriesController(private val dataset: List<Category>): EpoxyController() {

    var onCategoryClick: (Category) -> Unit = {}

    override fun buildModels() {
        dataset.forEach { category ->
            LoggerCompat.warning(category, "Category")
            categoryModel {
                id(category.id)
                category(category)
                onClick { onCategoryClick(category) }
            }
        }
    }
}