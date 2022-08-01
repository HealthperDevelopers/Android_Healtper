package com.umc.healthper.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.umc.healthper.databinding.ItemMainCalendarBinding
import com.umc.healthper.databinding.ItemMainDetailBinding
import com.umc.healthper.databinding.ItemMainNewBinding
import com.umc.healthper.databinding.ItemMainUserBinding
import java.util.*
import kotlin.math.abs


class MainRVAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data_list = listOf(1, 2, 3, 4)

    override fun getItemViewType(position: Int): Int {
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
                    CalendarHolder(binding)
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
                (holder as MainRVAdapter.CalendarHolder).bind()
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

    inner class CalendarHolder(private val binding: ItemMainCalendarBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val now = Calendar.getInstance()
            val year = now.get(Calendar.YEAR)
            val month = now.get(Calendar.MONTH) + 1
            val day = now.get(Calendar.DATE)
            val doy = now.get(Calendar.DAY_OF_WEEK)
            val maxDay = now.getActualMaximum(Calendar.DATE)
            val dow = now.get(Calendar.DAY_OF_WEEK_IN_MONTH)
            val line:Int = ((maxDay - day - (7 - doy))/ 7 )+ 1 + dow
            val firstDate = abs(day - (dow - 2)*7 - doy - 7) + 1

            val firstWeek = 7 - firstDate + 2;
            var weekList = ArrayList<Int>()
            weekList.add(1)
            for (i in 0..line - 2) {
                weekList.add(firstWeek + (7 *i))
            }
            Log.d("date", "$year $month $day $doy $maxDay $dow $line $firstDate $weekList")
            val data = listOf<Int>(year, month, day, doy, maxDay, dow, line, firstDate)
            val calRvList = ArrayList<DateRVAdapter>()
            if (calRvList.size == 0) {
                for (i in 1..6) {
                    val tmp = DateRVAdapter(data, i, weekList)
                    calRvList.add(tmp)
                }
            }
            val layoutManager = FlexboxLayoutManager()
            binding.itemMainCalW1Rv.
            binding.itemMainCalW1Rv.adapter = calRvList[0]
            binding.itemMainCalW2Rv.adapter = calRvList[1]
            binding.itemMainCalW3Rv.adapter = calRvList[2]
            binding.itemMainCalW4Rv.adapter = calRvList[3]
            binding.itemMainCalW5Rv.adapter = calRvList[4]
            binding.itemMainCalW6Rv.adapter = calRvList[5]
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