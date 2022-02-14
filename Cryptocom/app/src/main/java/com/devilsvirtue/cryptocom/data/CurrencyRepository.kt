package com.devilsvirtue.cryptocom.data

import com.devilsvirtue.cryptocom.domain.bo.CurrencyBo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface CurrencyRepository {
    suspend fun insertAllCurrency(list: List<Currency>)
    suspend fun loadCurrencyByName(isAsc: Boolean?): Flow<List<CurrencyBo>>
}

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyDao: CurrencyDao,
) : CurrencyRepository {
    override suspend fun loadCurrencyByName(isAsc: Boolean?): Flow<List<CurrencyBo>> {
        return flow {
            currencyDao.loadCurrencyByName(isAsc).collect { dbList ->
                emit(
                    dbList.map {
                        it.mapDbToBo()
                    }
                )
            }
        }
    }

    override suspend fun insertAllCurrency(list: List<Currency>) {
        return currencyDao.insertAll(list)
    }
}