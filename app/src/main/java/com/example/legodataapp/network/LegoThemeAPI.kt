package com.example.legodataapp.network

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstanceLegoTheme {
    private const val BASE_URL = "https://rebrickable.com/api/v3/lego/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val setService: ISetService by lazy {
        retrofit.create(ISetService::class.java)
    }

    suspend fun getThemeName(themeId: Int): String{
        return try {
            val themeResponse = setService.getTheme((themeId))
            themeResponse.name
        } catch(e: Exception) {
            "$themeId"
        }
    }
}