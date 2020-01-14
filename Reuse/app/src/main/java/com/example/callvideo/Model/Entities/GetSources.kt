package com.example.callvideo.Model;


import com.google.gson.annotations.SerializedName

data class GetSources(
        @SerializedName("status") val status: String?,
        @SerializedName("sources") val sources: ArrayList<Source>?
)