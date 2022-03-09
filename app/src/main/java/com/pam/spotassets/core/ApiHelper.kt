package com.pam.spotassets.core

import com.pam.spotassets.model.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiHelper {
    private val baseUrl = "https://osint.bevigil.com/"
    private var service: ApiService? = null

    init {
        getRetrofitBuilder()
    }
    private fun getRetrofitBuilder() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        service = retrofit.create(ApiService::class.java)
    }

    suspend fun getAllAssets(packageId: String): AllAssetsResponseModel? {
        getRetrofitBuilder()
        return service?.getAllAssets(packageId=packageId)
    }

    suspend fun getWordlist(packageId: String): WordlistResponseModel? {
        getRetrofitBuilder()
        return service?.getWordlist(packageId=packageId)
    }

    suspend fun getS3Buckets(packageId: String): S3BucketsResponseModel? {
        getRetrofitBuilder()
        return service?.getS3Buckets(packageId=packageId)
    }

    suspend fun getS3Keyword(keyword: String): S3KeywordResponseModel? {
        getRetrofitBuilder()
        return service?.getS3Keyword(keyword=keyword)
    }

    suspend fun getSubdomains(domainName: String): SubdomainsResponseModel? {
        getRetrofitBuilder()
        return service?.getSubdomains(domainName= domainName)
    }

    suspend fun getUrls(domainName: String): URLSResponseModel? {
        getRetrofitBuilder()
        return service?.getUrls(domainName= domainName)
    }

    suspend fun getUrlParams(packageId: String): URLParamsResponseModel? {
        getRetrofitBuilder()
        return service?.getUrlParams(packageId=packageId)
    }

    suspend fun getApps(domainName: String): AppsResponseModel? {
        getRetrofitBuilder()
        return service?.getApps(domainName= domainName)
    }

    suspend fun getHosts(packageId: String): HostsResponseModel? {
        getRetrofitBuilder()
        return service?.getHosts(packageId=packageId)
    }
}