package com.hieupham.absolutecleanarchitecturekt.feature.transactiondetail

import com.hieupham.absolutecleanarchitecturekt.di.FragmentScope
import com.hieupham.domain.interactor.usecase.GetTransactionUseCase
import dagger.Module
import dagger.Provides

@Module
class TransactionDetailModule {

    @Provides
    @FragmentScope
    internal fun provideViewModel(useCase: GetTransactionUseCase): ViewModel {
        return TransactionDetailViewModel(useCase)
    }
}