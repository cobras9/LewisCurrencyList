package com.devilsvirtue.cryptocom.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface CurrencyUseCase {
    suspend fun loadAllCurrency(): Flow<List<Currency>>
    suspend fun insertAllCurrency(list: List<Currency>)
    suspend fun sortCurrency(isAsc: Boolean?): Flow<List<Currency>>
}

class CurrencyUseCaseImpl @Inject constructor(
    private val currencyRepo: CurrencyRepository,
) : CurrencyUseCase {
    override suspend fun loadAllCurrency(): Flow<List<Currency>> {
        return currencyRepo.loadAllCurrency()
    }

    override suspend fun insertAllCurrency(list: List<Currency>) {
        return currencyRepo.insertAllCurrency(list)
    }

    override suspend fun sortCurrency(isAsc: Boolean?): Flow<List<Currency>> {
        return currencyRepo.sortCurrency(isAsc)
    }
}