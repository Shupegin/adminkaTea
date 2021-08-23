package com.newAdmilaTea.newadmilatea.singleton

import android.util.Log
import com.newAdmilaTea.newadmilatea.listener.EventListenerss
import com.newAdmilaTea.newadmilatea.model.MenuModelcatMenu


object BasketSingleton {

    var listeners: ArrayList<EventListenerss> = ArrayList()
    var basketItem : ArrayList<MenuModelcatMenu> = ArrayList()

    fun addBasket(item : MenuModelcatMenu){
        var boolean = true
        for(i in basketItem){
            if(i.Items?.Name == item.Items?.Name){
                boolean = false
            }
        }
        if (boolean == true){
            basketItem.add(item)
        }
    }

    fun itemCountBasket(ss : String){
        val menu = MenuModelcatMenu()
        menu.Items?.CountDialog = ss.toLong()
    }

    fun count(): Long {
        var sumItems: Long = 0
        for (i in basketItem) {
            sumItems = sumItems +  (i.Items?.Cost!! * i.Items?.CountDialog!!)
        }
        return sumItems
    }

    fun proverkaNaNalichie(position: MenuModelcatMenu): MenuModelcatMenu? {
        var nn: MenuModelcatMenu? = null
        for (i in basketItem) {
            if (position.Items?.Name.equals(i.Items?.Name)) {
                nn = i
            }
        }
        return nn
    }

    fun del() {
        basketItem.removeAll(basketItem)
    }

    fun delPos(position: Int) {
            basketItem.removeAt(position)

    }

    fun checkingThelist(gg: MenuModelcatMenu): Boolean {
        for (i in basketItem) {
            if (gg.Items?.Name.equals(i.Items?.Name)) {
                return true
            }
        }
        return false
    }

    fun showBasket(){
        for(i in basketItem){
            var yy = i.Items?.Name

            Log.d("Basket", "Items = "  + yy)
        }
    }

    fun notifyTwo() {
        for (listener in listeners) {
            listener.updateRR()
            Log.d("Test", "notifiTwo = " + listener)
        }
    }

    fun subscribe(listener: EventListenerss) {
        listeners.add(listener)
        Log.d("Test", "Listener = " + listener)
    }
}