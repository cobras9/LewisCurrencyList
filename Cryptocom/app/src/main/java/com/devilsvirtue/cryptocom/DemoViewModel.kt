package com.devilsvirtue.cryptocom

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devilsvirtue.cryptocom.data.Currency
import com.devilsvirtue.cryptocom.data.CurrencyUseCase
import com.devilsvirtue.cryptocom.data.mapDbToUio
import com.devilsvirtue.cryptocom.ui.uio.CurrencyUio
import com.devilsvirtue.cryptocom.util.IoDispatcher
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
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

    suspend fun loadCurrency() {
        viewModelScope.launch(ioDispatcher) {
            currencyUseCase.loadAllCurrency().collect { dbCurrencyList ->
                currencyData.postValue(dbCurrencyList.map {
                    it.mapDbToUio()
                })
            }
        }
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