package com.luckly.eraser.ui.activity.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.luckly.eraser.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            finish()
            overridePendingTransition(R.anim.visible_effects, R.anim.invisible_effects)
        }, 2500)
    }
}