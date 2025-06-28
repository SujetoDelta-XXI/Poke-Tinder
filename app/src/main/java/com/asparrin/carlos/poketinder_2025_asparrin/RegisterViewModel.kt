package com.asparrin.carlos.poketinder_2025_asparrin

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel(
    val context: Context
): ViewModel() {

    val inputsError = MutableLiveData<Boolean>()
    val passwordsNotMatch = MutableLiveData<Boolean>()
    val userAlreadyExists = MutableLiveData<Boolean>()
    val registerSuccess = MutableLiveData<Boolean>()

    private var sharedPreferencesRepository: SharedPreferencesRepository =
        SharedPreferencesRepository().also {
            it.setSharedPreference(context)
        }

    fun validateInputs(email: String, password: String, confirmPassword: String) {
        if (isEmptyInputs(email, password, confirmPassword)) {
            inputsError.postValue(true)
            return
        }

        if (password != confirmPassword) {
            passwordsNotMatch.postValue(true)
            return
        }

        val existingEmail = sharedPreferencesRepository.getUserEmail()
        if (existingEmail.isNotEmpty()) {
            userAlreadyExists.postValue(true)
            return
        }

        // Guardar el nuevo usuario
        sharedPreferencesRepository.saveUserEmail(email)
        sharedPreferencesRepository.saveUserPassword(password)
        registerSuccess.postValue(true)
    }

    private fun isEmptyInputs(email: String, password: String, confirmPassword: String) = 
        email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
} 