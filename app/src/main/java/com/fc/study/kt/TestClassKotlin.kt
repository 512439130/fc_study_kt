package com.fc.study.kt

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.fc.study.const.INTENT_AGE
import com.fc.study.const.INTENT_NAME
import com.fc.study.const.INTENT_SEX
import java.util.*

private const val TAG: String = "TestClassKotlin"

class TestClassKotlin : Activity(), View.OnClickListener {
    //val var
    private val test1 = "test1"
    private var test2 = "test2"
    private val test3: Int = 666
    private var test4: Double = 88.88
    private var test5: String = "test5"


    //lateinit：延迟初始化
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

        tv1 = findViewById(R.id.tv_1)
        tv2 = findViewById(R.id.tv_2)
        tv3 = findViewById(R.id.tv_3)
        btnTest.setOnClickListener(this)
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

    private fun testValVarConst() {
        println("--------------------testValVarConst--------------------")
        test2 = "test2 new"
        test4 = 10.23
        println("test1 $test1")
        println("test2 $test2")
        println("test3 $test3")
        println("test4 $test4")
        println("test5 $test5")
        println("log $TAG")
        ConstObject.test()
        fileTest()
    }

    //参数的声明格式为："参数名"："参数类型"
    //返回类型定义：默认Unit
    fun getValue(sex: String?, age: String?): String {
        return (((tv1?.text ?: "test").toString()) + sex + age)
    }

    //当函数的函数体只有一行时，使用”=“连接
    fun getValue(name: String?, sex: String?, age: String?): String = (name + sex + age)

    private fun getMaxAge(age1: Int, age2: Int) = if (age1 > age2) age1 else age2

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
    @JvmOverloads
    fun jvmOverloadTest(name: String?, sex: String = "女", age: String = "19"): String {
        return (name + sex + age)
    }

    //数组Test
    private fun arrayTest() {
        println("--------------------arrayTest--------------------")
        //闭包初始化数组
        val arrayTest1 = Array(3) { it.inc() }
        arrayTest1.forEach {
            print("Array-forEach $it ")
        }
        println()

        for (item in arrayTest1) {
            print("Array-for-in $item ")
        }
        println()

        //创建一般数组
        val arrayTest2 = arrayOf("Array-001", "Array-002", "Array-003")
        arrayTest2[2] = "Update-Array-003"

        for (index in arrayTest2.indices) {
//          print(arrayTest2.get(index))
            print("arrayOf-for-in " + arrayTest2[index] + " ")
        }
        println()

        //创建固定长度的数组
        val arrayTest3 = arrayOfNulls<String>(5)
        arrayTest3[0] = "arrayOfNulls-001"
        for (item in arrayTest3) print("arrayOfNulls for-in $item ")
        println()

        //创建空数组
        val arrayTest4 = emptyArray<String>()
        for (item in arrayTest4) print("emptyArray for-in $item ")
        println()

        val arrayTest5 = IntArray(5)
        arrayTest5[0] = 1
        for (item in arrayTest5) print("IntArray for-in $item ")
        println()

        val arrayTest6 = intArrayOf(1, 2, 3)
        arrayTest6[1] = 666
        for (item in arrayTest6) print("intArrayOf for-in $item ")
        println()

        val arrayTest7 = booleanArrayOf(true, true, false)
        arrayTest7[1] = false
        for (item in arrayTest7) print("booleanArrayOf for-in $item ")
        println()

        //二维数组
        val arrayTest8 = Array(3) { IntArray(3) }
        //初始化赋值
        for (one in arrayTest8.indices) {
            for (two in arrayTest8[one].indices) {
                arrayTest8[one][two] = (one.toString().toInt() + two)
            }
        }
        //打印数据
        //indices为数组遍历时的动态下标
        for (one in arrayTest8.indices) {
            print("$one: ")
            print("{")
            for (two in arrayTest8[one].indices) {
                print("${arrayTest8[one][two]}")
            }
            print("}")
        }
        println()
    }

    //集合Test
    private fun collectionTest() {
        collectionMapTest()
        collectionSetTest()
        collectionListTest()
    }

    private fun collectionMapTest() {
        println("--------------------collectionMapTest--------------------")
        //mapOf(): 该函数返回不可变的Map集合。:LinkedHashMap
        //mutableMapOf(): 该函数返回可变的MutableMap集合。:LinkedHashMap
        //hashMapOf(): 返回可变的HashMap集合。
        //linkedMapOf(): 返回可变的LinkedHashMap集合。
        //sortedMapOf(): 返回可变的SortedMap(TreeMap)集合。

        val mapTest1: Map<String, String> =
            mapOf("Map-001" to "first", "Map-002" to "second", "Map-003" to "third")
        println("$mapTest1")
        mapTest1.forEach {
            print(it.key + " " + it.value)
        }
        println()

        for (entry in mapTest1.entries) {
            print("$entry")
        }
        println()

        for (key in mapTest1.keys) {
            print("$key: ${mapTest1[key]}")
        }
        println()

        for ((key, value) in mapTest1) {
            print("$key: $value")
        }
        println()

        val mapTest2: MutableMap<String, String> = mutableMapOf()
        mapTest2["MutableMap-001"] = "first"
        mapTest2["MutableMap-002"] = "second"
        mapTest2["MutableMap-003"] = "third"
        println("$mapTest2")

        val mapTest3: HashMap<String, String> = hashMapOf()
        mapTest3["HashMap-001"] = "first"
        mapTest3["HashMap-002"] = "second"
        mapTest3["HashMap-003"] = "third"
        println("$mapTest3")

        val mapTest4: LinkedHashMap<String, String> = linkedMapOf()
        mapTest4["LinkedHashMap-001"] = "first"
        mapTest4["LinkedHashMap-002"] = "second"
        mapTest4["LinkedHashMap-003"] = "third"
        println("$mapTest3")


        val mapTest5: SortedMap<String, String> = sortedMapOf()
        mapTest5["SortedMap-01"] = "first"
        mapTest5["SortedMap-002"] = "second"
        mapTest5["SortedMap-0003"] = "third"
        println("$mapTest5")


        println(
            //所有的元素都满足给定的条件
            "map.all" + mapTest5.all {
                it.key.contains("SortedMap-01")
            }
        )
        println(
            //至少有一个元素满足给定的条件
            "map.any" + mapTest5.any {
                it.key.contains("SortedMap-01")
            }
        )
        //对 Map 集合中的元素进行过滤生成新Map
        val filteredMap = mapTest5.filter {
            "SortedMap-0002" in it.key
        }
        println(filteredMap.toString())

        //将 Map 集合中的元素映射成一个新的 List 集合
        val newList = mapTest5.map { "${it.key}_${it.value}" }
        println(newList.toString())

        println(
            "maxByOrNull" + mapTest5.maxByOrNull {
                it.key.length
            }
        )

    }

    private fun collectionSetTest() {
        println("--------------------collectionSetTest--------------------")
        //不可变set
        val testSet1: Set<String> = setOf("Set-001", "Set-002", "Set-003", "Set-003")
        println(testSet1.toString())

        //得到删除set集合前面n个元素后的集合，原集合不变
        val testSet1Result = testSet1.drop(1)
        println("testSet1: $testSet1")
        println("testSet1Result: $testSet1Result")

        //创建一个可变Set集合
        val testSet2 = mutableSetOf<String>("Set-001", "Set-002", "Set-003")
        testSet2.add("Set-003")
        testSet2.add("Set-005")
        println("testSet2: $testSet2")

        //Set集合中是否存在元素，存在返回元素本身，不存在返回null
        val testSet2Result = testSet2.find {
            "Set-005" in it
        }
        println("testSet2Result: $testSet2Result")
    }

    private fun collectionListTest() {
        println("--------------------collectionListTest--------------------")

        val testList1 = listOf("List-001", "List-002", "List-003")
        val testList1Reeust = testList1.drop(2)
        println("testList1: $testList1")
        println("testList1Reeust: $testList1Reeust")

        val testList2 = mutableListOf("List-001", "List-002", "List-003")
        testList2.add("List-008")
        testList2.add("List-009")
        testList2.add("List-0010")
        println("testList2: $testList2")
    }

    private fun loopTest() {
        println("--------------------loopTest--------------------")
        var j: Int = 0
        while (j <= 15) {
            print("$j ")
            j++
        }
        println()

        var k: Int = 0
        do {
            print("$k ")
            k++;
        } while (k < 15)
        println()

        val items = mutableListOf<String>()
        items.add("apple")

        //使用 .. 表示创建两端都是闭区间的升序区间
        for (i in 0..15) {
            print("$i ")
        }
        println()
        //until:左闭右开 升序
        for (i in 0 until 15) {
            print("$i ")
        }
        println()

        for (i in 0 until 15 step 3) {
            print("$i ")
        }
        println()

        for (i in 0 downTo 15) {
            print("$i ")
        }
        println()

        for (i in 0 downTo 15 step 3) {
            print("$i ")
        }
        println()

    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_test -> {
                // val var const
                testValVarConst()

                //kotlin空安全
                println(getValue(tv2.text.toString(), tv3.text.toString()))
                println(getValue(tv1?.text?.toString(), tv2.text.toString(), tv3.text.toString()))

                var age1: Int = 16
                age1 += 1
                var age2: Int = 28
                age2 -= 1
                //when
                println(getMaxAge(age1, age2))
                println(getSexTitle(tv2.text.toString()))
                println(getAgeType(age1.toString()))

                //重载
                println(jvmOverloadTest(("第一个参数")))
                println(jvmOverloadTest("第一个参数", "第二个参数"))
                println(jvmOverloadTest("第一个参数", "第二个参数", "第三个参数"))

                //循环
                loopTest()
                //数组
                arrayTest()
                //集合
                collectionTest()
            }
            else -> {

            }
        }
    }

}