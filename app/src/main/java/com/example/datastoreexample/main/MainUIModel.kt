package com.example.datastoreexample.main

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.datastoreexample.interactors.ObservableString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainUIModel(context: Context) : ViewModel() {

    // Observables
    val nameObservable = ObservableString()
    val emailObservable = ObservableString()
    val isRegisterUser = ObservableBoolean()

    val isSaveButtonClicked = MutableLiveData<Boolean>()

    lateinit var user:Flow<User>

    private val dataStorePreference = context.createDataStore(
        name = "my_pref"
    )

    init {
        viewModelScope.launch {
            readDataFromPreference()
        }
    }

    /**
     * Read the data from the preference using DataStore API
     */
    private fun readDataFromPreference() {
        user = dataStorePreference.data.map { value: Preferences ->
            val name = value[PreferenceKeys.KEY_NAME] ?: "Test"
            val email = value[PreferenceKeys.KEY_EMAIL] ?: "test@gmail.com"
            val isRegisterUser = value[PreferenceKeys.KEY_IS_REGISTER] ?: false
            User(name, email, isRegisterUser)
        }
    }


    /**
     * Trigger this method on clicking on "Save" button
     */
    fun onSaveButtonClick() {
        viewModelScope.launch {
            writeDataToPreference()
        }
    }

    /**
     * Write the data into the preference using DataStore API
     */
    private suspend fun writeDataToPreference() {
        dataStorePreference.edit { mutablePreferences ->
            mutablePreferences[PreferenceKeys.KEY_NAME] = nameObservable.trimmed
            mutablePreferences[PreferenceKeys.KEY_EMAIL] = emailObservable.trimmed
            mutablePreferences[PreferenceKeys.KEY_IS_REGISTER] = isRegisterUser.get()
        }
        isSaveButtonClicked.value = true
    }


    /**
     * Object contains all keys required to read/write into preference using DataStore.
     */
    private object PreferenceKeys {
        val KEY_NAME = stringPreferencesKey("name")
        val KEY_EMAIL = stringPreferencesKey("email")
        val KEY_IS_REGISTER = booleanPreferencesKey("isRegister")
    }

    data class User(
        val name: String,
        val email: String,
        val isRegister: Boolean
    )

}