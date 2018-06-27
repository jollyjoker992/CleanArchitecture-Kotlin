package com.hieupham.absolutecleanarchitecturekt.feature

import android.app.Activity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.ButterKnife
import com.hieupham.absolutecleanarchitecturekt.di.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector

abstract class BaseAppCompatActivity : DaggerAppCompatActivity(), HasSupportFragmentInjector {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel() != null) {
            lifecycle.addObserver(viewModel()!!)
        }
        setContentView(layoutRes())
        ButterKnife.bind(this)
        initComponents()
        observe()
    }

    /**
     * Define the layout res id can be used to [Activity.setContentView]
     *
     * @return the layout res id
     */
    @LayoutRes
    protected abstract fun layoutRes(): Int

    /**
     * Define the [BaseViewModel] instance
     *
     * @return the [BaseViewModel] instance
     */
    protected abstract fun viewModel(): BaseViewModel?

    /**
     * Init [View] components here. Such as set adapter for [RecyclerView], set listener
     * or anything else
     */
    protected fun initComponents() {}

    /**
     * Observe data change from ViewModel
     */
    protected fun observe() {}

}