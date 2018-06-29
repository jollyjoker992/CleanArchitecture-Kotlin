package com.hieupham.absolutecleanarchitecturekt.feature.splash

import com.hieupham.absolutecleanarchitecturekt.di.ActivityScope
import com.hieupham.absolutecleanarchitecturekt.feature.Navigator
import dagger.Module
import dagger.Provides

@Module
class SplashModule {

    @ActivityScope
    @Provides
    internal fun provideNavigator(activity: SplashActivity): Navigator<SplashActivity> {
        return Navigator(activity)
    }
}