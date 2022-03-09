package com.pam.spotassets.model

data class S3KeywordResponseModel(
    val keyword: String,
    val s3_buckets: List<String>
)