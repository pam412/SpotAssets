package com.pam.spotassets.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.pam.spotassets.R

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
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


}