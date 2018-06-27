package com.hieupham.absolutecleanarchitecturekt.feature.main

import android.os.Bundle
import com.hieupham.absolutecleanarchitecturekt.R
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
