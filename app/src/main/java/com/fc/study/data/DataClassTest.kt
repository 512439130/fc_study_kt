package com.fc.study.data

import java.io.Serializable

// 数据类
// 数据类的特性
// 1.主构造函数需要至少有一个参数
// 2.主构造函数的所有参数需要标记为 val 或 var
// 3.数据类不能是抽象、开放、密封或者内部的
// 4.数据类是可以实现接口的，如(序列化接口)，同时也是可以继承其他类的，如继承自一个密封类

data class DataClassTest(
    var name: String = "YY",
    var sex: String = "男",
    var age: Int = 21,  // 默认值
): Serializable {
    init {
        println("init DataClassTest " + toString())
    }
    // 次构造函数
    constructor():this("zhanghui","女",25){
        println("constructor...")
    }

    fun hello(){
        println("${name}_${sex}_${age}")
    }
}
