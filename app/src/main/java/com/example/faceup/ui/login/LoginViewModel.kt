package com.example.faceup.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.faceup.data.repository.DataRepository
import com.example.faceup.network.models.login.LoginBody
import com.example.faceup.network.models.login.LoginResponse
import com.example.faceup.utils.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private val dataRepository: DataRepository): ViewModel() {

    private var _login = MutableLiveData<Resource<LoginResponse>>()
    val login : LiveData<Resource<LoginResponse>> get() = _login

    fun Postlogin(loginBody: LoginBody){
        Log.e("Login Fragmen" , "email: ${loginBody.email} password: ${loginBody.password}")
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val response = dataRepository.postLogin(loginBody)
            viewModelScope.launch (Dispatchers.Main){
                _login.postValue(response)
            }
        }
    }


}