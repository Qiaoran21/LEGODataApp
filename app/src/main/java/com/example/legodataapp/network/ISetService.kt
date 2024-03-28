package com.example.legodataapp.network

import com.example.legodataapp.data.AllSet
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "c050c14bcfade14ee60444055abec162"

interface ISetService {
    @GET("sets")
    suspend fun getSets(
        @Query("key") key: String = API_KEY
    ): AllSet
}