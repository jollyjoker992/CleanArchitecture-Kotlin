package com.hieupham.absolutecleanarchitecturekt.feature.main

import android.os.Bundle
import com.hieupham.absolutecleanarchitecturekt.R
import com.hieupham.absolutecleanarchitecturekt.feature.BaseAppCompatActivity
import com.hieupham.absolutecleanarchitecturekt.feature.BaseViewModel
import com.hieupham.absolutecleanarchitecturekt.feature.Navigator
import com.hieupham.absolutecleanarchitecturekt.feature.transactionlist.TransactionListFragment
import javax.inject.Inject

class MainActivity : BaseAppCompatActivity() {

    @Inject
    internal lateinit var viewModel: ViewModel

    @Inject
    internal lateinit var navigator: Navigator<MainActivity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.replaceFragment(R.id.layout_root, TransactionListFragment.newInstance(), false)
    }

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun viewModel(): BaseViewModel? {
        return viewModel
    }


}
