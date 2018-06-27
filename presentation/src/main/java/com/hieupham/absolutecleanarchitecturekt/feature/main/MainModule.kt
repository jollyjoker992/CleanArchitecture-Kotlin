package com.hieupham.absolutecleanarchitecturekt.feature.main

import com.hieupham.absolutecleanarchitecturekt.di.ActivityScope
import com.hieupham.absolutecleanarchitecturekt.di.FragmentBuilderModule
import com.hieupham.absolutecleanarchitecturekt.feature.Navigator
import dagger.Module
import dagger.Provides

@Module(includes = [FragmentBuilderModule::class])
class MainModule {

    @Provides
    @ActivityScope
    internal fun provideViewModel(): ViewModel {
        return MainViewModel()
    }

    @Provides
    @ActivityScope
    internal fun provideNavigator(activity: MainActivity): Navigator<MainActivity> {
        return Navigator(activity)
    }
}