package com.devilsvirtue.cryptocom.ui.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devilsvirtue.cryptocom.data.CurrencyUseCase
import com.devilsvirtue.cryptocom.ui.uio.CurrencyUio
import com.devilsvirtue.cryptocom.util.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
    private val currencyUseCase: CurrencyUseCase,
) : ViewModel() {
    private val currencyData: MutableLiveData<List<CurrencyUio>> =
        MutableLiveData<List<CurrencyUio>>()
    val currencyList: LiveData<List<CurrencyUio>>
        get() = currencyData
    /**
     * Personally, I would put my repos/usecase in here to get the data,
     * so that I don't need an external list from the activity
     */
}