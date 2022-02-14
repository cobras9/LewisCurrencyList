package com.devilsvirtue.cryptocom.ui.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devilsvirtue.cryptocom.databinding.FragmentCurrencylistBinding
import com.devilsvirtue.cryptocom.ui.uio.CurrencyUio
import com.devilsvirtue.cryptocom.util.Keys
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CurrencyListFragment : Fragment() {

    private var _binding: FragmentCurrencylistBinding? = null
    private val binding get() = _binding!!
    private var currencyList: List<CurrencyUio>? = null
    private var _callBack: CurrencyExternalHooks? = null
    private val callBack get() = _callBack!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currencyListViewModel =
            ViewModelProvider(this).get(CurrencyListViewModel::class.java)

        _binding = FragmentCurrencylistBinding.inflate(inflater, container, false)
        _callBack = activity as CurrencyExternalHooks
        val root: View = binding.root
        arguments?.getParcelableArray(Keys.CURRENCY_LIST_KEY)?.let {
            currencyList = it.toList() as List<CurrencyUio>
        }
        setupRecyclerView(binding.currencyList)
        return root
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView
    ) {
        binding.currencyList.apply {
            adapter = CurrencyListAdapter(currencyList ?: CurrencyPlaceholder.ITEMS
            ) { callBack.onItemClickListener() }
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context, LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface CurrencyExternalHooks {
        val onItemClickListener : () -> Unit
    }
}