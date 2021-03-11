package com.peterstev.domain.models

import com.google.gson.annotations.SerializedName

data class Planet(
    @SerializedName("created")
    val created: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("population")
    val population: String?,
)