package com.umc.healthper.ui.chart.view

import android.R
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.umc.healthper.data.entity.ChartData
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.data.remote.AuthRetrofitInterface
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.databinding.FragmentPartchartBinding
import com.umc.healthper.util.VarUtil
import com.umc.healthper.util.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PartchartFragment : Fragment() {
    lateinit var binding : FragmentPartchartBinding
    var partName : String = ""
    val worknameList = arrayListOf<String>()
    var chartDataXY : ChartData? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("data : ", arguments?.getString("part").toString())

        binding = FragmentPartchartBinding.inflate(inflater, container, false)
        binding.partchartUserNameTv.text = VarUtil.glob.Nickname
        binding.partchartUserNameTv.setOnClickListener {
            VarUtil.glob.mainActivity.Mypage()
        }

        // 파트 이름 가져와서 파트 Text에 초기화
        partName = arguments?.getString("part").toString()
        binding.partchartPartTv.text = partName

        setLineChartData()
        getSpinnerWorkNameData()
        setSpinner(binding.partchartSp)

        return binding.root
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

    fun setLineChartData()
    {
        val Yvalue = ArrayList<Int>()
        Yvalue.add(1)
        Yvalue.add(4)
        Yvalue.add(3)
        Yvalue.add(9)
        Yvalue.add(5)
        Yvalue.add(1)
        Yvalue.add(4)
        Yvalue.add(3)
        Yvalue.add(9)
        Yvalue.add(5)

        val lineentry = ArrayList<Entry>()

        for (i in 0 until Yvalue.size)
            lineentry.add(Entry(i.toFloat(), Yvalue[i].toFloat()))

        val lineDataSet = LineDataSet(lineentry, "first")
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
        xAxis.textColor = Color.WHITE // 없애거나, 투명화하는 방법은 없을지
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
                    chartDataXY = response.body()
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
}