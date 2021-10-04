package com.newAdmilaTea.newadmilatea.listsFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminkatea.adapter.OrderAdapter
import com.example.adminkatea.adapter.SaleAdapter
import com.newAdmilaTea.newadmilatea.R
import com.newAdmilaTea.newadmilatea.databinding.FragmentSaleBinding
import com.newAdmilaTea.newadmilatea.model.LastModel
import com.newAdmilaTea.newadmilatea.model.MenuModelcatMenu


class SaleFragment : Fragment() {

    lateinit var binding : FragmentSaleBinding

    lateinit var saleAdapter : SaleAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSaleBinding.inflate(layoutInflater)
        saleAdapter = SaleAdapter()

        binding.recuclerSale.adapter = saleAdapter
        binding.recuclerSale.layoutManager = LinearLayoutManager(binding.root.context,
            RecyclerView.VERTICAL,false)
        binding.recuclerSale.setHasFixedSize(true)
        binding.recuclerSale.recycledViewPool.setMaxRecycledViews(100, 100)
        binding.recuclerSale.setItemViewCacheSize(300)
        binding.recuclerSale.isDrawingCacheEnabled = true

        var list : ArrayList<LastModel> = ArrayList()

        var Lasr = LastModel()

        Lasr.name = "Дима"

        var sda = LastModel()
        sda.name = "Саша"
        list.add(Lasr)
        list.add(sda)
        updateAdapter(list)
        return binding.root



    }

    private fun updateAdapter(list : ArrayList<LastModel>) {
        saleAdapter.setupSalek(list)
    }

    companion object {

        fun newInstance() =
            SaleFragment()
    }
}