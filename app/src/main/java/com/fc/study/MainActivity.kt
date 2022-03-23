package com.fc.study

import android.content.Context
import android.content.Intent
import android.database.DatabaseUtils
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import com.fc.study.base.BaseActivity
import com.fc.study.code.CodeActivity
import com.fc.study.const.INTENT_AGE
import com.fc.study.const.INTENT_NAME
import com.fc.study.const.INTENT_SEX
import com.fc.study.inter.ActivityInterface
import com.fc.study.kt.R
import com.fc.study.kt.TestClassKotlin
import com.fc.study.kt.databinding.ActivityMainBinding
import com.fc.study.observable.UserModel

class MainActivity : BaseActivity(), View.OnClickListener,TextWatcher, ActivityInterface {

    private lateinit var username: String
    private lateinit var sex: String
    private lateinit var age: String

    private var binding: ActivityMainBinding? = null
    private var userModel: UserModel? = null

    override fun init() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding?.userModel = userModel
        initView()
        initListener()
    }

    private fun initListener() {
        binding?.btnLogin?.setOnClickListener(this)
        binding?.etName?.addTextChangedListener(this);
        binding?.btnCode?.setOnClickListener(this)
    }


    private fun initView() {
        binding?.etName?.setText("杨阳")
        binding?.etSex?.text = Editable.Factory.getInstance().newEditable("男")
        binding?.etAge?.text = Editable.Factory.getInstance().newEditable("27")

        userModel = UserModel()
    }


    private fun login(username: String, sex: String, age: String) {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(sex) && !TextUtils.isEmpty(age)) {
            val intent = Intent(this, TestClassKotlin::class.java)
            intent.putExtra(INTENT_NAME, username)
            intent.putExtra(INTENT_SEX, sex)
            intent.putExtra(INTENT_AGE, age)
            startActivity(intent)
        } else {
            check()
            toast(this, "请输入")
        }
    }

    override fun showToast(context: Context, content: String) {
        toast(this,content)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_login -> {
                username = binding?.etName?.text.toString()
                sex = binding?.etSex?.text.toString()
                age = binding?.etAge?.text.toString()
                login(username, sex, age)
            }
            R.id.btn_code -> {
                startActivity(Intent(this, CodeActivity::class.java))
            }
            else -> {

            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        println("beforeTextChanged")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        println("onTextChanged")
    }

    override fun afterTextChanged(s: Editable?) {
        println("afterTextChanged ${userModel?.name }\n ${userModel?.sex } \n ${userModel?.age }")
    }
}