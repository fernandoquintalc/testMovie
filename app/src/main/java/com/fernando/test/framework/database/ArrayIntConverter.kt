package com.fernando.test.framework.database

import android.text.TextUtils
import androidx.room.TypeConverter
import org.w3c.dom.Text

class ArrayIntConverter {

    @TypeConverter
    fun fromString(value: String): Array<Int> {
        if(value.isEmpty()){
            return emptyArray()
        }
        val list = ArrayList<Int>()
        value.split(",").toTypedArray().forEach {
            list.add(it.toInt())
        }
        return list.toTypedArray()
    }

    @TypeConverter
    fun fromArrayInt(array: Array<Int>): String = TextUtils.join(",", array)
}