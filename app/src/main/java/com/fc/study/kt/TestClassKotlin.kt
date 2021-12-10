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
            var age1:Int = 16;
            var age2:Int = 28
            println(getMaxAge(age1,age2))
            println(getSexTitle(tv2.text.toString()))

            println(jvmOverloadTest((tv1?.text?.toString())))

            println(getAgeType(age1.toString()))
            println(getAgeType(age2.toString()))

            //循环
            //1）使用 .. 表示创建两端都是闭区间的升序区间
            whileForTest1()
            //2）使用 until 表示创建左端是闭区间右端是开区间的升序区间
            whileForTest2()
            //3）使用 downTo 表示创建两端都是闭区间的降序区间
            whileForTest3()
            //4）在区间的后面加上 step ，表示跳过几个元素
            whileForTest4()
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


    private fun getMaxAge(age1:Int, age2:Int) = if(age1 > age2) age1 else age2

    private fun getSexTitle(sex: String): String {
        return when (sex) {
            "男" -> "靓仔"
            "女" -> "靓女"
            else -> "未知"
        }
    }
    private fun getAgeType(age: String): String {
        return when {
            // (age.toInt >=0 && age.toInt <=18) ->
            (age.toInt() in 0..18) -> "未成年"
            else -> "成年人"
        }
    }
    //kotlin重载的体现
    @JvmOverloads fun jvmOverloadTest(name: String?, sex:String = "女", age:String = "19"): String {
        return (name + sex + age)
    }

    private fun whileForTest1(){
        //使用 .. 表示创建两端都是闭区间的升序区间
        for (i in 0..10){
            print("$i ")
        }


    }

    private fun whileForTest2() {
        //使用 until 表示创建左端是闭区间右端是开区间的升序区间
        for (i in 0 until 10){
            print("$i ")
        }
    }

    private fun whileForTest3() {
        //使用 downTo 表示创建两端都是闭区间的降序区间
        for (i in 10 downTo 0){
            print("$i ")
        }
    }

    //情况4
    private fun whileForTest4() {
        //使用 downTo 表示创建两端都是闭区间的降序区间,每次在跳过3个元素
        for (i in 10 downTo 0 step 3){
            print("$i ")
        }
    }

}