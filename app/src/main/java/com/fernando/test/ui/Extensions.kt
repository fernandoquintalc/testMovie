package com.fernando.test.ui

fun String.extractYear() : String{
    return if(this.length<4)
        ""
    else
        this.substring(0,4)
}