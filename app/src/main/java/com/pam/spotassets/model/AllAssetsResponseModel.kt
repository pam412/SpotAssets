package com.pam.spotassets.model

data class AllAssetsResponseModel(
    val assets: HashMap<String, Any> = HashMap(),
    val package_id: String? = null,
    val details: String? = null
)