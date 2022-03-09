package com.pam.spotassets.core

object ApiEndpoint {
    const val ALL_ASSETS: String = "api/{package_id}/all-assets/"
    const val WORDLIST: String = "api/{package_id}/wordlist/"
    const val HOSTS: String = "api/{package_id}/hosts/"
    const val S3_BUCKETS: String = "api/{package_id}/S3-buckets/"
    const val URL_PARAMS: String = "api/{package_id}/params/"
    const val APPS: String = "api/{domain_name}/apps/"
    const val SUBDOMAINS: String = "api/{domain_name}/subdomains/"
    const val URLS: String = "api/{domain_name}/urls/"
    const val S3_KEYWORD: String = "api/{keyword}/S3-keyword/"
}