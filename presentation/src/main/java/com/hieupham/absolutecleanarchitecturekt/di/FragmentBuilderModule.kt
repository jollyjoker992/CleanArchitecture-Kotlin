package com.hieupham.absolutecleanarchitecturekt.di

import com.hieupham.absolutecleanarchitecturekt.feature.transactiondetail.TransactionDetailFragment
import com.hieupham.absolutecleanarchitecturekt.feature.transactiondetail.TransactionDetailModule
import com.hieupham.absolutecleanarchitecturekt.feature.transactionlist.TransactionListFragment
import com.hieupham.absolutecleanarchitecturekt.feature.transactionlist.TransactionListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [TransactionListModule::class])
    @FragmentScope
    internal abstract fun bindTransactionListFragment(): TransactionListFragment

    @ContributesAndroidInjector(modules = [TransactionDetailModule::class])
    @FragmentScope
    internal abstract fun bindTransactionDetailFragment(): TransactionDetailFragment
}