package com.newAdmilaTea.newadmilatea.listsFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminkatea.adapter.OrderAdapter
import com.example.adminkatea.adapter.SaleAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.newAdmilaTea.newadmilatea.R
import com.newAdmilaTea.newadmilatea.databinding.FragmentSaleBinding
import com.newAdmilaTea.newadmilatea.model.CatMenuModel
import com.newAdmilaTea.newadmilatea.model.LastModel
import com.newAdmilaTea.newadmilatea.model.MenuModelcatMenu


class SaleFragment : Fragment() {

    lateinit var binding : FragmentSaleBinding
    lateinit var saleAdapter : SaleAdapter
    private  var menulist : ArrayList<CatMenuModel> = ArrayList()

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

        loadMenu()

        return binding.root

    }

    private fun loadMenu() {


        val database = FirebaseDatabase.getInstance()

        val myRef = database.getReference("RestaurantsMenu/TeaTemple")


        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {


                for (ds in dataSnapshot.children) {
                    val value = ds.getValue(CatMenuModel::class.java)!!

                    Log.d("URES","${value.CategoryNameENG}")

                    menulist.add(value)
                }
                updateAdapter(menulist)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("dima", "Failed to read value.", error.toException())
            }
        })
    }

    private fun updateAdapter(menuList : ArrayList<CatMenuModel>) {
        saleAdapter.setupSalek(menuList)
    }

    companion object {

        fun newInstance() =
            SaleFragment()
    }
}