package com.devilsvirtue.cryptocom.ui

import com.devilsvirtue.cryptocom.ui.uio.CurrencyUio

sealed class LoadingState {
    object Idle : LoadingState()
    object InProgress : LoadingState()
    class Success(val currencies: List<CurrencyUio>) : LoadingState()
}