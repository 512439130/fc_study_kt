package com.fc.study.inter

import android.content.Context

interface ActivityInterface {
    fun showToast(context: Context, content:String)
    fun check(){
        println("default-implement-check")
    }
}