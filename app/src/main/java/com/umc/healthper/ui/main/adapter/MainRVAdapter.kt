package com.umc.healthper.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.databinding.ItemMainCalendarBinding
import com.umc.healthper.databinding.ItemMainDetailBinding
import com.umc.healthper.databinding.ItemMainNewBinding
import com.umc.healthper.databinding.ItemMainUserBinding

class MainRVAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data_list = ArrayList<Int>()

    override fun getItemViewType(position: Int): Int {
        data_list.add(1)
        data_list.add(2)
        data_list.add(3)
        data_list.add(4)
        return data_list[position]
    }
    override fun getItemCount(): Int {
        return 4
    }
    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            1-> {
                val binding =
                    ItemMainUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                UserHolder(binding)
            }
            2 -> {
                    val binding = ItemMainCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    CalendarFrameHolder(binding)
            }
            3 -> {
                val binding = ItemMainDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DetailHolder(binding)
            }
            else -> {
                val binding = ItemMainNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                NewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(data_list[position]) {
            1-> {
                (holder as MainRVAdapter.UserHolder).bind()
            }
            2-> {
                (holder as MainRVAdapter.CalendarFrameHolder).bind()
            }
            3-> {
                (holder as MainRVAdapter.DetailHolder).bind()
            }
            4-> {
                (holder as MainRVAdapter.NewHolder).bind()
            }
        }
    }

    inner class UserHolder(binding: ItemMainUserBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    inner class CalendarFrameHolder(binding: ItemMainCalendarBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    inner class DetailHolder(binding: ItemMainDetailBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
        }
    }

    inner class NewHolder(binding: ItemMainNewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }


}