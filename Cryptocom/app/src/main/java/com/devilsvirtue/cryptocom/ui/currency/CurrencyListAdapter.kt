package com.devilsvirtue.cryptocom.ui.currency

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.recyclerview.widget.RecyclerView
import com.devilsvirtue.cryptocom.databinding.CurrencyItemBinding
import com.devilsvirtue.cryptocom.ui.uio.CurrencyUio

class CurrencyListAdapter(
    private val values: List<CurrencyUio>
) :
    RecyclerView.Adapter<CurrencyListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CurrencyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.initialView.text = item.name.substring(0, 1)
        holder.nameView.text = item.name
        holder.symbolView.text = item.symbol
        with(holder.mainView) {
            tag = item
            setOnClickListener {
                Toast.makeText(
                    context,
                    "(${item.id})${item.name} clicked, hooking up activity",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun getItemCount() = values.size
    inner class ViewHolder(binding: CurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val initialView: TextView = binding.initial
        val nameView: TextView = binding.name
        val symbolView: TextView = binding.symbol
        val mainView: ConstraintLayout = binding.currencyMain
    }
}