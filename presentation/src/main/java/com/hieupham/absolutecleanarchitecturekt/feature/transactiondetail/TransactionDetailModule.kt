package com.hieupham.absolutecleanarchitecturekt.feature.transactiondetail

import android.support.v4.app.FragmentActivity
import com.hieupham.absolutecleanarchitecturekt.di.FragmentScope
import com.hieupham.absolutecleanarchitecturekt.feature.DialogManager
import com.hieupham.absolutecleanarchitecturekt.model.mapper.CompositeTransactionModelViewMapper
import com.hieupham.domain.interactor.usecase.GetTransactionUseCase
import dagger.Module
import dagger.Provides

@Module
class TransactionDetailModule {

    @Provides
    @FragmentScope
    internal fun provideViewModel(useCase: GetTransactionUseCase,
            mapper: CompositeTransactionModelViewMapper): ViewModel {
        return TransactionDetailViewModel(useCase, mapper)
    }

    @Provides
    @FragmentScope
    internal fun provideDialogManager(fragment: TransactionDetailFragment): DialogManager {
        return DialogManager(fragment.activity as FragmentActivity)
    }
}