package com.example.adminkatea.adapter

import android.annotation.SuppressLint
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

import com.newAdmilaTea.newadmilatea.R
import com.newAdmilaTea.newadmilatea.dialog.CountDialog

import com.newAdmilaTea.newadmilatea.model.MenuModelcatMenu
import com.newAdmilaTea.newadmilatea.singleton.BasketSingleton

class CheckAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mItemCheckList: ArrayList<MenuModelcatMenu> = ArrayList()



    @SuppressLint("NotifyDataSetChanged")
    fun setupCheck(checkList: ArrayList<MenuModelcatMenu>){
        mItemCheckList.clear()
        mItemCheckList.addAll(checkList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var itemView = layoutInflater.inflate(R.layout.item_check_adapter, parent, false)
        return Holderitem(itemView = itemView)
    }



    override fun getItemCount(): Int {
        return mItemCheckList.count()

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Holderitem){
            holder.bind(itemView1 = mItemCheckList[position])
            btnDel(holder, position)
        }
    }

   class Holderitem(itemView : View) : RecyclerView.ViewHolder(itemView){
       var  textname: TextView = itemView.findViewById(R.id.text_name_item)
       var  textlastCost: TextView = itemView.findViewById(R.id.lastCost)
       var  textnewCost: TextView = itemView.findViewById(R.id.newCost)
       var  btnDel: Button = itemView.findViewById(R.id.btn_del_)
      fun bind(itemView1: MenuModelcatMenu){
          textname.text = itemView1.Item?.Name
          textlastCost.text = itemView1.Item?.Cost.toString()
          textnewCost.text = itemView1.Item?.newCost.toString()




          itemView.setOnClickListener {

              CountDialog.openDialog(itemView.context, itemView1)

          }

       }


   }

    private fun btnDel(holder: RecyclerView.ViewHolder, position: Int){
        if(holder is Holderitem ){
            holder.bind(itemView1 = mItemCheckList[position])
            holder.btnDel.setOnClickListener {

                val delPosDialog = AlertDialog.Builder(holder.itemView.context
                )
                delPosDialog.setTitle("Аннигилирование")
                delPosDialog.setMessage("Удалить блюдо?")
                delPosDialog.setPositiveButton(
                    "Да"
                ) { _, _ ->
                    BasketSingleton.delPos(position)
                    BasketSingleton.notifyTwo()
                    Log.d("list", "del= ${mItemCheckList}")
                }
                delPosDialog.setNegativeButton(
                    "Ой, нет!"
                ) { _, _ -> }
                delPosDialog.show()
            }
        }
    }


}