package com.pam.spotassets.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import java.util.*


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        }, 3000)
    }
}