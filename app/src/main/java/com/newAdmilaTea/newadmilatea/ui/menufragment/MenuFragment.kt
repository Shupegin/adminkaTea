package com.newAdmilaTea.newadmilatea.ui.menufragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminkatea.adapter.MenuAdapter
import com.example.adminkatea.model.ItemMenu
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase
import com.newAdmilaTea.newadmilatea.databinding.FragmentMenuBinding
import com.newAdmilaTea.newadmilatea.listener.EventListenerss
import com.newAdmilaTea.newadmilatea.model.CatMenuModel
import com.newAdmilaTea.newadmilatea.singleton.BasketSingleton


class MenuFragment : Fragment(), EventListenerss {


    private lateinit var binding : FragmentMenuBinding

    private lateinit var menuAdapter : MenuAdapter
    private  var menulist : ArrayList<CatMenuModel> = ArrayList()




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(layoutInflater)
        BasketSingleton.subscribe(this)

        menuAdapter = MenuAdapter(inflater.context)



        binding.recuclerMenu.adapter = menuAdapter
        binding.recuclerMenu.layoutManager = LinearLayoutManager(binding.root.context,RecyclerView.VERTICAL,false)
        binding.recuclerMenu.setHasFixedSize(true)
        binding.recuclerMenu.recycledViewPool.setMaxRecycledViews(100, 100)
        binding.recuclerMenu.setItemViewCacheSize(300)
        binding.recuclerMenu.isDrawingCacheEnabled = true



        binding.SearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                menuAdapter.filter.filter(newText)
                return false
            }

        })

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
        menuAdapter.setupMenu(menuList)
    }

    override fun updateRR() {
        updateAdapter(menulist)
    }

    


}