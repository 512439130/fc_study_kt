package com.fc.study.kt

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.fc.study.base.BaseActivity
import com.fc.study.const.INTENT_AGE
import com.fc.study.const.INTENT_NAME
import com.fc.study.const.INTENT_SEX
import com.fc.study.inter.ActivityInterface

class MainActivity : BaseActivity(), View.OnClickListener, ActivityInterface {
    private lateinit var btnLogin: Button
    private lateinit var etUsername: EditText
    private lateinit var etSex: EditText
    private lateinit var etAge: EditText

    private lateinit var username: String
    private lateinit var sex: String
    private lateinit var age: String

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        initView()
    }

    private fun initView() {
        btnLogin = findViewById(R.id.btn_login)
        btnLogin.setOnClickListener(this)
        etUsername = findViewById(R.id.et_username)
        etSex = findViewById(R.id.et_sex)
        etAge = findViewById(R.id.et_age)

        etUsername.setText("杨阳")
        etSex.text = Editable.Factory.getInstance().newEditable("男")
        etAge.text = Editable.Factory.getInstance().newEditable("27")
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_login -> {
                username = etUsername.text.toString()
                sex = etSex.text.toString()
                age = etAge.text.toString()
                login(username, sex, age)
            }
            else -> {

            }
        }
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
}