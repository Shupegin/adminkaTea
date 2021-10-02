package com.example.adminkatea.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.storage.FirebaseStorage
import com.newAdmilaTea.newadmilatea.R
import com.newAdmilaTea.newadmilatea.dialog.CountDialog
import com.newAdmilaTea.newadmilatea.model.CatMenuModel
import com.newAdmilaTea.newadmilatea.model.MenuModelcatMenu
import com.newAdmilaTea.newadmilatea.singleton.BasketSingleton
import kotlin.collections.ArrayList

class MenuAdapter(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    var mItemMenuList: ArrayList<MenuModelcatMenu> = ArrayList()
    var mMenuList: ArrayList<CatMenuModel>? = null

    private val LAYOUT_HEADER = 0
    private val LAYOUT_CHILD = 1

    private val glide = Glide.with(context)

    @SuppressLint("NotifyDataSetChanged")
    fun setupMenu(menuList: ArrayList<CatMenuModel>) {
        mItemMenuList.clear()
        if (mMenuList == null) {
            mMenuList = menuList
        }
        for(categoryModel in menuList) {
            val model = MenuModelcatMenu()
            model.CategoryName = categoryModel.CategoryName
            model.isHeader = true
            mItemMenuList.add(model)

            for (i in categoryModel.Items) {
                mItemMenuList.add(MenuModelcatMenu(i.value))
            }
        }
        notifyDataSetChanged()
    }



    override fun getItemViewType(position: Int): Int {
        if (mItemMenuList[position].isHeader) {
            return LAYOUT_HEADER
        } else {
            return LAYOUT_CHILD
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == LAYOUT_HEADER) {
            var layoutInflater = LayoutInflater.from(parent.context)
            var itemView = layoutInflater.inflate(R.layout.category_item_menu, parent, false)
            return HeaderViewHolder(itemView = itemView)

        } else {

            var layoutInflater = LayoutInflater.from(parent.context)
            var itemView = layoutInflater.inflate(R.layout.item_menu_recyclerview, parent, false)
            return MenuViewHolder(itemView = itemView)
        }
    }



    override fun getItemCount(): Int {
        return mItemMenuList.count()

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == LAYOUT_HEADER) {
            if (holder is HeaderViewHolder) {
                holder.bindHeader(menuCategoryModel = mItemMenuList[position])
            } else {
            }

        } else {
            if (holder is MenuViewHolder) {
                holder.bindMenu(menuCategoryModel = mItemMenuList[position])
            }
        }
    }
    class HeaderViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        var categoryHeader: TextView = itemView.findViewById(R.id.category_item_menu)

        fun bindHeader(menuCategoryModel: MenuModelcatMenu) {

            categoryHeader.text = "${menuCategoryModel.CategoryName}"

        }
    }

   inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var name: TextView = itemView.findViewById(R.id.text_roll)
        private var discription: TextView = itemView.findViewById(R.id.discription_text)
        private var cost: TextView = itemView.findViewById(R.id.txt_roll_price)
        private var newCost: TextView = itemView.findViewById(R.id.txt_roll_price_new_cost)
        private var imgLine: ImageView = itemView.findViewById(R.id.img_roll_prise)
        private var checkBoxItem: TextView = itemView.findViewById(R.id.checkBoxItem)
        private var imgDish: ImageView = itemView.findViewById(R.id.image_dish_menu)
        private var wt: TextView = itemView.findViewById(R.id.txt_roll_weight)

        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun bindMenu(menuCategoryModel: MenuModelcatMenu) {

            name.text = "${menuCategoryModel.Item?.Name}"
            discription.text = "${menuCategoryModel.Item?.Description}"
            cost.text = "${menuCategoryModel.Item?.Cost}" + " р."
            val newCostt = "${menuCategoryModel.Item?.NewCost}"

            newCost.visibility = View.GONE
            imgLine.visibility = View.GONE

            //здесь костыль: при пустых значаниях приходят странные цифры, но они не больше 10000)
            if (newCostt.toDouble() in 1.1..9999.99) {
                newCost.text = "${newCostt.toString()} р.".toString()
                newCost.visibility = View.VISIBLE
                imgLine.visibility = View.VISIBLE
            }


            val wtVal = menuCategoryModel.Item?.Wt
            if(wtVal?.toInt() == 0){
                wt.visibility = View.GONE
            }else {
                wt.visibility = View.VISIBLE
                wt.text = "$wtVal гр."
            }

            if (menuCategoryModel.Item?.PictureForLoad == null) {
                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.getReferenceFromUrl(menuCategoryModel.Item?.Picture!!)
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    menuCategoryModel.Item?.PictureForLoad = uri
                    val img = glide.load(uri)
                    img.diskCacheStrategy(DiskCacheStrategy.NONE)
                    img.into(imgDish)
                }
            } else {
                val img = glide.load(menuCategoryModel.Item?.PictureForLoad)
                img.diskCacheStrategy(DiskCacheStrategy.NONE)
                img.into(imgDish)
            }




            itemView.setOnClickListener {
                CountDialog.openDialog(itemView.context, menuCategoryModel)
            }

            Log.d("Color", "menu = " + menuCategoryModel)

            var rr = BasketSingleton.checkingThelist(menuCategoryModel)
            Log.d("Color", "Bolean = " + rr)

            if (rr == true) {
                checkBoxItem.setBackgroundResource(R.color.qgreen)
            } else {
                checkBoxItem.setBackgroundResource(R.color.transparent)
            }
        }


    }

    fun scrollToCategory(name: String): Int {
        var position = 0
        for (i in mItemMenuList.indices) {
            val element: MenuModelcatMenu = mItemMenuList[i]
            if (element.isHeader) {
                if (element.CategoryName.equals(name)) {
                    position = i
                }
            }
        }
        return position
    }

   override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                Log.d("RRRER","ASED = " + charSearch)

                val filterResults = FilterResults()
                if (charSearch.isNotEmpty() && mMenuList != null) {
                    val filter: ArrayList<CatMenuModel> = ArrayList()

                    for(categoryModel in mMenuList!!) {
                        val filterCategory = CatMenuModel(categoryModel.CategoryName, categoryModel.CategoryNameENG)

                        for (item in categoryModel.Items) {
                            if (item.value.Name?.contains(charSearch, true) == true
                            || item.value.NameENG?.contains(charSearch, true) == true
                            ) {
                                filterCategory.Items[item.key] = item.value
                            }
                        }
                        if (filterCategory.Items.size > 0)
                            filter.add(filterCategory)
                    }
                    filterResults.values =  filter
                } else {
                    filterResults.values =  mMenuList
                }
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                setupMenu(results?.values as ArrayList<CatMenuModel>)
            }

        }
    }



}