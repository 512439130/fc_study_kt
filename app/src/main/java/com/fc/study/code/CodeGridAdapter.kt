/*
 * Copyright (c) 2015 - present Hive-Box.
 */

package com.fc.study.code

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fc.study.kt.databinding.ItemCodeBinding


class CodeGridAdapter : RecyclerView.Adapter<CodeGridAdapter.CodeViewHolder>() {
    private lateinit var dataList: ArrayList<Code>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeViewHolder {
        return CodeViewHolder(
            ItemCodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: CodeViewHolder, position: Int) {
        var codeBitmap = dataList[position].codeBitmap
        var codeContent = dataList[position].codeContent
        holder.bindView(codeBitmap,codeContent?:"")
    }

    override fun getItemCount(): Int {
        return dataList.size ?: 0
    }

    fun addList(list: ArrayList<Code>) {
        this.dataList = list
    }

    fun clearData() {
        this.dataList = ArrayList()
    }

    class CodeViewHolder(private val binding: ItemCodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(codeBitmap: Bitmap, content: String) {
            binding.apply {
                ivCode.setImageBitmap(codeBitmap)
                codeContent = content
            }
        }
    }
}
