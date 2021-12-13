//顶层方法的类重命名：StatusUtil.topTest()
@file:JvmName("StatusUtil")
package com.fc.study.stat

//Kotlin 静态方法方式
// 1、使用 @JvmStatic 注解，且注解只能加在伴生类和单例类上的方法上面
// 2、定义顶层方法

//顶层方法
fun topTest(){
    println("top-Test")
}

//静态方法-伴生类-@JvmStatic
class StaticClassTest {

    //为当前类创建伴生类
    companion object Test{
        @JvmStatic
        fun doStatusAction(){
            println("companion-object-doStatusAction")
        }
        fun doAction(){
            println("companion-object-doAction")
        }

    }

    fun main(){
        doAction()
    }
}

//静态方法-单例类-@JvmStatic
object SingleClassTest {
    @JvmStatic
    fun doStaticAction(){
        println("-------------doStaticAction-----------")
    }

    fun doAction(){
        println("-------------doAction-----------")
    }
}