package com.example.faceup.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.faceup.data.repository.DataRepository
import com.example.faceup.network.models.predict.PredictResponse
import com.example.faceup.utils.wrapper.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class DetailViewModel(private val repository: DataRepository) : ViewModel() {

    private var _detail = MutableLiveData<Resource<PredictResponse>>()
    val detail : LiveData<Resource<PredictResponse>> get() = _detail

    fun postPredictDetail (image : MultipartBody.Part){
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val response = repository.postPredict(image)
            viewModelScope.launch (Dispatchers.Main){
                _detail.postValue(response)
                Log.e("PostPredict", "berhasil")
            }
        }
    }
}