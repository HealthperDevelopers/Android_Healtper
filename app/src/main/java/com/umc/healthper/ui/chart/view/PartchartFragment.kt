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
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.databinding.FragmentPartchartBinding
import com.umc.healthper.databinding.FragmentPartchartTmpBinding
import com.umc.healthper.ui.board.adapter.BoardVPAdapter
import com.umc.healthper.ui.chart.adapter.PartchartVPAdapter
import com.umc.healthper.util.VarUtil

class PartchartFragment : Fragment() {
    lateinit var binding : FragmentPartchartBinding
    var partName : String = ""
    private val itemList = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("data : ", arguments?.getString("part").toString())

        binding = FragmentPartchartBinding.inflate(inflater, container, false)
        binding.partchartUserNameTv.text = VarUtil.glob.Nickname

        partName = arguments?.getString("part").toString()
        binding.partchartPartTv.text = partName

        setLineChartData()

        Log.d("Week", "create")

        val spinner = binding.partchartSp

        var db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
        var partId = db.WorkPartDao().getWorkPartIdbyPartName(partName)
        Log.d("partId", partId.toString())
        val tmpFav = db.WorkFavDao().getAllFavWorkByPartId(partId)
        val tmpAll = db.WorkDao().findWorkbyPartId(partId)
        itemList.clear()
        for (i in tmpFav) {
            for (j in tmpAll) {
                if (i.workId == j.id) {
                    itemList.add(db.WorkDao().findWorkbyId(j.id).workName)
                }
            }
        }

        val adapter = ArrayAdapter(VarUtil.glob.mainContext, R.layout.simple_spinner_item, itemList)
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
                var authService = AuthService()
                authService.statistic(itemList[position])
            }
        }
        return binding.root
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
}