package com.example.responsi1.repository

import com.example.responsi1.api.ApiConfig
import com.example.responsi1.api.FootballService
import com.example.responsi1.api.WOLVES_TEAM_ID
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TeamRepository {
    private val retrofit: Retrofit by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
            
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val footballService: FootballService by lazy {
        retrofit.create(FootballService::class.java)
    }

    suspend fun getTeamInfo() = footballService.getTeamInfo(
        WOLVES_TEAM_ID,
        ApiConfig.API_TOKEN
    )
}