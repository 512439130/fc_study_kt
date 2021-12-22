package com.fc.study.kt

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.fc.study.const.INTENT_AGE
import com.fc.study.const.INTENT_NAME
import com.fc.study.const.INTENT_SEX
import com.fc.study.data.DataClassTest
import com.fc.study.feature.*
import com.fc.study.inter.StudyInterface
import com.fc.study.stat.SingleClassTest
import com.fc.study.stat.StaticClassTest
import com.fc.study.stat.topTest
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import java.util.*
import kotlin.system.measureTimeMillis

private const val TAG: String = "TestClassKotlin"

var acquired = 0

class Resource {
    init {
        acquired++  // Acquire the resource
    }

    fun close() {
        acquired--
    }
}

/**
 * Kotlin 基础学习
 */
@SuppressLint("SetTextI18n")
class TestClassKotlin : Activity(), View.OnClickListener, StudyInterface,
    GenericInterfaceTest<String> {
    //val var
    private val test1 = "test1"
    private var test2 = "test2"
    private val test3: Int = 666
    private var test4: Double = 88.88
    private var test5: String = "test5"

    private var tv1: TextView? = null
    private lateinit var tv2: TextView
    private lateinit var tv3: TextView
    private lateinit var tvContentBlocking: TextView
    private lateinit var tvContentLaunch: TextView

    private lateinit var btnKTStudy: Button
    private lateinit var btnCoroutineBlocking: Button
    private lateinit var btnCoroutineInit: Button
    private lateinit var btnCoroutineStart: Button
    private lateinit var btnCoroutineCancel: Button
    private lateinit var btnCoroutineContextTest: Button
    private lateinit var btnCoroutineLaunchModeTest: Button
    private lateinit var btnCoroutineAsyncTest: Button
    private lateinit var btnCoroutineSuspendTest: Button
    private lateinit var btnCoroutineFinallyTest: Button
    private lateinit var btnCoroutineTimeOutTest: Button
    private lateinit var btnCoroutineCombinedSuspendTest: Button
    private lateinit var btnCoroutineChannelTest: Button
    private lateinit var btnCoroutineAsyncStreamTest: Button

    //lateinit：延迟初始化
    private lateinit var testLateInit: String

    //coroutineTestContent
    var coroutineBlockingContent = ""
    var coroutineLaunchContent = ""

    //使用 by lazy 对一个变量延迟初始化
    //特点：该属性调用的时候才会初始化，且 lazy 后面的 Lambda 表达式只会执行一次
    private val testByLazyTest: String by lazy {
        println("lazy init testByLazyTest")
        "this is kt"
    }

    //协程作用域
    private lateinit var scopeTest: CoroutineScope

    //协程
    private lateinit var jobTest: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_base_study)
        init()
    }

    private fun init() {
        initView()
        initData()
        initListener()
    }

    private fun initJob() {
        println("-----------job-init-------------")
        //GlobalScope.launch有可能导致无法预料的内存泄漏
        scopeTest = CoroutineScope(context = Dispatchers.Main)
        jobTest = scopeTest.launch(start = CoroutineStart.LAZY) {
            repeat(times = 5) {
                delay(2000)
                val currentContent = "协程执行循环变量$it 线程id：${Thread.currentThread().id}\n"
                coroutineLaunchContent += currentContent
                Log.d(TAG, currentContent)
                withContext(Dispatchers.Main) {
                    tvContentLaunch.text = coroutineLaunchContent
                }
            }
            Log.e(TAG, "协程执行结束")
        }
    }

    private fun initView() {
        tv1 = findViewById(R.id.tv_1)
        tv2 = findViewById(R.id.tv_2)
        tv3 = findViewById(R.id.tv_3)
        tvContentBlocking = findViewById(R.id.tv_content_blocking)
        tvContentLaunch = findViewById(R.id.tv_content_launch)

        btnKTStudy = findViewById(R.id.btn_kt_study)
        btnCoroutineBlocking = findViewById(R.id.btn_coroutine_blocking)
        btnCoroutineInit = findViewById(R.id.btn_coroutine_init)
        btnCoroutineStart = findViewById(R.id.btn_coroutine_start)
        btnCoroutineCancel = findViewById(R.id.btn_coroutine_cancel)
        btnCoroutineContextTest = findViewById(R.id.btn_coroutine_context_test)
        btnCoroutineLaunchModeTest = findViewById(R.id.btn_coroutine_launch_mode_test)
        btnCoroutineSuspendTest = findViewById(R.id.btn_coroutine_suspend_test)
        btnCoroutineAsyncTest = findViewById(R.id.btn_coroutine_async_test)
        btnCoroutineFinallyTest = findViewById(R.id.btn_coroutine_finally_test)
        btnCoroutineTimeOutTest = findViewById(R.id.btn_coroutine_time_out_test)
        btnCoroutineCombinedSuspendTest = findViewById(R.id.btn_coroutine_combined_suspend_test)
        btnCoroutineChannelTest = findViewById(R.id.btn_coroutine_channel_test)
        btnCoroutineAsyncStreamTest = findViewById(R.id.btn_coroutine_async_stream_test)

    }

    private fun initListener() {
        btnKTStudy.setOnClickListener(this)
        btnCoroutineBlocking.setOnClickListener(this)
        btnCoroutineInit.setOnClickListener(this)
        btnCoroutineStart.setOnClickListener(this)
        btnCoroutineCancel.setOnClickListener(this)
        btnCoroutineContextTest.setOnClickListener(this)
        btnCoroutineLaunchModeTest.setOnClickListener(this)
        btnCoroutineSuspendTest.setOnClickListener(this)
        btnCoroutineAsyncTest.setOnClickListener(this)
        btnCoroutineFinallyTest.setOnClickListener(this)
        btnCoroutineTimeOutTest.setOnClickListener(this)
        btnCoroutineCombinedSuspendTest.setOnClickListener(this)
        btnCoroutineChannelTest.setOnClickListener(this)
        btnCoroutineAsyncStreamTest.setOnClickListener(this)
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
        val testSet2 = mutableSetOf("Set-001", "Set-002", "Set-003")
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
        var j = 0
        while (j <= 15) {
            print("$j ")
            j++
        }
        println()

        var k = 0
        do {
            print("$k ")
            k++
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


        //repeat循环
        repeat(times = 10) {
            print("$it ")
        }
        println()

    }

    private fun lambdaTest() {
        //1.Lambda 就是一段可以作为参数传递的代码，它可以作为函数的参数，返回值，同时也可以赋值给一个变量
        val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape", "Watermelon")
        val lambda = { title: String -> title.length }
        val maxLengthTitle = list.maxByOrNull(lambda)
        println("maxLengthTitle: $maxLengthTitle")
//        //2.集合函数式API通过lambda表达式
//        val maxLengthTitle2 = list.minByOrNull({
//                title:String -> title.length
//        })
//        println("maxLengthTitle2: $maxLengthTitle2")
        //3Kotlin 中规定，当 Lambda 表达式作为函数的最后一个参数的时候，我们可以把 Lambda 表达式移到函数括号的外面
//        val maxLengthTitle3 = list.maxByOrNull(){
//                title: String -> title.length
//        }
//        println("maxLengthTitle3: $maxLengthTitle3")

        //4.Kotlin 中规定，当 Lambda 表达式是函数的唯一参数的时候，函数的括号可以省略
        val maxLengthTitle4 = list.maxByOrNull { title: String ->
            title.length
        }
        println("maxLengthTitle4: $maxLengthTitle4")

        //5.当 Lambda 表达式的参数列表中只有一个参数的时候，我们可以把参数给省略，默认会有个 it 参数
        val maxLengthTitle5 = list.maxByOrNull {
            it.length
        }
        println("maxLengthTitle5: $maxLengthTitle5")
    }

    private fun javaLambdaOptimizationTest() {
//        Thread(object: Runnable{
//            override fun run() {
//                println("Thread-Id1: ${Thread.currentThread()}")
//            }
//        }).start()

        //lambda简化
//        Thread(Runnable {
//            println("Thread-Id2: ${Thread.currentThread()}")
//        }).start()

        //因为是单抽象方法接口，我们可以将接口名进行省略
//        Thread({
//            println("Thread-Id3: ${Thread.currentThread()}")
//        }).start()

        //当 Lambda 表达式作为函数的最后一个参数的时候，我们可以把 Lambda 表达式移到函数括号的外面
//        Thread(){
//            println("Thread-Id4: ${Thread.currentThread()}")
//        }.start()

        //当 Lambda 表达式是函数的唯一参数的时候，函数的括号可以省略
        Thread {
            println("Thread-Id5: ${Thread.currentThread()}")
        }.start()
    }


    private fun nullSafeTest() {
        //1）、在类型后面加上 ? ，表示可空类型，Kotlin 默认所有的参数和变量不可为空
        val nullTest1: Button? = null
        //2）、在对象调用的时候，使用 ?. 操作符，它表示如果当前对象不为空则调用，为空则什么都不做
        nullTest1?.text = "nullTest"
        //3）、?: 操作符表示如果左边的结果不为空，返回左边的结果，否则返回右边的结果
        val a: Int
        val b = null
        val c = 3
        a = b ?: c
        println("flag $a")
        //4）、在对象后面加 !! 操作符表示告诉Kotlin我这里一定不会为空,你不用进行检测了，如果为空，则抛出空指针异常
//        var nullTest2:Map<String,String>? = null
//        nullTest2 = mutableMapOf<String,String>()
//        nullTest2["name"] = "yy"
//        println("nullTest2: ${nullTest2!!.get("name")}")
        //5）、let 函数，提供函数式 Api，并把当前调用的对象当作参数传递到 Lambda 表达式中
    }

    private fun letTest(studyInterface: StudyInterface?) {
        studyInterface?.readBooks()
        studyInterface?.doHomeWork()
        studyInterface?.write()

        println("----------------------let----------------------")
        studyInterface.let {
            it?.readBooks()
            it?.doHomeWork()
            it?.write()
        }

    }

    private fun standardFunction() {
        //内联扩展函数let,also,with,run,apply

        val name = "yangyang"
        val age = 20

        //1）、let 函数，必须让某个对象调用，接收一个 Lambda 表达式参数，Lambda 表达式中的参数为当前调用者，且最后一行代码作为返回值
        val returnValue1 = StringBuilder().let {
            it.append(name).append(" ").append(age)
            it.append("~~~")
        }
        println("returnValue1: $returnValue1")

        //2）、also 函数，必须让某个对象调用，接收一个 Lambda 表达式参数，Lambda 表达式中的参数为当前调用者，无法指定返回值，这个函数返回的是当前调用对象本身
        val returnValue2 = StringBuilder().also {
            it.append(name).append(" ").append(age)
        }
        println("returnValue3: $returnValue2")

        //3）、with 函数，接收两个参数，第一个为任意类型参数，第二个为 Lambda 表达式参数，Lambda 表达式中拥有第一个参数的上下文 this ，且最后一行代码作为返回值
        val returnValue3 = with(StringBuilder()) {
            this.append(name).append(" ").append(age)
        }
        println("returnValue3: $returnValue3")

        //4）、run 函数，必须让某个对象调用，接收一个 Lambda 表达式参数，Lambda 表达式中拥有当前调用对象的上下文 this ，且最后一行代码作为返回值
        val returnValue4 = StringBuilder().run {
            this.append(name).append(" ").append(age)
        }
        println("returnValue4: $returnValue4")
        //5）、apply 函数，必须让某个对象调用，接收一个 Lambda 表达式参数，Lambda 表达式中拥有当前调用对象的上下文 this ，无法指定返回值，这个函数返回的是当前调用对象本身
        val stringBuilder5 = StringBuilder().apply {
            this.append(name).append(" ").append(age)
        }
        println("stringBuilder5: $stringBuilder5")

        functionTest()
    }

    //apply函数的返回值是本身，在函数内部可以任意调用对象的属性或方法或给属性赋值等操作
    private fun functionTest() {
        //let函数
        letTest(this)

        val data: DataClassTest? = DataClassTest()
        data?.apply {
            name = "Test Name"
            sex = "Test Sex"
            age = 18
        }?.hello()

        data?.run {
            name = "Test run Name"
            sex = "Test run Sex"
            age = 18
        }
        data?.hello()

        with(data) {
            this?.name = "Test run Name"
            this?.sex = "Test run Sex"
            this?.age = 18
        }

    }

    private fun staticMethod() {
        println("-----------staticMethod-------------")
        //1.1 伴生类-@JvmStatic-静态方法
        StaticClassTest.doStaticAction()
        //1.2 单例类-@JvmStatic-静态方法
        SingleClassTest.doStaticAction()
        //2.顶层方法-静态方法
        topTest()

        //伴生类
        StaticClassTest.doAction()
        //单例类
        SingleClassTest.doAction()
    }

    private fun lateInitTest() {
        println("-----------lateInitTest-------------")

        println("testInitTest-isInitialized1: ${::testLateInit.isInitialized}")
        if (!::testLateInit.isInitialized) {
            testLateInit = "this is kotlin"
            println("testInitTest-isInitialized2: ${::testLateInit.isInitialized}")
        }
        println("testInitTest-value: $testLateInit")

        println("-----------ByLazyTest-------------")
        println("testByLazyTest $testByLazyTest")
    }

    private fun sealedClassTest() {
        println("-----------sealedClassTest-------------")
        val content1 = sealedTest(result = Success)
        println("sealedClassTest $content1")
        val result2: Result2
        result2 = Error2()
        val content2 = interfaceTest(result2)
        println("interfaceTest $content2")
    }

    private fun dataClassTest() {
        println("-----------dataClassTest-------------")
        val dataTest1 = DataClassTest()
        println("dataClassTest-dataTest1-init: $dataTest1")
        dataTest1.name = "yangyang"
        dataTest1.sex = "男"
        dataTest1.age = 27
        println("dataClassTest-dataTest1-set: $dataTest1")

        val dataTest2 = DataClassTest(name = "SoMustYY", sex = "男", age = 28)
        println("dataClassTest-dataTest2: $dataTest2")
        //copy
        val newDataTest2 = dataTest2.copy(name = "Summer")
        println("dataClassTest-newDataTest2: $newDataTest2")
        //componentN:解构声明
        val (name, sex, age) = newDataTest2
        println("dataClassTest-componentN-name: $name")
        println("dataClassTest-componentN-name: $sex")
        println("dataClassTest-componentN-name: $age")
    }

    private fun extensionTest() {
        println("-----------extensionTest-------------")
        val stringTest = "SoMustYY"
        println("genericTest ${stringTest.printValue()}")
    }

    private fun genericTest() {
        println("-----------genericTest-------------")

        val genericTest1 = GenericClassTest<String>()
        genericTest1.genericTest("SoMustYY")

        val genericTest2 = GenericClassTest2()
        genericTest2.genericTest("SoMustYY")
        genericTest2.genericTest(666)

        val genericInterface = this
        genericInterface.interfaceMethod("SoMustYY")
    }

    private fun coroutineBlockingTest() {
        println("-----------coroutineBlockingTest-start-------------")
        Log.e(TAG, "主线程id：${mainLooper.thread.id}")

        //runBlocking（阻塞式线程）启动的协程任务会阻断当前线程，直到协程执行结束，当协程执行结束后，页面才会显示出来
        runBlocking {
            repeat(times = 5) {
                val currentContent = "协程执行循环变量$it 线程id：${Thread.currentThread().id}\n"
                coroutineBlockingContent += currentContent
                Log.d(TAG, currentContent)
                //延迟1000ms
                delay(1000)
            }
            //协程执行结束，再执行下边的逻辑
            tvContentBlocking.text = coroutineBlockingContent
        }
        println("-----------coroutineBlockingTest-end-------------")

    }

    //suspend：挂起函数
    private suspend fun coroutineLaunchTest() {
        println("-----------coroutineLaunchTest-start-------------")
        if (::jobTest.isInitialized) {
            if (jobTest.isCancelled) {
                coroutineLaunchContent = ""
                tvContentLaunch.text = coroutineLaunchContent
                Toast.makeText(this, "Job is canceled , need to reinitialize", Toast.LENGTH_SHORT)
                    .show()
            } else if (!jobTest.isActive) {
                coroutineLaunchContent = ""
                tvContentLaunch.text = coroutineLaunchContent

                Log.e(TAG, "主线程id：${mainLooper.thread.id}")
                jobTest.join()
            } else {
                Toast.makeText(this, "Job 正在执行", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Job 未初始化", Toast.LENGTH_SHORT).show()
        }
    }

    @ObsoleteCoroutinesApi
    private fun coroutineContextTest() {
        println("-----------coroutineContextTest-start-------------")
        Log.e(TAG, "当前主线程-id：${mainLooper.thread.id}")
        CoroutineScope(Dispatchers.Main).launch {
            //主线程上运行一个协程,可以用来更新UI
            println("coroutineContextTest-Dispatchers.Main ${Thread.currentThread().id}")
        }
        CoroutineScope(Dispatchers.IO).launch {
            //在主线程之外执行磁盘或网络 I/O，在线程池中执行
            println("coroutineContextTest-Dispatchers.IO ${Thread.currentThread().id}")
        }
        CoroutineScope(Dispatchers.Default).launch {
            //执行 cpu 密集型的工作，在线程池中执行
            println("coroutineContextTest-Dispatchers.Default ${Thread.currentThread().id}")
        }
        CoroutineScope(Dispatchers.Unconfined).launch {
            //在调用的线程直接执行
            println("coroutineContextTest-Dispatchers.Unconfined ${Thread.currentThread().id}")
        }

        //创建一个新的线程
        val thread = newFixedThreadPoolContext(1, "SoMustYY")
        CoroutineScope(thread).launch {
            println("coroutineContextTest-newSingleThreadContext ${Thread.currentThread().id}")
        }
        thread.close()
        println("-----------coroutineContextTest-end-------------")
    }

    @ExperimentalCoroutinesApi
    private suspend fun coroutineLaunchModeTest() {
        println("-----------coroutineLaunchMode-start-------------")

        // 立即执行协程体，随时可以取消
        val jobDefault =
            CoroutineScope(context = Dispatchers.Main).launch(start = CoroutineStart.DEFAULT) {
                println("coroutineLaunchMode-DEFAULT-1111")
            }
        jobDefault.cancel()
        println("coroutineLaunchMode-DEFAULT-2222")

        // 立即执行协程体，但在开始运行协程体之前无法取消
        val jobAtomic =
            CoroutineScope(context = Dispatchers.Main).launch(start = CoroutineStart.ATOMIC) {
                println("coroutineLaunchMode-ATOMIC-1111")
            }
        jobAtomic.cancel()
        println("coroutineLaunchMode-ATOMIC-2222")


        // 只有在用户需要的情况下运行
        // 分离协程的创建和执行
        val jobLazy =
            CoroutineScope(context = Dispatchers.Main).launch(start = CoroutineStart.LAZY) {
                println("coroutineLaunchMode-LAZY-1111")
                delay(2000)
            }
        println("coroutineLaunchMode-LAZY-2222")
//        jobLazy.start()
        jobLazy.join()
        println("coroutineLaunchMode-LAZY-3333")

        //立即在当前线程执行协程体，直到第一个 suspend 调用
        val jobUnDisPatched =
            CoroutineScope(context = Dispatchers.Main).launch(start = CoroutineStart.UNDISPATCHED) {
                println("coroutineLaunchMode-UNDISPATCHED-1111")
                //delay 挂起 3s
                delay(3000)
                println("coroutineLaunchMode-UNDISPATCHED-2222")
            }
        println("coroutineLaunchMode-UNDISPATCHED-3333")
        //join 要求等待协程体执行完
        jobUnDisPatched.join()
        println("coroutineLaunchMode-UNDISPATCHED-4444")

        println("-----------coroutineLaunchMode-end-------------")
    }

    private fun coroutineAsyncTest() {
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(context = Dispatchers.Default).launch {
                val result1 = CoroutineScope(Dispatchers.Default).async {
                    getResult1()
                }

                val result2 = CoroutineScope(Dispatchers.Default).async {
                    getResult2()
                }

                val result = result1.await() + result2.await()
                Log.e(TAG, "result = $result")
            }
        }
    }

    private suspend fun getResult1(): Int {
        delay(2000)
        return 1
    }

    private suspend fun getResult2(): Int {
        delay(4000)
        return 2
    }

    private fun coroutineSuspendTest() {
        CoroutineScope(context = Dispatchers.Main).launch {
            val token = getToken()
            val userInfo = getUserInfo(token)
            setUserInfo(userInfo)
        }
    }

    private suspend fun getToken(): String {
        delay(2000)
        return "SoMustYY"
    }

    private suspend fun getUserInfo(token: String): String {
        delay(1000)
        return "$token _test1_test2"
    }

    private fun setUserInfo(userInfo: String) {
        println("userInfo: $userInfo")
    }

    private fun coroutineFinallyTest() {
        println("-----------coroutineFinallyTest-start-------------")

        //在 finally 中释放资源
        runBlocking {
            val job = launch {
                try {
                    repeat(100) { i ->
                        //调用ensureActive，非活跃会抛出取消的异常
                        ensureActive()
                        println("job1: I'm sleeping $i ...")
                        delay(500)
                    }
                } catch (e: Exception) {
                    println("job1: Exception $e")
                } finally {
                    println("job1: I'm running finally")
                    delay(1000L)
                    //cancelAndJoin后调用
                    println("job1: I'm running finally-delay")
                }
            }
            //延迟5s后执行 cancelAndJoin
            delay(5000L)
            println("main1: I'm tired of waiting!")
            job.cancelAndJoin()
            println("main1: Now I can quit.")
        }

        //运行不能取消的代码块
        runBlocking {
            val job = launch {
                var i = 0
                try {
                    while (isActive) {
                        println("job2: I'm sleeping $i ...")
                        delay(500L)
                        i++
                    }
                } catch (e: CancellationException) {
                    println("job2: Exception $e")
                } finally {
                    withContext(NonCancellable) {
                        println("job2: I'm running finally")
                        delay(1000L)
                        println("job2: And I've just delayed for 1 sec because I'm non-cancellable")
                    }
                }
            }
            //延迟2s后执行 cancelAndJoin
            delay(2000)
            println("main2: I'm tired of waiting!")
            job.cancelAndJoin()
            println("main2: Now I can quit.")
        }
        println("-----------coroutineFinallyTest-end-------------")
    }


    private fun coroutineTimeOutTest() {
        println("-----------coroutineTimeOutTest-start-------------")
        runBlocking {
            //使用 withTimeoutOrNull 后 不再抛出异常，而 withTimeoutOrNull 通过返回 null 来进行超时操作，从而替代抛出一个异常
            val result = withTimeoutOrNull(1500L) {
                repeat(1000) { i ->
                    println("I'm sleeping $i ...")
                    delay(500L)
                }
            }
            println("Result is $result")
        }


        //变量中存储对资源的引用，资源不泄露
        runBlocking {
            repeat(1) {
                launch {
                    var resource: Resource? = null
                    try {
                        withTimeout(60) {
                            delay(50)
                            resource = Resource()
                            println("acquired-init: $acquired")
                        }
                    } catch (e: Exception) {
                        println("Exception: $e")
                    } finally {
                        println("resource?.close-1111")
                        resource?.close()
                        println("resource?.close-2222")
                    }
                }
            }
            println("acquired-end: $acquired")
        }
        println("-----------coroutineTimeOutTest-end-------------")
    }

    private suspend fun coroutineCombinedSuspendTest() {
        println("-----------coroutineCombinedSuspendTest-start-------------")
        //默认顺序调用
        val time1 = measureTimeMillis {
            val one = doSomeThingOne()
            val two = doSomethingTwo()
            val result = one + two
            println("result1 $result")
        }
        println("Completed1 in $time1 ms")

        //并发调用
        val time2 = measureTimeMillis {
            val one = CoroutineScope(Dispatchers.IO).async {
                doSomeThingOne()
            }
            val two = CoroutineScope(Dispatchers.IO).async {
                doSomethingTwo()
            }
            val result = one.await() + two.await()
            println("result2 $result")
        }
        println("Completed2 in $time2 ms")


        val time3 = measureTimeMillis {
            val one = CoroutineScope(Dispatchers.Unconfined).async(start = CoroutineStart.LAZY) {
                doSomeThingOne()
            }
            val two = CoroutineScope(Dispatchers.IO).async(start = CoroutineStart.LAZY) {
                doSomethingTwo()
            }
            //如果我们只调用 await，而没有在单独的协程中调用 start，这将会导致 顺序执行
            one.start()
            two.start()
            val result3 = one.await() + two.await()
            println("result3 $result3")
        }
        println("Completed3 in $time3 ms")


        //结构化并发能为：
        //1. 消任务，当任务不再被需要的时候；
        //2. 跟踪任务，当任务正在运行的时候；
        //3. 报错，当协程失败的时候；
        //结构化并发的保证：
        //1. 当一个 scope 取消的时候，它里面所有的协程都会被取消；
        //2. 当一个 suspend 方法 return 的时候，它所有的工作都已完成；
        //3. 当一个协程出错的时候，他的调用者或 scope 会收到通知；

        println("-----------coroutineCombinedSuspendTest-end-------------")
    }

    private suspend fun doSomeThingOne(): Int {
        delay(1000L)
        return 15
    }

    private suspend fun doSomethingTwo(): Int {
        delay(1500L)
        return 25
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    private fun coroutineChannelTest() {
        println("-----------coroutineChannelTest-start-------------")
        //通道：channel send receive
        runBlocking {
            val channel = Channel<Int>()
            launch {
                for (i in 1..5) {
                    channel.send(i * i)
                }
            }
            repeat(5) {
                println("receive: ${channel.receive()}")
            }
            println("Done!")
        }

        runBlocking {
            delay(1000)
        }

        //关闭与迭代通道
        runBlocking {
            val channel = Channel<Int>()
            launch {
                for (i in 1..8) {
                    channel.send(i * i * 2)
                }
                channel.close()
            }

            for (j in channel) {
                println("channel: $j")
            }
            println("Done!")
        }

        runBlocking {
            delay(1000)
        }

        //构建通道生产者
        runBlocking {
            val squares = produceSquares()
            //迭代器
            squares.consumeEach {
                println("square: $it")
            }
            println("Done!")
        }

        //管道

        runBlocking {
            val numbers = produceNumber()
            val squares = square(numbers)
            repeat(5) {
                println(squares.receive())
            }
            println("Done!")
            // 取消所有子协程来让主协程结束，释放资源
            coroutineContext.cancelChildren()
        }

        println("-----扇出-----")
        //扇出：多个协程也许会接收相同的管道
        runBlocking {
            val producer = produceNumbers()
            repeat(5) {
                launchProcessor(it, producer)
            }
            delay(900)
            // 取消协程生产者从而将它们全部杀死
            producer.cancel()
        }
        println("-----扇入-----")
        //扇入：多个协程可以发送到同一个通道
        runBlocking {
            val channel = Channel<String>()
            //开启多个子协程发送到同一个通道，分布式工作
            launch {
                sendString(channel, "yy", 200L)
            }
            launch {
                sendString(channel, "test", 200L)
            }
            launch {
                sendString(channel, "zh", 500L)
            }

            repeat(10) {
                println(channel.receive())
            }

            //取消所有子协程，使主协程结束
            coroutineContext.cancelChildren()
        }


        //带缓冲的通道
        runBlocking {
            val channel = Channel<Int>(capacity = 4) //启动带缓冲的通道
            //启动发送者协程
            val sender = launch {
                repeat(10) {
                    println("Test Sending $it")
                    channel.send(it) //将在缓冲区被占满时挂起
                }
            }
            delay(1000)
            //取消发送者协程
            sender.cancel()

        }
        //通道的公平性，先进先出（FIFO）


        println("-----计时器通道-----")
        //计时器通道
        runBlocking {
            //创建计时器通道
            val tickerChannel = ticker(delayMillis = 100, initialDelayMillis = 0)
            var nextElement = withTimeoutOrNull(1) {
                tickerChannel.receive()
            }
            println("Initial element is available immediately: $nextElement")

            nextElement = withTimeoutOrNull(50) {
                tickerChannel.receive()
            }

            println("Next element is not ready in 50 ms: $nextElement")

            nextElement = withTimeoutOrNull(49) {
                tickerChannel.receive()
            }
            println("Next element is ready in 100 ms: $nextElement")

            //模拟大量消费延迟
            println("Consumer pauses for 150m")
            delay(150)

            // 下一个元素立即可用
            nextElement = withTimeoutOrNull(1) {
                tickerChannel.receive()
            }
            println("Next element is available immediately after large consumer delay: $nextElement")
            // 请注意，`receive` 调用之间的暂停被考虑在内，下一个元素的到达速度更快
            nextElement = withTimeoutOrNull(49) {
                tickerChannel.receive()
            }
            println("Next element is ready in 50ms after consumer pause in 150ms: $nextElement")

            tickerChannel.cancel() // 表明不再需要更多的元素
        }

        println("-----------coroutineChannelTest-end-------------")
    }


    private fun coroutineAsyncStreamTest() {
        TODO("Not yet implemented")
    }

    @ExperimentalCoroutinesApi
    private fun CoroutineScope.produceNumbers() = produce<Int> {
        var i = 1
        while (true) {
            send(i++)
            delay(100)
        }
    }

    private fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
        for (msg in channel) {
            println("Processor #$id received $msg")
        }
    }

    private suspend fun sendString(channel: SendChannel<String>, content: String, time: Long) {
        while (true) {
            delay(time)
            channel.send(content)
        }
    }


    //创建协程的所有函数都被定义为 CoroutineScope 的扩展，因此我们可以依赖结构化并发来确保应用程序中没有延迟的全局协程
    @ExperimentalCoroutinesApi
    private fun CoroutineScope.produceNumber() = produce<Int> {
        var i = 5
        while (true) {
            send(i++)
        }
    }

    //创建协程的所有函数都被定义为 CoroutineScope 的扩展，因此我们可以依赖结构化并发来确保应用程序中没有延迟的全局协程
    @ExperimentalCoroutinesApi
    private fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
        for (i in numbers) {
            send(i * i)
        }
    }


    //produce: 便捷的协程构建器
    @ExperimentalCoroutinesApi
    private fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
        for (i in 2..10) {
            send(i * i)
        }
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_kt_study -> {
                // val var const
                testValVarConst()

                //kotlin空安全
                println(getValue(tv2.text.toString(), tv3.text.toString()))
                println(getValue(tv1?.text?.toString(), tv2.text.toString(), tv3.text.toString()))

                var age1 = 16
                age1 += 1
                var age2 = 28
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

                //lambda表达式
                lambdaTest()
                //lambda简化
                javaLambdaOptimizationTest()

                //空指针检查
                nullSafeTest()

                //内联扩展函数
                standardFunction()

                //静态方法
                staticMethod()

                //延迟初始化
                lateInitTest()

                //密封类
                sealedClassTest()

                //数据类
                dataClassTest()

                //扩展函数
                extensionTest()

                //泛型
                genericTest()
            }
            R.id.btn_coroutine_blocking -> {
                //协程-阻塞式
                coroutineBlockingTest()
            }
            R.id.btn_coroutine_init -> {
                initJob()
            }
            R.id.btn_coroutine_start -> {
                CoroutineScope(context = Dispatchers.Main).launch {
                    //协程-非阻塞式
                    coroutineLaunchTest()
                }
            }
            R.id.btn_coroutine_cancel -> {
                if (::jobTest.isInitialized) {
                    println("-----------job-cancel-------------")
                    jobTest.cancel()
                }
            }

            R.id.btn_coroutine_context_test -> {
                //上下文
                coroutineContextTest()
            }
            R.id.btn_coroutine_launch_mode_test -> {
                CoroutineScope(context = Dispatchers.Main).launch {
                    //启动模式
                    coroutineLaunchModeTest()
                }
            }

            R.id.btn_coroutine_suspend_test -> {
                //suspend 挂起函数
                coroutineSuspendTest()
            }

            R.id.btn_coroutine_async_test -> {
                //async
                coroutineAsyncTest()
            }
            R.id.btn_coroutine_finally_test -> {
                //finally
                coroutineFinallyTest()
            }
            R.id.btn_coroutine_time_out_test -> {
                //time out
                coroutineTimeOutTest()
            }

            R.id.btn_coroutine_combined_suspend_test -> {
                //组合挂起函数
                CoroutineScope(Dispatchers.Main).launch {
                    coroutineCombinedSuspendTest()
                }
            }
            R.id.btn_coroutine_channel_test -> {
                coroutineChannelTest()
            }
            R.id.btn_coroutine_async_stream_test -> {
                coroutineAsyncStreamTest()
            }
            else -> {

            }
        }
    }

    override fun readBooks() {
        println("StudyInterface-readBooks")
    }

    override fun doHomeWork() {
        println("StudyInterface-doHomeWork")
    }

    override fun write() {
        println("StudyInterface-write")
    }

    override fun interfaceMethod(params: String) {
        println("interfaceMethod $params")
    }
}