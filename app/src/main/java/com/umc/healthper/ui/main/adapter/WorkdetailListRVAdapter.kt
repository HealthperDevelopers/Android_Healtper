package com.umc.healthper.ui.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.databinding.ItemWorkdetailWorknameBinding

class WorkdetailListRVAdapter( ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return 1
    }

    inner class NameHolder(val binding: ItemWorkdetailWorknameBinding): RecyclerView.ViewHolder(binding.root) {

    }
}