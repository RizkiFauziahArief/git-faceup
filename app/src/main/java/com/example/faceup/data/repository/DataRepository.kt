package com.example.faceup.data.repository

import android.util.Log
import com.example.faceup.network.models.login.LoginBody
import com.example.faceup.network.models.login.LoginResponse
import com.example.faceup.network.models.predict.PredictResponse
import com.example.faceup.network.models.register.RegisterBody
import com.example.faceup.network.models.register.RegisterResponse
import com.example.faceup.network.service.ApiService
import com.example.faceup.utils.wrapper.Resource
import com.example.faceup.utils.wrapper.proceed
import okhttp3.MultipartBody

class DataRepository (private val apiService: ApiService) {

    suspend fun postLogin(loginBody: LoginBody) : Resource<LoginResponse> =
        proceed {
            apiService.postLogin(loginBody)
        }

    suspend fun postRegist (registerBody: RegisterBody) : Resource<RegisterResponse> =
        proceed {
            apiService.postRegister(registerBody)
        }

    suspend fun postPredict(image: MultipartBody.Part) : Resource<PredictResponse> =
        proceed {
            apiService.postPredict(image)

        }
}