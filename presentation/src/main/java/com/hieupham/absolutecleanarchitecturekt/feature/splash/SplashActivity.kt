package com.hieupham.absolutecleanarchitecturekt.feature.splash

import android.os.Bundle
import android.os.Handler
import com.hieupham.absolutecleanarchitecturekt.R
import com.hieupham.absolutecleanarchitecturekt.feature.BaseAppCompatActivity
import com.hieupham.absolutecleanarchitecturekt.feature.BaseViewModel
import com.hieupham.absolutecleanarchitecturekt.feature.Navigator
import com.hieupham.absolutecleanarchitecturekt.feature.Navigator.Companion.RIGHT_LEFT
import com.hieupham.absolutecleanarchitecturekt.feature.main.MainActivity
import javax.inject.Inject

class SplashActivity : BaseAppCompatActivity() {

    @Inject
    internal lateinit var navigator: Navigator<SplashActivity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            navigator.anim(RIGHT_LEFT).startActivity(MainActivity::class.java)
            navigator.finishActivity()
        }, 2000)
    }

    override fun layoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun viewModel(): BaseViewModel? {
        return null
    }
}