package com.hieupham.absolutecleanarchitecturekt.di

import android.content.Context
import android.support.v4.app.Fragment
import dagger.android.support.AndroidSupportInjection

abstract class DaggerSupportFragment : Fragment() {

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}