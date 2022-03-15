package com.pam.spotassets.core

import com.pam.spotassets.BuildConfig
import com.pam.spotassets.model.AllAssetsResponseModel
import com.pam.spotassets.model.AppsResponseModel
import com.pam.spotassets.model.URLParamsResponseModel
import retrofit2.http.GET
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
    ): HashMap<String, Any>

    @GET(ApiEndpoint.S3_BUCKETS)
    suspend fun getS3Buckets(
        @Header("X-Access-Token") apiKey: String = API_KEY,
        @Path("package_id") packageId: String
    ): HashMap<String, Any>

    @GET(ApiEndpoint.S3_KEYWORD)
    suspend fun getS3Keyword(
        @Header("X-Access-Token") apiKey: String = API_KEY,
        @Path("keyword") keyword: String
    ): HashMap<String, Any>

    @GET(ApiEndpoint.SUBDOMAINS)
    suspend fun getSubdomains(
        @Header("X-Access-Token") apiKey: String = API_KEY,
        @Path("domain_name") domainName: String
    ): HashMap<String, Any>

    @GET(ApiEndpoint.URLS)
    suspend fun getUrls(
        @Header("X-Access-Token") apiKey: String = API_KEY,
        @Path("domain_name") domainName: String
    ): HashMap<String, Any>

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
    ): HashMap<String, Any>

    companion object {
        const val API_KEY: String = BuildConfig.API_KEY
    }
}
