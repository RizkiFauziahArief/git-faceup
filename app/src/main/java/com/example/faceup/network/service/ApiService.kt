package com.example.faceup.network.service

import com.example.faceup.network.models.login.LoginBody
import com.example.faceup.network.models.login.LoginResponse
import com.example.faceup.network.models.predict.PredictResponse
import com.example.faceup.network.models.register.RegisterBody
import com.example.faceup.network.models.register.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface  ApiService {

    @Multipart
    @POST("predict")
    suspend fun postPredict(
        @Part image : MultipartBody.Part
    ) : PredictResponse

    @POST(LOGIN)
    suspend fun postLogin(@Body loginbody : LoginBody) : LoginResponse

    @POST(REGISTER)
    suspend fun postRegister(@Body registerbody : RegisterBody) : RegisterResponse

    companion object{
        const val LOGIN = "login"
        const val REGISTER = "register"
    }
}
