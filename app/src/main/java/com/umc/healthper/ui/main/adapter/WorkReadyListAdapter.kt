package com.umc.healthper.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.databinding.ItemWorkreadyWorkpartBinding

class WorkReadyListAdapter(private val dataList: ArrayList<Int>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface onClickListener {
        fun onClick()
    }
    lateinit var listener: onClickListener

    fun setOnClickListener(listen: onClickListener) {
        listener = listen
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ListHolder).bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemWorkreadyWorkpartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ListHolder(val binding: ItemWorkreadyWorkpartBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.itemWorkreadyWorkpartPartTv.text = dataList.toString()
            setListener()
        }

        fun setListener() {
            binding.root.setOnClickListener {
                listener.onClick()
            }
        }
    }
}