package com.devilsvirtue.cryptocom

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devilsvirtue.cryptocom.data.Currency
import com.devilsvirtue.cryptocom.data.CurrencyUseCase
import com.devilsvirtue.cryptocom.ui.LoadingState
import com.devilsvirtue.cryptocom.ui.uio.CurrencyUio
import com.devilsvirtue.cryptocom.util.IoDispatcher
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val currencyUseCase: CurrencyUseCase,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val currencyData: MutableLiveData<List<CurrencyUio>> =
        MutableLiveData<List<CurrencyUio>>()
    val currencyList: LiveData<List<CurrencyUio>>
        get() = currencyData

    private val _stateFlow = MutableStateFlow<LoadingState>(LoadingState.Idle)
    val currencyListStateFlow = _stateFlow.asStateFlow()
    private var currentSortOrder = false
    suspend fun loadCurrencyByName(isAsc: Boolean) {
        viewModelScope.launch(ioDispatcher) {
            _stateFlow.tryEmit(LoadingState.InProgress)
            currentSortOrder = isAsc // resetting sort order
            currencyUseCase.loadCurrencyByName(isAsc).collect {
                // Using ViewModel observe
                // currencyData.postValue(it)
                //Using sate
                _stateFlow.tryEmit(LoadingState.Success(it))
            }
        }
    }

    suspend fun sortCurrencyByName() {
        loadCurrencyByName(!currentSortOrder)
    }

    suspend fun insertAllCurrency() {
        viewModelScope.launch(ioDispatcher) {
            val currencies = loadJSONFromAsset("currencies.json")
            currencies?.let { list ->
                val currencyList: List<Currency> =
                    Gson().fromJson(list, type)
                currencyUseCase.insertAllCurrency(currencyList)
            }
        }
    }

    /**
     * Should really move into some sort of util class
     */
    private val type: Type = object : TypeToken<List<Currency?>?>() {}.type

    private fun loadJSONFromAsset(fileName: String): String? {
        var json: String? = null
        json = try {
            val `is`: InputStream = context.assets.open(fileName)
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}