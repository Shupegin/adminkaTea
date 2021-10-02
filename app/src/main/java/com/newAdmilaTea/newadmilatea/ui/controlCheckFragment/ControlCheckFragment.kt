package com.newAdmilaTea.newadmilatea.ui.controlCheckFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminkatea.adapter.ControlCheckAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.newAdmilaTea.newadmilatea.databinding.FragmentControlCheckBinding
import com.newAdmilaTea.newadmilatea.model.MenuModelcatMenu
import com.newAdmilaTea.newadmilatea.singleton.BasketSingleton


class ControlCheckFragment : Fragment() {

    private lateinit var binding : FragmentControlCheckBinding
    lateinit var adapterControlAdapter : ControlCheckAdapter
    lateinit var mDataBase: DatabaseReference
    var list : ArrayList<MenuModelcatMenu> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentControlCheckBinding.inflate(layoutInflater)
        adapterControlAdapter = ControlCheckAdapter()
        binding.checkControlRecycler.adapter = adapterControlAdapter
        binding.checkControlRecycler.layoutManager = LinearLayoutManager(binding.root.context,
            RecyclerView.VERTICAL,false)
        binding.checkControlRecycler.setHasFixedSize(true)
        binding.checkControlRecycler.recycledViewPool.setMaxRecycledViews(100, 100)
        binding.checkControlRecycler.setItemViewCacheSize(300)
        binding.checkControlRecycler.isDrawingCacheEnabled = true


        list.addAll(BasketSingleton.basketItem)
        setupAdapter(list)

        binding.btnNext.setOnClickListener {
            var  list = BasketSingleton.basketItem
            var categoryNameENG : String
            var nameENG : String
            for (i in list) {
                categoryNameENG = i.Item?.CategoryNameENG.toString()
                nameENG = i.Item?.NameENG.toString()
                Log.d("UREX", "$nameENG")
                Log.d("UREX", "$categoryNameENG")
                Log.d("UREX", "${i.Item?.NewCost}")
                Log.d("UREX",
                    "${"RestaurantsMenu/TeaTemple/" + categoryNameENG + "/Items/" + nameENG + "/NewCost"}")

                val stopVal = i.Item?.Stop?.toInt()!!

                if(stopVal == 0 || stopVal == 1){
                    mDataBase = FirebaseDatabase.getInstance()
                        .getReference("RestaurantsMenu/TeaTemple/" + categoryNameENG + "/Items/" + nameENG + "/Stop")
                    mDataBase.ref.setValue(i.Item?.Stop)

                }else {
                    mDataBase = FirebaseDatabase.getInstance()
                        .getReference("RestaurantsMenu/TeaTemple/" + categoryNameENG + "/Items/" + nameENG + "/NewCost")
                    mDataBase.ref.setValue(i.Item?.NewCost)
                }
            }

        }
        return binding.root
    }

    private fun setupAdapter(list : ArrayList<MenuModelcatMenu>) {
        adapterControlAdapter.setupControlCheck(list)
    }


}