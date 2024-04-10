package com.example.legodataapp.data

import com.example.legodataapp.network.RetrofitInstance

class SetRepo {
    private val setService = RetrofitInstance.setService

    suspend fun getSets(): AllSet {
        return setService.getSets()
    }
}