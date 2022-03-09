package com.pam.spotassets.core

import retrofit2.http.GET
import retrofit2.http.Query
import com.pam.spotassets.core.ApiEndpoint
import com.pam.spotassets.model.*
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    @GET(ApiEndpoint.ALL_ASSETS)
    suspend fun getAllAssets(
        @Header("X-Access-Token") apiKey: String = API_KEY,
        @Path("package_id") packageId: String
    ): AllAssetsResponseModel

    @GET(ApiEndpoint.WORDLIST)
    suspend fun getWordlist(
        @Header("X-Access-Token") apiKey: String = API_KEY,
        @Path("package_id") packageId: String
    ): WordlistResponseModel

    @GET(ApiEndpoint.S3_BUCKETS)
    suspend fun getS3Buckets(
        @Header("X-Access-Token") apiKey: String = API_KEY,
        @Path("package_id") packageId: String
    ): S3BucketsResponseModel

    @GET(ApiEndpoint.S3_KEYWORD)
    suspend fun getS3Keyword(
        @Header("X-Access-Token") apiKey: String = API_KEY,
        @Path("keyword") keyword: String
    ): S3KeywordResponseModel

    @GET(ApiEndpoint.SUBDOMAINS)
    suspend fun getSubdomains(
        @Header("X-Access-Token") apiKey: String = API_KEY,
        @Path("domain_name") domainName: String
    ): SubdomainsResponseModel

    @GET(ApiEndpoint.URLS)
    suspend fun getUrls(
        @Header("X-Access-Token") apiKey: String = API_KEY,
        @Path("domain_name") domainName: String
    ): URLSResponseModel

    @GET(ApiEndpoint.URL_PARAMS)
    suspend fun getUrlParams(
        @Header("X-Access-Token") apiKey: String = API_KEY,
        @Path("package_id") packageId: String
    ): URLParamsResponseModel

    @GET(ApiEndpoint.APPS)
    suspend fun getApps(
        @Header("X-Access-Token") apiKey: String = API_KEY,
        @Path("domain_name") domainName: String
    ): AppsResponseModel

    @GET(ApiEndpoint.HOSTS)
    suspend fun getHosts(
        @Header("X-Access-Token") apiKey: String = API_KEY,
        @Path("package_id") packageId: String
    ): HostsResponseModel

    companion object {
        const val API_KEY = "Ct0jgwq8aVCQ5qaq"
    }
}
