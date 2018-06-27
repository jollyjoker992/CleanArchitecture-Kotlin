package com.hieupham.absolutecleanarchitecturekt

import android.app.Application
import com.hieupham.absolutecleanarchitecturekt.di.ActivityBuilderModule
import com.hieupham.data.NetworkModule
import com.hieupham.data.RepositoryModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class,
    ActivityBuilderModule::class,
    RepositoryModule::class, NetworkModule::class])
@Singleton
interface AppComponent : AndroidInjector<CleanArchitectureApp> {


    @Component.Builder
    interface Builder {

        fun application(app: Application): Builder

        fun build(): AppComponent

    }

}