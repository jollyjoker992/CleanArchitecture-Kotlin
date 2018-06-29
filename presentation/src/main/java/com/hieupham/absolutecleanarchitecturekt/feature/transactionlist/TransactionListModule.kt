package com.hieupham.absolutecleanarchitecturekt.feature.transactionlist

import com.hieupham.absolutecleanarchitecturekt.di.FragmentScope
import com.hieupham.absolutecleanarchitecturekt.feature.DialogManager
import com.hieupham.absolutecleanarchitecturekt.feature.Navigator
import com.hieupham.absolutecleanarchitecturekt.model.mapper.CompositeTransactionModelViewMapper
import com.hieupham.absolutecleanarchitecturekt.util.common.IntervalScheduler
import com.hieupham.domain.interactor.usecase.GetTransactionsUseCase
import dagger.Module
import dagger.Provides

@Module
class TransactionListModule {

    @Provides
    @FragmentScope
    internal fun provideViewModel(getTransactionsUseCase: GetTransactionsUseCase,
            intervalScheduler: IntervalScheduler,
            mapper: CompositeTransactionModelViewMapper): ViewModel {
        return TransactionListViewModel(getTransactionsUseCase, intervalScheduler, mapper)
    }

    @Provides
    @FragmentScope
    internal fun provideNavigator(
            fragment: TransactionListFragment): Navigator<TransactionListFragment> {
        return Navigator(fragment)
    }

    @Provides
    @FragmentScope
    internal fun provideAdapter(): TransactionListAdapter {
        return TransactionListAdapter()
    }

    @Provides
    @FragmentScope
    internal fun provideDialogManager(fragment: TransactionListFragment): DialogManager {
        return DialogManager(fragment.activity!!)
    }
}