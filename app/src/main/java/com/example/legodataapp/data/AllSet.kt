package com.example.legodataapp.data

import com.google.gson.annotations.SerializedName

data class AllSet(
    @SerializedName("results")
    var legoSetList: List<LegoSet>
)