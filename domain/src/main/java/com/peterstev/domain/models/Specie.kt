package com.peterstev.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Specie(
    @SerializedName("average_lifespan")
    val averageLifespan: String?,
    @SerializedName("classification")
    val classification: String?,
    @SerializedName("created")
    val created: String?,
    @SerializedName("designation")
    val designation: String?,
    @SerializedName("homeworld")
    val homeworld: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("name")
    val name: String?,
) : Serializable