package com.pam.spotassets.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pam.spotassets.R
import com.pam.spotassets.core.ApiHelper
import com.pam.spotassets.model.AllAssetsResponseModel
import com.pam.spotassets.model.Resource
import com.pam.spotassets.model.URLParamsResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    val chosenPackage = MutableLiveData<String>()
    var expandableListDetail = HashMap<String, Any>()
    var menuChosen: String = "Wordlist"
    val carouselTexts = intArrayOf(R.string.wordlist, R.string.hosts, R.string.s3_buckets, R.string.all_assets, R.string.url_params, R.string.apps, R.string.subdomains, R.string.urls, R.string.search_for_s3)

    enum class Type(val value: String) {
        HOSTS("Hosts"),
        WORDLIST("Wordlist"),
        S3_BUCKETS("S3 Buckets"),
        SUBDOMAINS("Subdomains"),
        URLS("URLs"),
        SEARCH_FOR_S3("Search For S3")
    }

    fun setChosenPackage(packageName: String) {
        chosenPackage.value = packageName
    }

    fun getChosenPackage(): String? {
        return chosenPackage.value
    }

    private val apiHelper: ApiHelper by lazy { ApiHelper() }


    fun getAllAssets(packageId: String): MutableLiveData<Resource<AllAssetsResponseModel>>{
        val allAssetsResponseModel = MutableLiveData<Resource<AllAssetsResponseModel>>()
        allAssetsResponseModel.value = Resource.loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            allAssetsResponseModel.postValue(apiHelper.getAllAssets(packageId))
        }
        return allAssetsResponseModel
    }

    fun getUrlParams(packageId: String): MutableLiveData<Resource<URLParamsResponseModel>>{
        val urlParamsResponseModel = MutableLiveData<Resource<URLParamsResponseModel>>()
        urlParamsResponseModel.value = Resource.loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            urlParamsResponseModel.postValue(apiHelper.getUrlParams(packageId))
        }
        return urlParamsResponseModel
    }

    fun getSelectedAssets(type: String, packageId: String): MutableLiveData<Resource<HashMap<String, Any>>> {
        val responseModel = MutableLiveData<Resource<HashMap<String, Any>>>()
        responseModel.value = Resource.loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            when(type)
            {
                Type.HOSTS.value -> responseModel.postValue(apiHelper.getHosts(packageId))
                Type.WORDLIST.value -> responseModel.postValue(apiHelper.getWordlist(packageId))
                Type.S3_BUCKETS.value -> responseModel.postValue(apiHelper.getS3Buckets(packageId))
                Type.SUBDOMAINS.value -> responseModel.postValue(apiHelper.getSubdomains(packageId))
                Type.URLS.value -> responseModel.postValue(apiHelper.getUrls(packageId))
                Type.SEARCH_FOR_S3.value -> responseModel.postValue(apiHelper.getS3Keyword(packageId))
            }
        }
        return responseModel
    }
}