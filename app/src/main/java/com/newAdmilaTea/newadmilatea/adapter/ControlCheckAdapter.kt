package com.example.adminkatea.adapter

import android.annotation.SuppressLint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.newAdmilaTea.newadmilatea.R

import com.newAdmilaTea.newadmilatea.model.MenuModelcatMenu

class ControlCheckAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mItemCheckList: ArrayList<MenuModelcatMenu> = ArrayList()



    @SuppressLint("NotifyDataSetChanged")
    fun setupControlCheck(checkList: ArrayList<MenuModelcatMenu>){
        mItemCheckList.clear()
        mItemCheckList.addAll(checkList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var itemView = layoutInflater.inflate(R.layout.item_check_adapter_two, parent, false)
        return Holderitem(itemView = itemView)
    }



    override fun getItemCount(): Int {
        return mItemCheckList.count()

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Holderitem){
            holder.bind(itemView = mItemCheckList[position])
        }
    }

   class Holderitem(itemView : View) : RecyclerView.ViewHolder(itemView){
        var  textname: TextView = itemView.findViewById(R.id.text_name_item)
        var  textlastCost: TextView = itemView.findViewById(R.id.lastCost)
        var  textnewCost: TextView = itemView.findViewById(R.id.newCost)
        var  stop: TextView = itemView.findViewById(R.id.stop_control)
        var  stopActive: TextView = itemView.findViewById(R.id.stop_active)

      fun bind(itemView: MenuModelcatMenu){
          textname.text = itemView.Item?.Name
          textlastCost.text = itemView.Item?.Cost.toString()
          textnewCost.text = itemView.Item?.NewCost.toString()

          val stopVal = itemView.Item?.Stop?.toInt()!!

          when (stopVal) {
              0 -> {
                  stop.visibility = View.VISIBLE
                  stopActive.visibility = View.GONE
                  textlastCost.visibility = View.GONE
                  textnewCost.visibility = View.GONE
              }
              1 -> {
                  stop.visibility = View.GONE
                  stopActive.visibility = View.VISIBLE
                  textlastCost.visibility = View.GONE
                  textnewCost.visibility = View.GONE
              }
              else -> {
                  stop.visibility = View.GONE
                  stopActive.visibility = View.GONE
                  textlastCost.visibility = View.VISIBLE
                  textnewCost.visibility = View.VISIBLE
              }
          }
       }
   }


}