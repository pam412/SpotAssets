package com.pam.spotassets.model

import com.google.gson.annotations.SerializedName

data class AllAssetsResponseModel(
    val host: Host,
    val package_id: String
)

data class Host(
    @SerializedName("AWS URL") val aws_url: List<String>,
    @SerializedName("Firebase URL") val firebase_url: List<String>,
    @SerializedName("IP Address disclosure") val ip_address_disclosure: List<String>,
    val email: List<String>,
    val file_path: List<String>,
    val filename: List<String>,
    val host: List<String>,
    val ip_url: List<String>,
    val relative_endpoint: List<String>,
    val rest_api: List<String>,
    val url: List<String>
)