package com.umc.healthper.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.databinding.ItemRoutinereadyWorkpartBinding

class RoutineReadyListAdapter(private val dataList: ArrayList<Int>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ListHolder).bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemRoutinereadyWorkpartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ListHolder(val binding: ItemRoutinereadyWorkpartBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }
}