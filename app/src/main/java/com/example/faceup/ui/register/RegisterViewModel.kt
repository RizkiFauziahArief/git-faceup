package com.example.faceup.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.faceup.data.repository.DataRepository
import com.example.faceup.network.models.register.RegisterBody
import com.example.faceup.network.models.register.RegisterResponse
import com.example.faceup.utils.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: DataRepository)  : ViewModel(){

    private var _regist = MutableLiveData<Resource<RegisterResponse>>()
    val regist : LiveData<Resource<RegisterResponse>> = _regist

    fun postRegist (registerBody: RegisterBody) {
        viewModelScope.launch (Dispatchers.IO){
            delay(1000)
            viewModelScope.launch (Dispatchers.Main){
                _regist.postValue(repository.postRegist(registerBody))
            }
        }

    }
}
