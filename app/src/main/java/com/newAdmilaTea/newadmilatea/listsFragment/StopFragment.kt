package com.newAdmilaTea.newadmilatea.listsFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminkatea.adapter.SaleAdapter
import com.example.adminkatea.adapter.StopAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.newAdmilaTea.newadmilatea.R
import com.newAdmilaTea.newadmilatea.databinding.FragmentSaleBinding
import com.newAdmilaTea.newadmilatea.databinding.FragmentStopBinding
import com.newAdmilaTea.newadmilatea.model.CatMenuModel
import com.newAdmilaTea.newadmilatea.model.LastModel


class StopFragment : Fragment() {

    lateinit var binding : FragmentStopBinding
    lateinit var stopAdapter : StopAdapter
    private  var menulist : ArrayList<CatMenuModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentStopBinding.inflate(layoutInflater)
        stopAdapter = StopAdapter()

        binding.recuclerStop.adapter = stopAdapter
        binding.recuclerStop.layoutManager = LinearLayoutManager(binding.root.context,
            RecyclerView.VERTICAL,false)
        binding.recuclerStop.setHasFixedSize(true)
        binding.recuclerStop.recycledViewPool.setMaxRecycledViews(100, 100)
        binding.recuclerStop.setItemViewCacheSize(300)
        binding.recuclerStop.isDrawingCacheEnabled = true

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
        stopAdapter.setupStop(menuList)
    }

    companion object {

        fun newInstance() =
            StopFragment()
    }
}