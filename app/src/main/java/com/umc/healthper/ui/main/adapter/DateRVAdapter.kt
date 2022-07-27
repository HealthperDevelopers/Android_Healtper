package com.umc.healthper.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.databinding.ItemMainCalendarListBinding

class DateRVAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateHolder {
        val binding = ItemMainCalendarListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DateHolder).bind()
    }

    override fun getItemCount(): Int {
        return 6
    }

    inner class DateHolder(private val binding: ItemMainCalendarListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
        }
    }


}