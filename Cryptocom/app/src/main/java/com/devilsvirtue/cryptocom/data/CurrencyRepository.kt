package com.devilsvirtue.cryptocom.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CurrencyRepository {
    suspend fun loadAllCurrency(): Flow<List<Currency>>
    suspend fun insertAllCurrency(list: List<Currency>)
    suspend fun sortCurrency(isAsc: Boolean?): Flow<List<Currency>>
}

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyDao: CurrencyDao,
) : CurrencyRepository {
    override suspend fun loadAllCurrency(): Flow<List<Currency>> {
        return currencyDao.loadAll()
    }

    override suspend fun sortCurrency(isAsc: Boolean?): Flow<List<Currency>> {
        return currencyDao.sortCurrencyByName(isAsc)
    }

    override suspend fun insertAllCurrency(list: List<Currency>) {
        return currencyDao.insertAll(list)
    }
}