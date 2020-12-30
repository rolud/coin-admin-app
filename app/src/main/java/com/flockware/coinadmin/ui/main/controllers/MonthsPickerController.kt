package com.flockware.coinadmin.ui.main.controllers

import com.airbnb.epoxy.EpoxyController
import com.flockware.coinadmin.ui.main.models.monthModel
import java.util.*

class MonthsPickerController(val dataset: List<Date>) : EpoxyController() {
    override fun buildModels() {

        dataset.forEach {
            monthModel {
                id(it.time)
                date(it)
            }
        }
    }
}