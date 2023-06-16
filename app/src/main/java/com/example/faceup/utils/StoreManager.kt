package com.example.faceup.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "storeManager")
class StoreManager private constructor(private val datastore: DataStore<Preferences>){

    private object PreferencesKeys {
        val TOKEN = stringPreferencesKey("token")
    }

    suspend fun saveToken (token :String){
        datastore.edit {
            it[PreferencesKeys.TOKEN] = token
        }
    }

    suspend fun getToken(): String? {
        val preferences = datastore.data.first()
        return preferences[PreferencesKeys.TOKEN]
    }

    suspend fun deleteToken(){
        datastore.edit {
            it.remove(PreferencesKeys.TOKEN)
        }
    }

    companion object {

        fun getTokenSynchronously(dataStore: DataStore<Preferences>) : String?{
            return runBlocking {
                StoreManager(dataStore).getToken()
            }
        }

        @Volatile
        private var INSTANCE : StoreManager? = null
        fun getInstance(datastore: DataStore<Preferences>): StoreManager{
            return INSTANCE ?: synchronized(this){
                val instance = StoreManager(datastore)
                INSTANCE = instance
                instance
            }
        }
    }
}
