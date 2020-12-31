package com.flockware.coinadmin.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flockware.coinadmin.utils.SessionManager
import com.flockware.coinadmin.utils.getSHA512

class InsertPinViewModel(
        private val sessionManager: SessionManager
): ViewModel() {

    val digit1: MutableLiveData<Int> = MutableLiveData()
    val digit2: MutableLiveData<Int> = MutableLiveData()
    val digit3: MutableLiveData<Int> = MutableLiveData()
    val digit4: MutableLiveData<Int> = MutableLiveData()

    val pinValid: MutableLiveData<Boolean> = MutableLiveData()
    val startApp: MutableLiveData<Boolean> = MutableLiveData()

    fun checkExistingPin() {
        if (sessionManager.pin.isNullOrEmpty())
            startApp.value = true
    }
    fun insertDigit(digit: Int) {
        when {
            digit1.value == null -> digit1.value = digit
            digit2.value == null -> digit2.value = digit
            digit3.value == null -> digit3.value = digit
            digit4.value == null -> { digit4.value = digit; checkPin() }
        }
    }

    fun deleteDigit() {
        when {
            digit4.value != null -> digit4.value = null
            digit3.value != null -> digit3.value = null
            digit2.value != null -> digit2.value = null
            digit1.value != null -> digit1.value = null
        }
    }

    private fun checkPin() {
        val pin = "${digit1.value}${digit2.value}${digit3.value}${digit4.value}".getSHA512()
        pinValid.value = pin == sessionManager.pin
    }

    fun removeAllDigits() {
        digit1.value = null
        digit2.value = null
        digit3.value = null
        digit4.value = null
    }
}