package com.fc.study.data

import java.io.Serializable

data class User(
    var name: String?,
    var sex: String?,
    var age: String?,
):Serializable{
    init {
        println("init data class User")
    }

    constructor():this("","男","25"){
        println("constructor data class User")
    }

}
