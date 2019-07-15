package com.example.expenseapp.activities

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import com.example.expenseapp.R


class SplashActivity : Activity() {

    private val SPLASH_TIME_OUT = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable {
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)

            finish()
        }, SPLASH_TIME_OUT.toLong())
    }
}
