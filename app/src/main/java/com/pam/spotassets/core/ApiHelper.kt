package com.pam.spotassets.core

import com.pam.spotassets.model.AllAssetsResponseModel
import com.pam.spotassets.model.AppsResponseModel
import com.pam.spotassets.model.Resource
import com.pam.spotassets.model.URLParamsResponseModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiHelper {
    private val baseUrl = ApiEndpoint.BASE_URL
    private val service: ApiService by lazy{ getRetrofitBuilder() }

    private fun getRetrofitBuilder(): ApiService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    suspend fun getAllAssets(packageId: String): Resource<AllAssetsResponseModel> {
        return safeApiCall { service.getAllAssets(packageId = packageId) }
    }

    suspend fun getWordlist(packageId: String): Resource<HashMap<String, Any>> {
        return safeApiCall { service.getWordlist(packageId = packageId) }
    }

    suspend fun getS3Buckets(packageId: String): Resource<HashMap<String, Any>> {
        return safeApiCall { service.getS3Buckets(packageId = packageId) }
    }

    suspend fun getS3Keyword(keyword: String): Resource<HashMap<String, Any>> {
        return safeApiCall { service.getS3Keyword(keyword=keyword) }
    }

    suspend fun getSubdomains(domainName: String): Resource<HashMap<String, Any>> {
        return safeApiCall { service.getSubdomains(domainName= domainName) }
    }

    suspend fun getUrls(domainName: String): Resource<HashMap<String, Any>> {
        return safeApiCall { service.getUrls(domainName= domainName) }
    }

    suspend fun getUrlParams(packageId: String): Resource<URLParamsResponseModel> {
        return safeApiCall { service.getUrlParams(packageId=packageId) }
    }

    //Api integration of "Apps" is done.
    suspend fun getApps(domainName: String): Resource<AppsResponseModel> {
        return safeApiCall { service.getApps(domainName= domainName) }
    }

    suspend fun getHosts(packageId: String): Resource<HashMap<String, Any>> {
        return safeApiCall { service.getHosts(packageId=packageId) }
    }
}