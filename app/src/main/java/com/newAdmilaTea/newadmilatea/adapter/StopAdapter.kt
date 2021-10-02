package com.example.adminkatea.adapter

import android.annotation.SuppressLint
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.newAdmilaTea.newadmilatea.R
import com.newAdmilaTea.newadmilatea.dialog.CountDialog
import com.newAdmilaTea.newadmilatea.dialog.CountDialogStop
import com.newAdmilaTea.newadmilatea.model.CatMenuModel
import com.newAdmilaTea.newadmilatea.model.LastModel

import com.newAdmilaTea.newadmilatea.model.MenuModelcatMenu

class StopAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    var mItemCheckList: ArrayList<LastModel> = ArrayList()

    var mItemMenuList: ArrayList<MenuModelcatMenu> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setupStop(catMenuModel: ArrayList<CatMenuModel>){

        mItemMenuList.clear()

        for (categoryModel in catMenuModel) {
            for (i in categoryModel.Items) {
                var menuModel = MenuModelcatMenu()
                menuModel.Item = i.value
                mItemMenuList.add(menuModel)
            }
        }

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var itemView = layoutInflater.inflate(R.layout.item_stop_adapter, parent, false)
        return Holderitem(itemView = itemView)
    }



    override fun getItemCount(): Int {
        return mItemMenuList.count()

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Holderitem){
            holder.bind(menuCategoryModel = mItemMenuList[position])
        }
    }

   class Holderitem(itemView : View) : RecyclerView.ViewHolder(itemView){
       var  textname: TextView = itemView.findViewById(R.id.name1)
       var  textCost: TextView = itemView.findViewById(R.id.cost1)
       var  textCost2: TextView = itemView.findViewById(R.id.cost2)
       var  layout: RelativeLayout = itemView.findViewById(R.id.layout_stop)

       fun bind(menuCategoryModel: MenuModelcatMenu){
           textname.text = "${menuCategoryModel.Item?.Name}"
           textCost.text = "${menuCategoryModel.Item?.Cost}" + " р."
           val newCost = "${menuCategoryModel.Item?.NewCost}"
           textCost2.text = "$newCost р."

           textCost2.visibility = View.GONE

           //здесь костыль: при пустых значаниях приходят странные цифры, но они не больше 10000)
           if (newCost.toDouble() in 1.1..9999.99) {
               textCost2.text = "${newCost.toString()} р.".toString()
               textCost2.visibility = View.VISIBLE
           }

           //если значение 0 или пустое то скрывать элемент

           val stop = menuCategoryModel.Item?.Stop?.toInt()!!

           Log.d("STOPPP", "stop= $stop")

           if (stop == 0) {
               layout.visibility = View.VISIBLE
               layout.layoutParams = RecyclerView.LayoutParams(
                   ViewGroup.LayoutParams.WRAP_CONTENT,
                   ViewGroup.LayoutParams.WRAP_CONTENT)
           } else {
               layout.visibility = View.GONE
               layout.layoutParams = RecyclerView.LayoutParams(0, 0)
           }

           itemView.setOnClickListener {
               CountDialogStop.openDialog(itemView.context, menuCategoryModel)
           }

       }
   }


}