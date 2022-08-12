package com.umc.healthper.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.databinding.ItemWorkreadyWorkpartBinding
import com.umc.healthper.util.VarUtil

class WorkReadyListAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface onClickListener {
        fun onClick(pos: Int)
    }
    lateinit var listener: onClickListener

    fun setOnClickListener(listen: onClickListener) {
        listener = listen
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ListHolder).bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemWorkreadyWorkpartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListHolder(binding)
    }

    override fun getItemCount(): Int {
        return VarUtil.glob.selectedPart.size
    }

    inner class ListHolder(val binding: ItemWorkreadyWorkpartBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.itemWorkreadyWorkpartPartTv.text = VarUtil.glob.selectedPart[pos]
            setListener(pos)
        }

        fun setListener(pos: Int) {
            binding.root.setOnClickListener {
                listener.onClick(pos)
            }
        }
    }
}