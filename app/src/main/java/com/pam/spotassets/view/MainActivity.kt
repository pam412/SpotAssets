package com.pam.spotassets.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.pam.spotassets.R
import com.pam.spotassets.databinding.HomeFragmentBinding
import com.pam.spotassets.viewmodel.HomeViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showSoftKeyboard(view: View, hasFocus: Boolean) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (hasFocus) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
        else {
            imm.hideSoftInputFromWindow(view.windowToken, 0);
        }
    }


}