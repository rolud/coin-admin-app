package com.flockware.coinadmin.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flockware.coinadmin.utils.SessionManager
import com.flockware.coinadmin.utils.getSHA512

class SetPinViewModel(
        private val sessionManager: SessionManager
) : ViewModel() {

    val digit1: MutableLiveData<Int> = MutableLiveData()
    val digit2: MutableLiveData<Int> = MutableLiveData()
    val digit3: MutableLiveData<Int> = MutableLiveData()
    val digit4: MutableLiveData<Int> = MutableLiveData()

    val editMode: MutableLiveData<Boolean> = MutableLiveData()
    val pinSaved: MutableLiveData<Boolean> = MutableLiveData()
    val pinValid: MutableLiveData<Boolean> = MutableLiveData()

    fun checkExistingPin() {
        editMode.value = !sessionManager.pin.isNullOrEmpty()
    }

    fun insertDigit(digit: Int) {
        when {
            digit1.value == null -> digit1.value = digit
            digit2.value == null -> digit2.value = digit
            digit3.value == null -> digit3.value = digit
            digit4.value == null -> {
                digit4.value = digit
                if (editMode.value == true)
                    checkPin()
                else
                    savePin()
            }
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

    private fun savePin() {
        val pin = "${digit1.value}${digit2.value}${digit3.value}${digit4.value}".getSHA512()
        sessionManager.pin = pin
        sessionManager.save()
        pinSaved.value = true
    }

    fun removeAllDigits() {
        digit1.value = null
        digit2.value = null
        digit3.value = null
        digit4.value = null
    }

    fun prepareForNewPin() {
        editMode.value = false
    }

    fun removePin() {
        sessionManager.pin = null
        sessionManager.isBiometricAuthEnabled = false
        sessionManager.save()
        pinSaved.value = true
    }
}