package com.devilsvirtue.cryptocom.di

import com.devilsvirtue.cryptocom.data.*
import com.devilsvirtue.cryptocom.util.DefaultDispatcher
import com.devilsvirtue.cryptocom.util.IoDispatcher
import com.devilsvirtue.cryptocom.util.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Provides
    fun provideCurrencyRepository(currencyDao: CurrencyDao): CurrencyRepository {
        return CurrencyRepositoryImpl(currencyDao)
    }

    @Provides
    fun provideCurrencyUseCase(repo: CurrencyRepository): CurrencyUseCase {
        return CurrencyUseCaseImpl(repo)
    }

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}