package com.pam.spotassets.model

data class AppsResponseModel(
    val packages: List<Package>
) {
    data class Package(
        val app_name: String,
        val app_version: String,
        val package_id: String
    )
}