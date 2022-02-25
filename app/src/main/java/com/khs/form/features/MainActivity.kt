package com.khs.form.features

import android.os.Bundle
import android.view.WindowManager
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.khs.form.base.BaseActivity
import com.khs.form.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}