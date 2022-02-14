package com.devilsvirtue.cryptocom.data

import com.devilsvirtue.cryptocom.ui.uio.CurrencyUio
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface CurrencyUseCase {
    suspend fun insertAllCurrency(list: List<Currency>)
    suspend fun loadCurrencyByName(isAsc: Boolean?): Flow<List<CurrencyUio>>
}

class CurrencyUseCaseImpl @Inject constructor(
    private val currencyRepo: CurrencyRepository,
) : CurrencyUseCase {

    override suspend fun insertAllCurrency(list: List<Currency>) {
        return currencyRepo.insertAllCurrency(list)
    }

    override suspend fun loadCurrencyByName(isAsc: Boolean?): Flow<List<CurrencyUio>> {
        return flow {
            currencyRepo.loadCurrencyByName(isAsc).collect { boList ->
                emit(
                    boList.map {
                        it.mapBoToUio()
                    }
                )
            }
        }
    }
}