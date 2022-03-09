package com.pam.spotassets.model

data class S3BucketsResponseModel(
    val package_id: String,
    val s3_buckets: List<String>
)