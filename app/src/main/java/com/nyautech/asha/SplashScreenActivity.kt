package com.nyautech.asha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.nyautech.asha.util.Constanta.TIME_SPLASH

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val handler = Handler(mainLooper)

        handler.postDelayed({
            val intent = Intent(this,OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        },TIME_SPLASH)

    }
}