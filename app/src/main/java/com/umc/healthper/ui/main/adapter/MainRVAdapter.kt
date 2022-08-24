package com.umc.healthper.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.data.remote.GetDayDetailFirst
import com.umc.healthper.data.remote.GetDayDetailSecond
import com.umc.healthper.databinding.ItemMainCalendarBinding
import com.umc.healthper.databinding.ItemMainDetailBinding
import com.umc.healthper.databinding.ItemMainNewBinding
import com.umc.healthper.databinding.ItemMainUserBinding
import com.umc.healthper.ui.main.view.CalendarDataView
import com.umc.healthper.ui.main.view.DetailFirstView
import com.umc.healthper.ui.main.view.DetailSecondView
import com.umc.healthper.util.VarUtil
import java.util.*
import kotlin.collections.ArrayList


class MainRVAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun getItemViewType(position: Int): Int {
        return VarUtil.glob.mainCompList[position]
    }
    override fun getItemCount(): Int {
        return VarUtil.glob.mainCompList.size
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
        when(VarUtil.glob.mainCompList[position]) {
            1-> {
                (holder as UserHolder).bind()
            }
            2-> {
                (holder as CalendarHolder).bind()
            }
            3-> {
                (holder as DetailHolder).bind()
            }
            4-> {
                (holder as NewHolder).bind()
            }
        }
    }

    class UserHolder(val binding: ItemMainUserBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.itemMainUserSettingIv.setOnClickListener {
                VarUtil.glob.mainActivity.openNav()
            }
        }
    }

    class CalendarHolder(private val binding: ItemMainCalendarBinding): RecyclerView.ViewHolder(binding.root) {
        private lateinit var data: List<Int>
        private lateinit var weekList: ArrayList<Int>
        private lateinit var now: Calendar
        private lateinit var calRvAdapList: ArrayList<DateRVAdapter>
        private lateinit var calRvLayoutList: ArrayList<FlexboxLayoutManager>
        private var authService = AuthService()

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

            binding.itemMainCalYearTv.text = data[0].toString() + "년"
            binding.itemMainCalMonthTv.text = data[1].toString()+ "월"
            VarUtil.glob.mainFragment.setAuth(authService)
            authService.calenderInfo(data[0], data[1])
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
            val dow = now.get(Calendar.WEEK_OF_MONTH)
            var line:Int = ((maxDay - day - (7 - doy))/ 7 )+ dow
            if ((maxDay - day - (7 - doy))% 7 != 0) {
                line += 1
            }
            now.set(year, month - 1, 1)
            val firstDate = now.get(Calendar.DAY_OF_WEEK)
            val firstWeek = 7 - firstDate + 2
            var weekList = ArrayList<Int>()
            weekList.add(1)
            for (i in 0..line - 2) {
                weekList.add(firstWeek + (7 *i))
            }
            Log.d("date", "$year $month $day $doy $maxDay $dow $line $firstDate $weekList")
            //년 월 일 요일 마지막날짜 오늘이몇주차인지 한달이총몇주인지
            val data = listOf<Int>(year, month, day, doy, maxDay, dow, line, firstDate)

            return data
        }

        fun setCalWeekData(): ArrayList<Int> {
            val year = now.get(Calendar.YEAR)
            val month = now.get(Calendar.MONTH) + 1
            val day = now.get(Calendar.DATE)
            val doy = now.get(Calendar.DAY_OF_WEEK)
            val maxDay = now.getActualMaximum(Calendar.DATE)
            val dow = now.get(Calendar.WEEK_OF_MONTH)
            var line:Int = ((maxDay - day - (7 - doy))/ 7 )+ dow
            if ((maxDay - day - (7 - doy))% 7 != 0) {
                line += 1
            }
            now.set(year, month - 1, 1)
            val firstDate = now.get(Calendar.DAY_OF_WEEK)
            val firstWeek = 7 - firstDate + 2
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
                    tmp.customListener(object: DateRVAdapter.UserListener {
                        override fun onClick(date: String) {
                            val y = now.get(Calendar.YEAR).toString()
                            val m = (now.get(Calendar.MONTH)+1)
                            val newM = if (m < 10) {
                                "0$m"
                            }
                            else {
                                m.toString()
                            }
                            val selectedDay = "$y-$newM-$date"
                            authService.dayInfoData = VarUtil.glob.mainFragment
                            authService.dayInfo(selectedDay)
                        }

                    })
                    calRvAdapList.add(tmp)
                    val tmp2 = FlexboxLayoutManager(VarUtil.glob.mainContext)
                    tmp2.flexDirection = FlexDirection.ROW
                    tmp2.justifyContent = JustifyContent.SPACE_AROUND
                    calRvLayoutList.add(tmp2)
                }
            }

            binding.itemMainCalYearTv.text = data[0].toString() + "년"
            binding.itemMainCalMonthTv.text = data[1].toString()+ "월"

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

    class DetailHolder(val binding: ItemMainDetailBinding): RecyclerView.ViewHolder(binding.root),
        DetailSecondView {
        fun bind() {
            binding.root.setOnClickListener {
                VarUtil.glob.recordId = VarUtil.glob.detailFirstList[adapterPosition - 2].record_id

                val conn = AuthService()
                conn.dayDetailData = this
                conn.dayDetail(VarUtil.glob.recordId)

            }
            if (VarUtil.glob.detailFirstList.isNullOrEmpty()) {

            }
            else {
                binding.itemMainDetailTotalTimeTv.text =
                    VarUtil.glob.detailFirstList[adapterPosition - 2].exerciseInfo!!.totalExerciseTime.toString()
                binding.itemMainDetailTotalWeightTv.text =
                    VarUtil.glob.detailFirstList[adapterPosition - 2].exerciseInfo!!.totalVolume.toString()
            }
        }

        override fun daySecondDetailonSuccess(data: ArrayList<GetDayDetailSecond>) {
            VarUtil.glob.recordList = data
            VarUtil.glob.mainActivity.changeMainFragment(3)
        }

    }

    class NewHolder(val binding: ItemMainNewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.itemMainNewGoWorkTv.setOnClickListener {
                VarUtil.glob.mainActivity.changeMainFragment(1)
            }

        }
    }

}