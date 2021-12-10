package com.fc.study.kt

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.fc.study.const.*

private const val TAG: String = "TestClassKotlin"

class TestClassKotlin : Activity() {

    private lateinit var btnTest: Button

    private var tv1: TextView? = null
    private lateinit var tv2: TextView
    private lateinit var tv3: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_kotlin)
        init()
    }

    private fun init() {
        initView()
        initData();
    }

    private fun initView() {
        btnTest = findViewById(R.id.btn_test)

//        tv1 = findViewById(R.id.tv_1)
        tv2 = findViewById(R.id.tv_2)
        tv3 = findViewById(R.id.tv_3)
        btnTest.setOnClickListener {
            println("log $TAG")
            println(getValue(tv2.text.toString(), tv3.text.toString()))
            println(getValue(tv1?.text?.toString(), tv2.text.toString(), tv3.text.toString()))
            println(getMaxAge(27,28))
            println(getSexTitle(tv2.text.toString()))
        }
    }

    private fun initData() {
        val bundle = this.intent.extras
        val name = bundle?.getString(INTENT_NAME)
        val sex = bundle?.getString(INTENT_SEX)
        val age = bundle?.getString(INTENT_AGE)
        tv1?.text = name
        tv2.text = sex
        tv3.text = age
    }

    //参数的声明格式为："参数名"："参数类型"
    //返回类型定义：默认Unit
    fun getValue(sex: String?, age: String?): String {
        return (((tv1?.text ?: "test").toString()) + sex + age)
    }

    //当函数的函数体只有一行时，使用”=“连接
    fun getValue(name: String?, sex: String?, age: String?): String = (name + sex + age)


    fun getMaxAge(age1:Int, age2:Int) = if(age1 > age2) age1 else age2

    fun getSexTitle(sex: String): String {
        return when (sex) {
            "男" -> "靓仔"
            "女" -> "靓女"
            else -> "未知"
        }
    }

}