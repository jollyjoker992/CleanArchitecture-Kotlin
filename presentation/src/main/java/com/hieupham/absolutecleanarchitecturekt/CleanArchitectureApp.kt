package com.hieupham.absolutecleanarchitecturekt

import com.hieupham.absolutecleanarchitecturekt.di.DaggerApplication


class CleanArchitectureApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this).build().inject(this)
    }

}