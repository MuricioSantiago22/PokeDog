package com.example.pokedog.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object SecurePreferences {

    private const val PREFS_NAME = "secure_prefs"
    private const val AUTH_TOKEN_KEY = "auth_token"

    private fun getEncryptedPrefs(context: Context) =
        EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun saveAuthToken(context: Context, token: String) {
        getEncryptedPrefs(context).edit().putString(AUTH_TOKEN_KEY, token).apply()
    }

    fun getAuthToken(context: Context): String {
        return getEncryptedPrefs(context).getString(AUTH_TOKEN_KEY, "") ?: ""
    }

    fun clearAuthToken(context: Context) {
        getEncryptedPrefs(context).edit().remove(AUTH_TOKEN_KEY).apply()
    }
}