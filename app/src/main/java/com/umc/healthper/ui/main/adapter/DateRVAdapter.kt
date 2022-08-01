package com.umc.healthper.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.databinding.ItemMainCalendarListBinding

class DateRVAdapter(val data: List<Int>, val count:Int, val weekData: ArrayList<Int>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateHolder {
        val binding = ItemMainCalendarListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DateHolder).bind(position + 1)
    }

    override fun getItemCount(): Int {
        return 7
    }

    inner class DateHolder(private val binding: ItemMainCalendarListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            Log.d("pos", pos.toString())
            if (count == 1) {
                if (pos < data[7]) {
                binding.itemMainCalListDateTv.text = " "//그 달 첫 요일보다 작은 숫자들
                }
                else {
                    binding.itemMainCalListDateTv.text = (pos - data[7] + 1).toString()
                }
            }

            else if (count > data[6]) {//없는 주차

            }

            else if (count == data[6]) {//마지막 주
                if (pos > data[4] - weekData[count - 1] + 1) {//마지막 날짜 넘기는 경우
                    binding.itemMainCalListDateTv.text = "  "
                }
                else {
                    binding.itemMainCalListDateTv.text = (pos + weekData[count- 1] - 1).toString()
                }
            }

            else {
                binding.itemMainCalListDateTv.text = (pos + weekData[count - 1] - 1).toString()
            }
        }
    }


}