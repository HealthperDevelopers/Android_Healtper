package com.umc.healthper.ui.main.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.data.remote.GetDayDetailFirst
import com.umc.healthper.data.remote.GetDayDetailSecond
import com.umc.healthper.databinding.FragmentDetailWorkRecordSecondBinding
import com.umc.healthper.ui.main.adapter.DetailWorkRecordRvAdapter
import com.umc.healthper.ui.main.adapter.DetailWorkSetListRvAdapter
import com.umc.healthper.util.GlobVar
import com.umc.healthper.util.VarUtil

class DetailWorkRecordSecondFragment: Fragment() {
    lateinit var binding: FragmentDetailWorkRecordSecondBinding
    lateinit var spinnerAdapter: ArrayAdapter<String>
    lateinit var rvAdapter: DetailWorkSetListRvAdapter
    var spinnerArray = ArrayList<GetDayDetailSecond>()
    var spinnerTitleArray = ArrayList<String>()
    var arrayPos = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailWorkRecordSecondBinding.inflate(inflater, container, false)

        spinnerAdapter = ArrayAdapter(VarUtil.glob.mainContext, android.R.layout.simple_spinner_dropdown_item, spinnerTitleArray)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.detailWorkRecordSecondWorkSp.adapter = spinnerAdapter
        rvAdapter = DetailWorkSetListRvAdapter(spinnerArray, arrayPos)
        binding.detailWorkRecordSecondWorkDetailRv.adapter = rvAdapter

        setArraybyPart()
        setListener()
        return binding.root
    }

    fun setListener() {
        binding.detailWorkRecordSecondBackTv.setOnClickListener {
            VarUtil.glob.mainActivity.supportFragmentManager.popBackStack(
                "detailWorkRecordSecond",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        binding.detailWorkRecordSecondLeftIv.setOnClickListener {
            partChange(-1)
        }
        binding.detailWorkRecordSecondRightIv.setOnClickListener {
            partChange(1)
        }

        binding.detailWorkRecordSecondWorkSp.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val data = spinnerArray[p2]
                arrayPos = p2
                val tmph = data.exerciseTime / 3600
                val tmpm = (data.exerciseTime - tmph * 3600)/ 60
                val tmps = (data.exerciseTime - tmph * 3600)% 60
                val h = if (tmph < 10) {
                    "0$tmph"
                }
                else {
                    "$tmph"
                }
                val m = if (tmpm < 10) {
                    "0$tmpm"
                }
                else {
                    "$tmpm"
                }
                val s = if (tmps < 10) {
                    "0$tmps"
                }
                else {
                    "$tmps"
                }
                binding.detailWorkRecordSecondTimeTv.text = "$h:$m:$s"
                var totalVol = 0
                for (i in data.details!!) {
                    totalVol += i.weight * i.repeatTime
                }
                binding.detailWorkRecordSecondVolTv.text = totalVol.toString() + "kg"

                rvAdapter.arrPos = arrayPos
                rvAdapter.notifyDataSetChanged()


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }

    fun partChange(order: Int) {
        when(order) {
            1 -> {
                if (VarUtil.glob.recordPartList.size > VarUtil.glob.partPos + 1) {
                    VarUtil.glob.partPos += 1
                    setArraybyPart()
                }
            }
            else -> {
                if (0 <= VarUtil.glob.partPos - 1) {
                    VarUtil.glob.partPos -= 1
                    setArraybyPart()
                }
            }
        }
    }

    fun setArraybyPart() {
        spinnerArray.clear()
        spinnerTitleArray.clear()
        for (i in VarUtil.glob.recordList) {
            if (i.section == VarUtil.glob.recordPartList[VarUtil.glob.partPos]) {
                spinnerArray.add(i)
                spinnerTitleArray.add(i.exerciseName!!)
            }
        }
        spinnerAdapter.notifyDataSetChanged()

        arrayPos = 0
        rvAdapter.data = spinnerArray
        rvAdapter.arrPos = arrayPos
        rvAdapter.notifyDataSetChanged()

        val db = LocalDB.getInstance(VarUtil.glob.mainContext)!!
        val partStr = VarUtil.glob.recordPartList[VarUtil.glob.partPos]
        binding.detailWorkRecordSecondPartTv.text = partStr
        binding.detailWorkRecordSecondPartBackgroundTv.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(db.WorkPartDao().getColorbyPartName(partStr)))



        binding.detailWorkRecordSecondCommTv.text = VarUtil.glob.comm
    }

}