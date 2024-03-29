package com.umc.healthper.ui.chart.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.umc.healthper.R
import com.umc.healthper.data.entity.ChartData
import com.umc.healthper.data.entity.InChart
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.data.remote.AuthRetrofitInterface
import com.umc.healthper.databinding.FragmentPartchartBinding
import com.umc.healthper.util.VarUtil
import com.umc.healthper.util.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Collections

class PartchartFragment : Fragment() {
    lateinit var binding : FragmentPartchartBinding
    var partName : String = ""
    val worknameList = arrayListOf<String>()
    var totalChartDataXY : List<InChart>? = null
    var last5ChartDataXY : List<InChart>? = null
    var last10ChartDataXY : List<InChart>? = null

    /**
     * 기능 구현
     * 1. 차트 위에 5, 10, 전체 클릭 시 LAST_N 변경
     * 2. setLineChartData에 ChartDataXY를 x, y축으로 파싱해서 arraylist로 만들어주는 함수 만들어 넣어주기.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("data : ", arguments?.getString("part").toString())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_partchart, container, false)
        binding.partchartUserNameTv.text = VarUtil.glob.Nickname
        binding.partchartBackBt.setOnClickListener {
            VarUtil.glob.mainActivity.supportFragmentManager.popBackStack(
                "part_chart",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }

        // 파트 이름 가져와서 파트 Text에 초기화
        var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
        partName = arguments?.getString("part").toString()
        binding.partchartPartTv.text = partName
        binding.partchartPartTv.backgroundTintList = ColorStateList.valueOf(
            Color.parseColor(db.WorkPartDao().getColorbyPartName(partName)))

        clickChartBar()
        getSpinnerWorkNameData()
        setSpinner(binding.partchartSp)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this).get(PartChartViewModel::class.java)
        binding.viewModel = viewModel
    }

    private fun clickChartBar() {
        binding.partchartContentFiveTv.setOnClickListener {
            setLineChartData(5)
        }
        binding.partchartContentTenTv.setOnClickListener {
            setLineChartData(10)
        }
        binding.partchartContentAllTv.setOnClickListener {
            setLineChartData(-1)
        }
    }

    fun setSpinner(spinner: Spinner){
        val adapter = ArrayAdapter(VarUtil.glob.mainContext, R.layout.simple_spinner_item, worknameList)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                statistic(worknameList[position])
            }
        }
    }

    fun setLineChartData(LAST_N: Int)
    {
        var Yvalue = ArrayList<Int>()
        var Xvalue = ArrayList<String>()
        var chartDataXY : List<InChart>? = null

        when (LAST_N) {
            5 -> {
                chartDataXY = last5ChartDataXY
            }
            10 -> {
                chartDataXY = last10ChartDataXY
            }
            -1 -> {
                chartDataXY = totalChartDataXY
            }
        }

        for (inchart in chartDataXY!!){
            Yvalue.add(inchart.volume)
            Xvalue.add(inchart.date)
        }

        val lineEntry = ArrayList<Entry>()

        for (i in 0 until Yvalue.size)
            lineEntry.add(Entry(i.toFloat(), Yvalue[i].toFloat()))

        val lineDataSet = LineDataSet(lineEntry, "first")
        val data = LineData(lineDataSet)

        binding.partchartCt.data = data
        binding.partchartCt.setBackgroundColor(0)

        // animation
        binding.partchartCt.animateY(1000)
        binding.partchartCt.animateX(1000)

        // 축 visible 설정
        val xAxis: XAxis = binding.partchartCt.getXAxis()
        //change the position of x-axis to the bottom
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        //set the horizontal distance of the grid line
        xAxis.granularity = 1f
        xAxis.valueFormatter = IndexAxisValueFormatter(Xvalue)
//        xAxis.textColor = Color.WHITE // 없애거나, 투명화하는 방법은 없을지
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false)
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false)

        //좌측 값 hiding the left y-axis line, default true if not set
        val leftAxis: YAxis = binding.partchartCt.getAxisLeft()
        leftAxis.setDrawAxisLine(false)
    //        leftAxis.textColor = Color.RED
        leftAxis.setDrawAxisLine(false)
        leftAxis.setDrawGridLines(false)



        //우측 값 hiding the right y-axis line, default true if not set
        val rightAxis: YAxis = binding.partchartCt.getAxisRight()
        rightAxis.setDrawAxisLine(false)
    //        rightAxis.textColor = Color.RED
        rightAxis.setDrawAxisLine(false)
        rightAxis.setDrawGridLines(false)
    }

    private fun setLastNChartDataXY(totalData : List<InChart>){
        totalChartDataXY = totalData

        if (totalChartDataXY!!.size > 5) {
            last5ChartDataXY = totalChartDataXY!!.subList(0, 5)
        } else {
            last5ChartDataXY = totalChartDataXY
        }

        if (totalChartDataXY!!.size > 10) {
            last10ChartDataXY = totalChartDataXY!!.subList(0, 10)
        } else {
            last10ChartDataXY = totalChartDataXY
        }

        setLineChartData(5)
    }

    private fun getSpinnerWorkNameData(){
        var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
        var partId = db.WorkPartDao().getWorkPartIdbyPartName(partName)
        Log.d("partId", partId.toString())
        val tmpAll = db.WorkDao().findWorkbyPartId(partId)
        worknameList.clear()
        Log.d("WorkALL", tmpAll.toString())
        for (i in tmpAll) {
            worknameList.add(i.workName)
        }
    }

    fun statistic(partName : String) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.statistic(partName).enqueue(object : Callback<ChartData> {
            override fun onResponse(call: Call<ChartData>, response: Response<ChartData>
            ) {
                if (response.code() == 200) {
                    Log.d("statistic/success", response.body()!!.toString())
                    setLastNChartDataXY(response.body()!!.chart)
                    setTotalDatas(response.body()!!.chart, response.body()!!.totalVolume, response.body()!!.totalTime)
                    setLowHighDatas(response.body()!!.chart)
                }
                else {
                    Log.d("statistic/failure", "fail")
                }
            }

            override fun onFailure(call: Call<ChartData>, t: Throwable) {
                Log.d("statistic/FAILURE", t.message.toString())
            }
        })
    }

    fun setTotalDatas(totalData : List<InChart>, totalVolume : Int, totalTime : Int){
        val timeBox = binding.partchartWorktimeBoxTv
        val countBox = binding.partchartWorkcountBoxTv
        val volumeBox = binding.partchartWorkvolumeBoxTv

        timeBox.text = totalTime.toString()
        countBox.text = totalData.size.toString()
        volumeBox.text = totalVolume.toString()
    }

    private fun setLowHighDatas(totalData : List<InChart>) {
        var volumes : ArrayList<Int> = ArrayList()
        for (i in totalData.indices) {
            volumes.add(totalData[i].volume)
        }

        var high : Int = 0
        var low : Int = 0

        try {
            high = Collections.max(volumes)
            low = Collections.min(volumes)
        } catch (exception : NoSuchElementException) {

        }

        binding.partchartHighWeightTv.text = high.toString()
        binding.partchartLowWeightTv.text = low.toString()
    }
}