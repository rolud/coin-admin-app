package com.flockware.coinadmin.ui.main.controllers

import com.airbnb.epoxy.EpoxyController
import com.flockware.coinadmin.data.models.Transaction
import com.flockware.coinadmin.ui.main.models.transactionModel

class TransactionsController(val dataset: List<Transaction>): EpoxyController() {
    override fun buildModels() {
        dataset.forEach { transaction ->
            transactionModel {
                id(transaction.id)
                transaction(transaction)
            }
        }
    }
}