package com.knulphe.todomobile.helpers

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("users_prefs", Context.MODE_PRIVATE)

    fun saveUser(email: String) {
        preferences.edit().putString("email", email).putBoolean("remember_me", true).apply()
    }

    fun getUserEmail(): String? = preferences.getString("email", null)

    fun isRemembered(): Boolean = preferences.getBoolean("remember_me", false)

    fun clearUser() {
        preferences.edit().clear().apply()
    }
}