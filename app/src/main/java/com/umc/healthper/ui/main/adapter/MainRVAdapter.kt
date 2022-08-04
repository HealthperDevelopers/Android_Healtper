package com.umc.healthper.ui.main.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.umc.healthper.databinding.ItemMainCalendarBinding
import com.umc.healthper.databinding.ItemMainDetailBinding
import com.umc.healthper.databinding.ItemMainNewBinding
import com.umc.healthper.databinding.ItemMainUserBinding
import com.umc.healthper.ui.MainActivity
import com.umc.healthper.util.VarUtil
import java.util.*
import kotlin.math.abs


class MainRVAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data_list = listOf(1, 2, 3, 4)
    private lateinit var data: List<Int>
    private lateinit var weekList: ArrayList<Int>
    private lateinit var now: Calendar
    private lateinit var calRvAdapList: ArrayList<DateRVAdapter>
    private lateinit var calRvLayoutList: ArrayList<FlexboxLayoutManager>

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

    inner class UserHolder(val binding: ItemMainUserBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.itemMainUserSettingIv.setOnClickListener {
                VarUtil.glob.mainActivity.openNav()
            }
        }
    }

    inner class CalendarHolder(private val binding: ItemMainCalendarBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            now = Calendar.getInstance()
            setListener()
            data = setCalData()
            weekList = setCalWeekData()

            setCal(data, weekList)
        }

        private fun setListener() {

            binding.itemMainCalLeftIv.setOnClickListener{
                update(-2)
            }

            binding.itemMainCalRightIv.setOnClickListener {
                update(0)
            }
        }

        fun update(add: Int) {
            now.set(data[0], data[1] + add, data[2])
            data = setCalData()
            weekList = setCalWeekData()

            binding.itemMainCalMonyearTv.text = data[0].toString() + "년 " + data[1].toString()+ "월"
            VarUtil.glob.today.background = null
            for (i in 0..5) {
                calRvAdapList[i].data = data
                calRvAdapList[i].weekData = weekList
                calRvAdapList[i].notifyItemRangeChanged(0, 7)
            }
        }



        private fun setCalData():List<Int> {
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

            return data
        }

        fun setCalWeekData(): ArrayList<Int> {
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

            return weekList
        }

        fun setCal(data:List<Int>, weekList: ArrayList<Int>) {
            calRvAdapList = ArrayList<DateRVAdapter>()
            calRvLayoutList = ArrayList<FlexboxLayoutManager>()
            if (calRvAdapList.size == 0) {
                for (i in 1..6) {
                    val tmp = DateRVAdapter(data, i, weekList)
                    calRvAdapList.add(tmp)
                    val tmp2 = FlexboxLayoutManager(VarUtil.glob.mainContext)
                    tmp2.flexDirection = FlexDirection.ROW
                    tmp2.justifyContent = JustifyContent.SPACE_AROUND
                    calRvLayoutList.add(tmp2)
                }
            }

            binding.itemMainCalMonyearTv.text = data[0].toString() + "년 " + data[1].toString()+ "월"

            binding.itemMainCalW1Rv.layoutManager = calRvLayoutList[0]
            binding.itemMainCalW1Rv.adapter = calRvAdapList[0]
            binding.itemMainCalW2Rv.layoutManager = calRvLayoutList[1]
            binding.itemMainCalW2Rv.adapter = calRvAdapList[1]
            binding.itemMainCalW3Rv.layoutManager = calRvLayoutList[2]
            binding.itemMainCalW3Rv.adapter = calRvAdapList[2]
            binding.itemMainCalW4Rv.layoutManager = calRvLayoutList[3]
            binding.itemMainCalW4Rv.adapter = calRvAdapList[3]
            binding.itemMainCalW5Rv.layoutManager = calRvLayoutList[4]
            binding.itemMainCalW5Rv.adapter = calRvAdapList[4]
            binding.itemMainCalW6Rv.layoutManager = calRvLayoutList[5]
            binding.itemMainCalW6Rv.adapter = calRvAdapList[5]}
    }

    inner class DetailHolder(binding: ItemMainDetailBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
        }
    }

    inner class NewHolder(val binding: ItemMainNewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.itemMainNewGoWorkTv.setOnClickListener {
                VarUtil.glob.mainActivity.changeMainFragment()
            }

        }
    }


}