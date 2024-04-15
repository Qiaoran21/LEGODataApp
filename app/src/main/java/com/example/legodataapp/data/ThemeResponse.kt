package com.example.legodataapp.data

import com.google.gson.annotations.SerializedName

data class ThemeResponse(
    @SerializedName("results")
    val id: Int,
    val parent_id: Int,
    val name: String
)