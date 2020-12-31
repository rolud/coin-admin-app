package com.flockware.coinadmin.utils

import android.content.Context
import com.flockware.coinadmin.R
import java.util.*

object SharedPreferencesUtils {

    fun save(context: Context, tag: SessionKey, value: String) {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(tag.name, value)
        editor.apply()
    }

    fun save(context: Context, tag: SessionKey, value: Int) {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putInt(tag.name, value)
        editor.apply()
    }

    fun save(context: Context, tag: SessionKey, value: Long) {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putLong(tag.name, value)
        editor.apply()
    }

    fun save(context: Context, tag: SessionKey, value: Date) {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putLong(tag.name, value.time)
        editor.apply()
    }

    fun save(context: Context, tag: SessionKey, value: Boolean) {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(tag.name, value)
        editor.apply()
    }

    fun save(context: Context, tag: SessionKey, value: Set<String>) {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putStringSet(tag.name, value)
        editor.apply()
    }

    fun save(context: Context, tag: String, value: String) {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(tag, value)
        editor.apply()
    }

    fun save(context: Context, tag: String, value: Int) {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putInt(tag, value)
        editor.apply()
    }

    fun load(context: Context, tag: SessionKey) : String? {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        return sp.getString(tag.name, null)
    }

    fun load(context: Context, tag: SessionKey, def: String): String {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        return sp.getString(tag.name, def) ?: def
    }

    fun load(context: Context, tag: SessionKey, def: Set<String>): Set<String> {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        return sp.getStringSet(tag.name, def) ?: def
    }

    fun load(context: Context, tag: SessionKey, def: Long?): Long {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        return sp.getLong(tag.name, def!!)
    }

    fun load(context: Context, tag: SessionKey, def: Date): Date {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        return Date(sp.getLong(tag.name, def.time))
    }

    fun load(context: Context, tag: SessionKey, def: Int): Int {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        return sp.getInt(tag.name, def)
    }

    fun load(context: Context, tag: SessionKey, def: Boolean): Boolean {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        return sp.getBoolean(tag.name, def)
    }

    fun load(context: Context, tag: String, def: String): String {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        return sp.getString(tag, "") ?: ""
    }

    fun load(context: Context, tag: String, def: Int): Int {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        return sp.getInt(tag, def)
    }

    fun delete(context: Context, tag: SessionKey) {
        val sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_session),
                Context.MODE_PRIVATE)
        sp.edit().remove(tag.name).apply()
    }



}