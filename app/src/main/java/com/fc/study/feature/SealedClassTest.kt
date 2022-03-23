package com.fc.study.feature

// 1）、使用 sealed class 定义一个密封类
// 2）、密封类及其子类，只能定义在同一个文件的顶层位置
// 3）、密封类可被继承
// 4）、当我们使用条件语句的时候，需要实现密封类所有子类的情况，避免写出永远不会执行的代码

sealed class Result
object Success: Result()
object Failure: Result()

fun sealedTest(result: Result) = when(result){
    is Success -> "Result is Success"
    is Failure -> "Result is Failure"
}



// 对比
// 新增实现类不会强制规范
interface Result2
class Success2: Result2
class Failure2: Result2
class Error2: Result2

fun interfaceTest(result: Result2) = when(result) {
    is Success2 -> "Result is Success2"
    is Failure2 -> "Result is Failure2"

    else -> {
        "Result is else"
    }
}