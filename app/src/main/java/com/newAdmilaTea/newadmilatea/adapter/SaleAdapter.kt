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
import com.newAdmilaTea.newadmilatea.model.CatMenuModel
import com.newAdmilaTea.newadmilatea.model.LastModel

import com.newAdmilaTea.newadmilatea.model.MenuModelcatMenu

class SaleAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mItemCheckList: ArrayList<MenuModelcatMenu> = ArrayList()



    @SuppressLint("NotifyDataSetChanged")
    fun setupSalek(catMenuModel: ArrayList<CatMenuModel>){

        mItemCheckList.clear()

        for (categoryModel in catMenuModel) {
            for (i in categoryModel.Items) {
                var menuModel = MenuModelcatMenu()
                menuModel.Item = i.value
                mItemCheckList.add(menuModel)
            }
        }

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var itemView = layoutInflater.inflate(R.layout.item_sale_adapter, parent, false)
        return Holderitem(itemView = itemView)
    }



    override fun getItemCount(): Int {
        return mItemCheckList.count()

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Holderitem){
            holder.bind(menuCategoryModel = mItemCheckList[position])
        }
    }

   class Holderitem(itemView : View) : RecyclerView.ViewHolder(itemView){
        var  textname: TextView = itemView.findViewById(R.id.name1)
        var  textCost: TextView = itemView.findViewById(R.id.cost1)
        var  textCost2: TextView = itemView.findViewById(R.id.cost2)
        var  layout: RelativeLayout = itemView.findViewById(R.id.layout)

      fun bind(menuCategoryModel: MenuModelcatMenu){
          textname.text = "${menuCategoryModel.Item?.Name}"
          textCost.text = "${menuCategoryModel.Item?.Cost}" + " р."
          val newCost = "${menuCategoryModel.Item?.NewCost}"
          textCost2.text = "$newCost р."

          Log.d("NewCostString", "cost = $newCost")

          //если новая цена пуста или равна 0 то скрывать элемент
          if (newCost.toDouble() == 1.7976931348623157E308 || newCost.toDouble() == 0.0) {
              layout.visibility = View.GONE
              layout.layoutParams = RecyclerView.LayoutParams(0, 0)
          } else {
              layout.visibility = View.VISIBLE
              layout.layoutParams = RecyclerView.LayoutParams(
                  ViewGroup.LayoutParams.WRAP_CONTENT,
                  ViewGroup.LayoutParams.WRAP_CONTENT
              )
          }

          itemView.setOnClickListener {
              CountDialog.openDialog(itemView.context, menuCategoryModel)
          }
       }
   }


}