package com.flockware.coinadmin.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flockware.coinadmin.utils.SessionManager

class SettingsViewModel(
        private val sessionManager: SessionManager
) : ViewModel() {
    val pinIsEnabled: MutableLiveData<Boolean> = MutableLiveData()

    val isBiometricAuthEnabled: Boolean get() = sessionManager.isBiometricAuthEnabled

    fun checkPinSettings() {
        pinIsEnabled.value = sessionManager.pin.isNullOrEmpty().not()
    }

    fun enableBiometricAuthentication(enable: Boolean) {
        sessionManager.isBiometricAuthEnabled = enable
        sessionManager.save()
    }
}