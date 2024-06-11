package com.muralidhar.threadsapp.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppKeysStore {

    companion object {
        private val Context.dataStore by preferencesDataStore(
            name = "app_preferences"
        )

        private val INSTALL_KEY = booleanPreferencesKey("IS_INSTALL")

        suspend fun setFirstTimeInstall(isInstall: Boolean, context: Context) {
            context.dataStore.edit {
                it[INSTALL_KEY] = isInstall
            }
        }

        fun isAlreadyInstalled(context: Context): Flow<Boolean> {
            return context.dataStore.data.map {
                it[INSTALL_KEY] ?: false
            }
        }
    }
}