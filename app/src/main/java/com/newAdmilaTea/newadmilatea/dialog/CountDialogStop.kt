package com.newAdmilaTea.newadmilatea.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.widget.*
import com.newAdmilaTea.newadmilatea.R
import com.newAdmilaTea.newadmilatea.model.MenuModelcatMenu
import com.newAdmilaTea.newadmilatea.singleton.BasketSingleton


class CountDialogStop {
    companion object {
        fun openDialog(context: Context, fileMenu: MenuModelcatMenu) {


            var menuFile = fileMenu
            Log.d("PPPP ", " Menufile = " + menuFile.Item?.Name)

            val dialog = Dialog(context, R.style.CustomDialog)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_item_stop)
            dialog.window?.setGravity(Gravity.BOTTOM)
            dialog.window?.setLayout(
                ListPopupWindow.MATCH_PARENT,
                ListPopupWindow.WRAP_CONTENT
            )
            val file = BasketSingleton.proverkaNaNalichie(fileMenu)
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()

            var btn_active = dialog.findViewById(R.id.btn_stop_active_dialog) as Button
            btn_active.setOnClickListener {

                menuFile.Item?.Stop = 1
                    BasketSingleton.addBasket(menuFile)
                    BasketSingleton.showBasket()
                    BasketSingleton.notifyTwo()

                dialog.cancel()
            }

            var btn_cancel = dialog.findViewById(R.id.btn_stop_cancel_dialog) as Button

            btn_cancel.setOnClickListener {
                dialog.cancel()
            }
        }
    }
}