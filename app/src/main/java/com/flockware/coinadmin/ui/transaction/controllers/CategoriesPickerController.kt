package com.flockware.coinadmin.ui.transaction.controllers

import com.airbnb.epoxy.EpoxyController
import com.flockware.coinadmin.data.models.Category
import com.flockware.coinadmin.ui.transaction.models.addCategoryPickerModel
import com.flockware.coinadmin.ui.transaction.models.categoryPickerModel

class CategoriesPickerController(val dataset: List<Category>) : EpoxyController() {

    var onAddClick: () -> Unit = {}
    var onCategoryClick: (Category) -> Unit = {}

    override fun buildModels() {
        addCategoryPickerModel {
            id(0)
            onClick { onAddClick() }
        }

        dataset.forEach { category ->
            categoryPickerModel {
                id(category.id)
                category(category)
                onClick { onCategoryClick(category) }
            }
        }
    }
}