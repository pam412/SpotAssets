package com.pam.spotassets.model

data class URLParamsResponseModel(
    val package_id: String,
    val url_params: UrlParams
) {
    data class UrlParams(
        val RID: List<String>,
        val VAA: List<String>,
        val color: List<String>,
        val id: List<String>,
        val id_type: List<String>,
        val qr: List<String>,
        val rdid: List<String>,
        val sdk_version: List<String>,
        val user_code: List<String>,
        val v2: List<String>
    )
}