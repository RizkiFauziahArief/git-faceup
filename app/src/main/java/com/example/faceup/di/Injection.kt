package com.example.faceup.di

import android.content.Context
import com.example.faceup.data.repository.DataRepository
import com.example.faceup.network.service.ApiConfig

object Injection {
    fun provideRepository(context: Context): DataRepository {
        val apiService = ApiConfig.getApiService()
        return DataRepository(apiService)
    }
}