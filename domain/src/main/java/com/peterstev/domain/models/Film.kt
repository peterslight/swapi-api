package com.peterstev.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Film(
    @SerializedName("created")
    val created: String?,
    @SerializedName("director")
    val director: String?,
    @SerializedName("opening_crawl")
    val openingCrawl: String?,
    @SerializedName("producer")
    val producer: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?
): Serializable