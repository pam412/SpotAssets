package com.pam.spotassets.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pam.spotassets.core.ApiHelper
import com.pam.spotassets.model.AllAssetsResponseModel
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val chosenPackage = MutableLiveData<String>()

    fun setChosenPackage(packageName: String) {
        chosenPackage.value = packageName
    }

    fun getChosenPackage(): String? {
        return chosenPackage.value
    }

    private val apiHelper: ApiHelper by lazy { ApiHelper() }
    val allAssetsResponseModel: MutableLiveData<AllAssetsResponseModel> by lazy { MutableLiveData<AllAssetsResponseModel>() }

    fun getAllAssets(packageId: String) {
        viewModelScope.launch {
            apiHelper.getAllAssets(packageId)
            Log.v("TAG", allAssetsResponseModel.toString())
        }
    }
}