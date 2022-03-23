package com.fc.study.observable

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.fc.study.kt.BR

class UserModel: BaseObservable() {
    @get:Bindable
    var name: String? = ""
        set(name) {
            field = name
            notifyPropertyChanged(BR.name)
        }

    var sex: String? = ""
        set(sex) {
            field = sex
            notifyPropertyChanged(BR.sex)
        }
    var age: String? = ""
        set(age) {
            field = age
            notifyPropertyChanged(BR.age)
        }

}