package com.peterstev.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Result(
    @SerializedName("birth_year")
    val birthYear: String?,
    @SerializedName("created")
    val created: String?,
    @SerializedName("films")
    val films: List<String>?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("height")
    val height: String?,
    @SerializedName("homeworld")
    val homeworld: String?,
    @SerializedName("skin_color")
    val skinColor: String?,
    @SerializedName("eye_color")
    val eyeColor: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("species")
    val species: List<String>?,
) : Serializable