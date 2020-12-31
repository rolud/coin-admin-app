package com.flockware.coinadmin.utils

import android.content.Context
import com.flockware.coinadmin.utils.SessionKey.*

class SessionManager(val context: Context) {

    var pin: String? = null
    var useFingerPrint: Boolean = false

    init {
        load()
    }

    private fun load() {
        pin = SharedPreferencesUtils.load(context, SETTINGS_PIN, "")
        useFingerPrint = SharedPreferencesUtils.load(context, SETTINGS_FINGERPRINT, false)
    }

    fun save() {
        if (pin != null) SharedPreferencesUtils.save(context, SETTINGS_PIN, pin!!) else SharedPreferencesUtils.delete(context, SETTINGS_PIN)
        SharedPreferencesUtils.save(context, SETTINGS_FINGERPRINT, useFingerPrint)
    }
}