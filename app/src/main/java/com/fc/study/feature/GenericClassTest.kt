package com.fc.study.feature

//为该类定义泛型
class GenericClassTest<T>{
    fun genericTest(params:T){
        println("GenericClassTest-genericTest ${params.toString()}")
    }
}

class GenericClassTest2{
    //为该方法定义泛型
    fun <T> genericTest(params:T){
        println("GenericClassTest2-genericTest ${params.toString()}")
    }
}

//为该接口定义泛型
interface GenericInterfaceTest <T>{
    fun interfaceMethod(params: T)
}