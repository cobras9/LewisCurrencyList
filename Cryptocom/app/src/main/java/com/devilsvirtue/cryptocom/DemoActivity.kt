package com.devilsvirtue.cryptocom

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.devilsvirtue.cryptocom.databinding.ActivityDemoBinding
import com.devilsvirtue.cryptocom.ui.LoadingState
import com.devilsvirtue.cryptocom.ui.currency.CurrencyListFragment
import com.devilsvirtue.cryptocom.ui.uio.CurrencyUio
import com.devilsvirtue.cryptocom.util.IoDispatcher
import com.devilsvirtue.cryptocom.util.Keys
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DemoActivity : AppCompatActivity(), CurrencyListFragment.CurrencyExternalHooks {
    @Inject
    @IoDispatcher
    lateinit var ioDispatcher: CoroutineDispatcher
    private lateinit var binding: ActivityDemoBinding
    private lateinit var mainList: List<CurrencyUio>
    private lateinit var navController: NavController
    private lateinit var viewModel: DemoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupControllers()
        setupViews()
    }

    private fun setupViews() {
        viewModel = ViewModelProvider(this)[DemoViewModel::class.java]
        lifecycleScope.launchWhenResumed {
            viewModel.currencyListStateFlow.collect { state ->
                when (state) {
                    LoadingState.InProgress,
                    LoadingState.Idle -> {
                        // Do nothing
                    }
                    is LoadingState.Success -> {
                        updateCurrencyUiViaState(state.currencies)
                    }
                    // Error handling should be here with another state
                }
            }
        }
        // Observing just the list
        /*  viewModel.currencyList.observe(this) {
              mainList = it
              updateCurrencyUi()
          }*/
        binding.sortCurrency.setOnClickListener {
            if (viewModel.currencyListStateFlow.value != LoadingState.InProgress) {
                viewModel.viewModelScope.launch(ioDispatcher) {
                    //viewModel.insertAllCurrency()
                    viewModel.sortCurrencyByName()
                }
            }
        }
        binding.loadCurrency.setOnClickListener {
            // Avoiding fast double clicks if other operations are in progress
            // Other way would be to deal with the latest returned result using coroutine merge/combine/join
            if (viewModel.currencyListStateFlow.value != LoadingState.InProgress) {
                viewModel.viewModelScope.launch(ioDispatcher) {
                    viewModel.loadCurrencyByName(isAsc = true)
                }
            }
        }
    }

    private fun setupControllers() {
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_currency_list
            )
        )
        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.navigation_currency_list) {
                binding.currencyActions.visibility = View.VISIBLE
            } else {
                binding.currencyActions.visibility = View.GONE
            }
        }
    }

    private fun updateCurrencyUiViaState(list: List<CurrencyUio>?) {
        navController.navigate(R.id.navigation_currency_list, Bundle().apply {
            putParcelableArray(
                Keys.CURRENCY_LIST_KEY,
                list?.toTypedArray()
            )
        })
    }

    private fun updateCurrencyUi() {
        navController.navigate(R.id.navigation_currency_list, Bundle().apply {
            putParcelableArray(
                Keys.CURRENCY_LIST_KEY,
                viewModel.currencyList.value?.toTypedArray()
            )
        })
    }

    override val onItemClickListener: () -> Unit
        get() = {
            View.OnClickListener { view ->
                view?.let {
                    Toast.makeText(
                        applicationContext,
                        "Activity hooking up activity",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
}