package com.umc.healthper.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.databinding.ItemWorkreadyWorkpartBinding

class DetailWorkRecordRvAdapter(): RecyclerView.Adapter<DetailWorkRecordRvAdapter.ViewHolder>() {

    interface Listener {
        fun onClick(pos: Int)
    }
    lateinit var userListener: Listener
    fun setListener(data: Listener) {
        userListener = data
    }

    lateinit var binding: ItemWorkreadyWorkpartBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemWorkreadyWorkpartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, userListener)
    }

    override fun getItemCount(): Int {
        return 3
    }

    class ViewHolder(val binding: ItemWorkreadyWorkpartBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, userListener: Listener) {
            binding.root.setOnClickListener {
                userListener.onClick(pos)
            }
        }
    }
}