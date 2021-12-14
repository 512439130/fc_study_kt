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
import java.util.*

private const val TAG: String = "TestClassKotlin"

/**
 * Kotlin 基础学习
 */
@SuppressLint("SetTextI18n")
class TestClassKotlin : Activity(), View.OnClickListener, StudyInterface, GenericInterfaceTest<String> {
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
    private lateinit var btnCoroutineLaunch: Button
    private lateinit var btnCoroutineLaunchCancel: Button

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

    private lateinit var launchJobTest:Job

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

    private fun initView() {
        tv1 = findViewById(R.id.tv_1)
        tv2 = findViewById(R.id.tv_2)
        tv3 = findViewById(R.id.tv_3)
        tvContentBlocking = findViewById(R.id.tv_content_blocking)
        tvContentLaunch = findViewById(R.id.tv_content_launch)

        btnKTStudy = findViewById(R.id.btn_kt_study)
        btnCoroutineBlocking = findViewById(R.id.btn_coroutine_blocking)
        btnCoroutineLaunch = findViewById(R.id.btn_coroutine_launch)
        btnCoroutineLaunchCancel = findViewById(R.id.btn_coroutine_launch_cancel)
    }

    private fun initListener() {
        btnKTStudy.setOnClickListener(this)
        btnCoroutineBlocking.setOnClickListener(this)
        btnCoroutineLaunch.setOnClickListener(this)
        btnCoroutineLaunchCancel.setOnClickListener(this)
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
        repeat(times = 10){
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
    private fun functionTest(){
        //let函数
        letTest(this)

        val data:DataClassTest? = DataClassTest()
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

        with(data){
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

        val dataTest2 = DataClassTest(name = "SoMustYY",sex = "男",age = 28)
        println("dataClassTest-dataTest2: $dataTest2")
        //copy
        val newDataTest2 = dataTest2.copy(name = "Summer",)
        println("dataClassTest-newDataTest2: $newDataTest2")
        //componentN:解构声明
        val(name,sex,age) = newDataTest2
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
                Log.d(TAG,currentContent)
                //延迟1000ms
                delay(1000)
            }
            //协程执行结束，再执行下边的逻辑
            tvContentBlocking.text = coroutineBlockingContent
        }
        println("-----------coroutineBlockingTest-end-------------")

    }

    private fun coroutineLaunchTest(){
        if(!::launchJobTest.isInitialized || !launchJobTest.isActive){
            coroutineLaunchContent = ""
            tvContentLaunch.text = coroutineLaunchContent
            println("-----------coroutineLaunchTest-start-------------")
            Log.e(TAG, "主线程id：${mainLooper.thread.id}")
            launchJobTest = GlobalScope.launch {
                repeat(times = 5){
                    delay(2000)
                    val currentContent = "协程执行循环变量$it 线程id：${Thread.currentThread().id}\n"
                    coroutineLaunchContent += currentContent
                    Log.d(TAG,currentContent)
                    withContext(Dispatchers.Main){
                        tvContentLaunch.text = coroutineLaunchContent
                    }
                }
                Log.e(TAG, "协程执行结束")
            }
        } else {
            Toast.makeText(this,"Launch 正在执行",Toast.LENGTH_SHORT).show()
        }
    }


    override fun interfaceMethod(params: String) {
        println("interfaceMethod $params")
    }


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
            R.id.btn_coroutine_launch -> {
                //协程-非阻塞式
                coroutineLaunchTest()
            }
            R.id.btn_coroutine_launch_cancel -> {

                if(::launchJobTest.isInitialized){
                    launchJobTest.cancel()
                }
            }

            //协程-阻塞式
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
}