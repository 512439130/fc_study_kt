/*
 * Copyright (c) 2015 - present Hive-Box.
 */

package com.fc.study.code

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fc.study.kt.R
import com.fc.study.kt.databinding.ActivityCodeBinding
import com.fc.study.util.createQRCode
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel


class CodeActivity : Activity() {
    private lateinit var bitmapList: ArrayList<Code>
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var gridAdapter: CodeGridAdapter
    private var binding: ActivityCodeBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_code)
        initView()
    }

    private fun initView() {
        gridAdapter = CodeGridAdapter()
        layoutManager = GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)
        binding?.rvCodeGrid?.layoutManager = layoutManager
        binding?.rvCodeGrid?.adapter = gridAdapter
        createQrContents()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun createQrContents() {
        bitmapList = ArrayList()
        for (i in 1..9) {
            val contentStart = "JSDJ;2;00c046a5-9448-4018-a205-3cdf06f674ff;"
            val contentEnd = "报销2020[0401]0000$i"
            bitmapList.add(Code(createQrCode(contentStart + contentEnd), contentEnd))
        }
        gridAdapter.addList(bitmapList)
        gridAdapter.notifyDataSetChanged()
    }

    private fun createQrCode(content: String): Bitmap {
        return createQRCode(
            content,
            100,
            100,
            "GB2312",
            ErrorCorrectionLevel.L,
            4,
            getColor(R.color.color_black),
            getColor(R.color.color_white)
        )
    }
}
