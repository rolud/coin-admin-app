package com.flockware.coinadmin.ui.transaction.controllers

import com.airbnb.epoxy.EpoxyController
import com.flockware.coinadmin.data.models.Category
import com.flockware.coinadmin.ui.transaction.models.addCategoryPickerModel
import com.flockware.coinadmin.ui.transaction.models.categoryPickerModel
import com.flockware.coinadmin.ui.transaction.models.nullCategoryPickerModel

class CategoriesPickerController(val dataset: List<Category>) : EpoxyController() {

    var onAddClick: () -> Unit = {}
    var onCategoryClick: (Category) -> Unit = {}
    var onGenericClick: () -> Unit = {}
    var onNullCategoryClick: () -> Unit = {}

    var isForStatistics: Boolean = false

    override fun buildModels() {
        if (isForStatistics) {
            nullCategoryPickerModel {
                id(0)
                name("Generale")
                onClick { onGenericClick() }
            }
            nullCategoryPickerModel {
                id(1)
                name("Senza categoria")
                onClick { onNullCategoryClick() }
            }
        } else {
            addCategoryPickerModel {
                id(0)
                onClick { onAddClick() }
            }
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