package com.devilsvirtue.cryptocom.di

import android.content.Context
import com.devilsvirtue.cryptocom.data.CurrencyDao
import com.devilsvirtue.cryptocom.data.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
    ): CurrencyDatabase {
        return CurrencyDatabase.getInstance(appContext)
    }

    @Provides
    fun provideCurrencyDao(database: CurrencyDatabase): CurrencyDao {
        return database.currencyDao()
    }
}
