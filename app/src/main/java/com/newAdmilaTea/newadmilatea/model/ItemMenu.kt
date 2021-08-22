package com.example.adminkatea.model

import android.net.Uri

class ItemMenu(var Cost: Long, var Description: String?, var Name: String?, var Picture: String?, var CountDialog: Long, var PictureLoad: String?, var PictureForLoad: Uri?, var Wt: Long, var Delivery: Long) {

    constructor(): this(Cost = Long.MAX_VALUE, Description = String(), Name = String(),Picture = String(),CountDialog = Long.MAX_VALUE,PictureLoad = String() , PictureForLoad = null, Wt = Long.MAX_VALUE, Delivery = Long.MAX_VALUE)

}