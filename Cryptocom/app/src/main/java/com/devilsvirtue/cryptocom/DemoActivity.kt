package com.devilsvirtue.cryptocom

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.devilsvirtue.cryptocom.databinding.ActivityDemoBinding
import com.devilsvirtue.cryptocom.ui.uio.CurrencyUio
import com.devilsvirtue.cryptocom.util.IoDispatcher
import com.devilsvirtue.cryptocom.util.Keys
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DemoActivity : AppCompatActivity() {
    @Inject @IoDispatcher lateinit var ioDispatcher: CoroutineDispatcher
    private lateinit var binding: ActivityDemoBinding
    private lateinit var mainList: List<CurrencyUio>
    private lateinit var navController: NavController
    private lateinit var viewModel: DemoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[DemoViewModel::class.java]
        viewModel.currencyList.observe(this) {
            mainList = it
            updateCurrencyUi()
        }
        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_currency_list
            )
        )

        binding.sortCurrency.setOnClickListener {
            viewModel.viewModelScope.launch(ioDispatcher) {
                //viewModel.insertAllCurrency()
                viewModel.sortCurrencyByName()
            }
        }
        binding.loadCurrency.setOnClickListener {
            viewModel.viewModelScope.launch(ioDispatcher) {
                viewModel.loadCurrencyByName(isAsc = true)
            }
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.navigation_currency_list) {
                binding.currencyActions.visibility = View.VISIBLE
                //binding.description.visibility = View.VISIBLE
            } else {
                binding.currencyActions.visibility = View.GONE
                //binding.description.visibility = View.GONE
            }
        }

    }

    private fun updateCurrencyUi() {
        navController.navigate(R.id.navigation_currency_list, Bundle().apply {
            putParcelableArray(
                Keys.CURRENCY_LIST_KEY,
                viewModel.currencyList.value?.toTypedArray()
            )
        })
    }
}