package com.hieupham.absolutecleanarchitecturekt.di

import com.hieupham.absolutecleanarchitecturekt.feature.main.MainActivity
import com.hieupham.absolutecleanarchitecturekt.feature.main.MainModule
import com.hieupham.absolutecleanarchitecturekt.feature.splash.SplashActivity
import com.hieupham.absolutecleanarchitecturekt.feature.splash.SplashModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * The [Module] class declares how to inject an Activity instance into corresponding
 * {@link Module}
 */
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    @ActivityScope
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [SplashModule::class])
    @ActivityScope
    internal abstract fun bindSplashActivity(): SplashActivity
}