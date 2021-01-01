package com.flockware.coinadmin.utils

import android.content.Context
import com.flockware.coinadmin.utils.SessionKey.*

class SessionManager(val context: Context) {

    var pin: String? = null
    var isBiometricAuthEnabled: Boolean = false

    init {
        load()
    }

    private fun load() {
        pin = SharedPreferencesUtils.load(context, SETTINGS_PIN, "")
        isBiometricAuthEnabled = SharedPreferencesUtils.load(context, SETTINGS_BIOMETRIC_AUTHENTICATION, false)
    }

    fun save() {
        if (pin != null) SharedPreferencesUtils.save(context, SETTINGS_PIN, pin!!) else SharedPreferencesUtils.delete(context, SETTINGS_PIN)
        SharedPreferencesUtils.save(context, SETTINGS_BIOMETRIC_AUTHENTICATION, isBiometricAuthEnabled)
    }
}