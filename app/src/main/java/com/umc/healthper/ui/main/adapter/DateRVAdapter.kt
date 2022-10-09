package com.umc.healthper.ui.main.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.databinding.ItemMainCalendarListBinding
import com.umc.healthper.util.VarUtil
import java.util.*
import kotlin.collections.ArrayList

class DateRVAdapter(var data: List<Int>, val count:Int, var weekData: ArrayList<Int>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface UserListener {
        fun onClick(date: String)
    }
    lateinit var userListener: UserListener
    fun customListener(data: UserListener) {
        userListener = data
    }
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
            val now = Calendar.getInstance()
            val year = now.get(Calendar.YEAR)
            val month = now.get(Calendar.MONTH) + 1

            if (count == data[5] && data[3] == pos) {
                if (year == data[0] && month == data[1]) {//당일일경우
                    binding.itemMainCalListTodayTv.visibility = View.VISIBLE
                    VarUtil.glob.today = binding.itemMainCalListTodayTv
                    VarUtil.glob.selectedDay = binding.itemMainCalListTodayTv
                }
            }
            if (count == 1) {
                if (pos < data[7]) {
                binding.itemMainCalListDateTv.text = "  "//그 달 첫 요일보다 작은 숫자들
                }
                else {
                    binding.itemMainCalListDateTv.text = (pos - data[7] + 1).toString()
                    setCol(pos - data[7] + 1)
                }
            }

            else if (count > data[6]) {//없는 주차

            }

            else if (count == data[6]) {//마지막 주
                if (pos > data[4] - weekData[count - 1] + 1) {//마지막 날짜 넘기는 경우
                    binding.itemMainCalListDateTv.text = "   "
                }
                else {
                    binding.itemMainCalListDateTv.text = (pos + weekData[count- 1] - 1).toString()
                    setCol(pos + weekData[count- 1] - 1)
                }
            }

            else {
                binding.itemMainCalListDateTv.text = (pos + weekData[count - 1] - 1).toString()
                setCol(pos + weekData[count - 1] - 1)
            }


            binding.root.setOnClickListener {
                val date = binding.itemMainCalListDateTv.text.toString()
                binding.itemMainCalListSelectTv.visibility = View.VISIBLE
                VarUtil.glob.selectedDate = date
                userListener.onClick(date)
            }

        }

        fun setCol(date: Int) {
            var y = false
            var count = 0
            for (i in VarUtil.glob.calData) {
                if (i.day == date) {
                    y = true
                    break
                }
                count++
            }
            if (y) {
                val data = VarUtil.glob.calData[count].sections
                val db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
                if (data.isNotEmpty()) {
                    binding.itemMainCalListPart1Tv.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor(db.WorkPartDao().getColorbyPartName(data[0])))
                }
                if (data.size >= 2) {
                    binding.itemMainCalListPart2Tv.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor(db.WorkPartDao().getColorbyPartName(data[1])))
                }
                if (data.size >= 3) {
                    binding.itemMainCalListPart3Tv.setBackgroundColor(
                        Color.parseColor(db.WorkPartDao().getColorbyPartName(data[2])))
                }
                if (data.size >= 4) {
                    binding.itemMainCalListPart4Tv.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor(db.WorkPartDao().getColorbyPartName(data[3])))
                }

            }
        }
    }


}