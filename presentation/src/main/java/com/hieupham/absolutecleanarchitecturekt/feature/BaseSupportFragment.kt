package com.hieupham.absolutecleanarchitecturekt.feature

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.hieupham.absolutecleanarchitecturekt.di.DaggerSupportFragment

abstract class BaseSupportFragment : DaggerSupportFragment() {

    protected var rootView: View? = null
    private lateinit var unbinder: Unbinder

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (viewModel() != null)
            lifecycle.addObserver(viewModel()!!)
        observe@ observe()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        if (rootView == null) rootView = inflater.inflate(layoutRes(), container, false)
        unbinder = ButterKnife.bind(this, rootView!!)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

    /**
     * Define the layout res id can be used to inflate [View]
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
    protected open fun initComponents() {}

    /**
     * Observe data change from ViewModel
     */
    protected open fun observe() {}
}