package com.fc.study.kt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.fc.study.const.*

class MainActivity : AppCompatActivity() {
    private val test1 = "test1"
    private var test2 = "test2"
    private val test3: Int = 666
    private var test4: Double = 88.88
    private var test5: String = "test5"

    private lateinit var btnTest1:Button
    private lateinit var btnTest2:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        btnTest1 = findViewById(R.id.btn_test1)
        btnTest1.setOnClickListener(View.OnClickListener {
            test()
        })

        btnTest2 = findViewById(R.id.btn_test2)
        btnTest2.setOnClickListener {
//            val intent = Intent(this,TestClassKotlin().javaClass)
            val intent = Intent(this,TestClassKotlin::class.java)
            intent.putExtra(INTENT_NAME,"杨阳")
            intent.putExtra(INTENT_SEX,"男")
            intent.putExtra(INTENT_AGE, "27")
            startActivity(intent)
        }
    }

    private fun test() {
        test2 = "test2 new"
        test4 = 10.23
        println("test1 $test1")
        println("test2 $test2")
        println("test3 $test3")
        println("test4 $test4")
        println("test5 $test5")

        ConstObject.test()
        fileTest()
    }


}