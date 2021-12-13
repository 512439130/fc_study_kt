@file:JvmName("TopTestUtil")
package com.fc.study.kt

//修饰顶层的常量，不在class中
const val fileConstString: String = "fileConstTest"
const val fileConstBoolean: Boolean = false
fun fileTest(){
    println("fileTest $fileConstString")
    println("fileTest2 $fileConstBoolean")
}
//修饰object的成员
object ConstObject{
    private const val constTest1 = "constTest1"
    fun test(){
        println("constTest $constTest1")
    }
}