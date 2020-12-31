package com.flockware.coinadmin.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InsertPinViewModel: ViewModel() {

    val digit1: MutableLiveData<Int> = MutableLiveData()
    val digit2: MutableLiveData<Int> = MutableLiveData()
    val digit3: MutableLiveData<Int> = MutableLiveData()
    val digit4: MutableLiveData<Int> = MutableLiveData()

    val pinValid: MutableLiveData<Boolean> = MutableLiveData()

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
        pinValid.value = digit1.value == 1
                && digit2.value == 2
                && digit3.value == 3
                && digit4.value == 4
    }

    fun removeAllDigits() {
        digit1.value = null
        digit2.value = null
        digit3.value = null
        digit4.value = null
    }
}