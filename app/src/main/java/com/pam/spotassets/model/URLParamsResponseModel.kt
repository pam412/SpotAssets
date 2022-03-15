package com.pam.spotassets.model

data class URLParamsResponseModel(
    val package_id: String? = null,
    val url_params: HashMap<String, Any> = HashMap()
)
