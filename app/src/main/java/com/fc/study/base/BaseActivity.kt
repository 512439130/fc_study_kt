package com.fc.study.base

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fc.study.kt.databinding.ActivityKotlinBaseStudyBinding

abstract class BaseActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    abstract fun init()

    fun toast(context: Context, content:String){
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
    }
}