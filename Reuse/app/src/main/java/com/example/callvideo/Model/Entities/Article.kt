package com.example.callvideo.Model;

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Article(
        @SerializedName("source") val source: Source?,
        @SerializedName("author") val author: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("url") val url: String?,
        @SerializedName("content") val content: String?,
        @SerializedName("urlToImage") val urlToImage: String?,
        @SerializedName("publishedAt") val publishedAt: String?
) : Serializable