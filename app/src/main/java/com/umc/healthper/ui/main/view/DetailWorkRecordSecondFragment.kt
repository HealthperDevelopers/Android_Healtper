package com.umc.healthper.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.data.remote.GetDayDetailFirst
import com.umc.healthper.data.remote.GetDayDetailSecond
import com.umc.healthper.databinding.FragmentDetailWorkRecordSecondBinding
import com.umc.healthper.util.GlobVar
import com.umc.healthper.util.VarUtil

class DetailWorkRecordSecondFragment: Fragment() {
    lateinit var binding: FragmentDetailWorkRecordSecondBinding
    lateinit var spinnerAdapter: ArrayAdapter<String>
    var spinnerArray = ArrayList<GetDayDetailSecond>()
    var spinnerTitleArray = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailWorkRecordSecondBinding.inflate(inflater, container, false)

        spinnerAdapter = ArrayAdapter(VarUtil.glob.mainContext, android.R.layout.simple_spinner_dropdown_item, spinnerTitleArray)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.detailWorkRecordSecondWorkSp.adapter = spinnerAdapter
        setArraybyPart()

        setListener()
        return binding.root
    }

    fun setListener() {
        binding.detailWorkRecordSecondLeftIv.setOnClickListener {
            partChange(-1)
        }
        binding.detailWorkRecordSecondRightIv.setOnClickListener {
            partChange(1)
        }

        binding.detailWorkRecordSecondWorkSp.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val data = spinnerArray[p2]
                binding.detailWorkRecordSecondTimeTv.text = data.exerciseTime.toString()
                var totalVol = 0
                for (i in data.details!!) {
                    totalVol += i.weight * i.repeatTime
                }
                binding.detailWorkRecordSecondVolTv.text = totalVol.toString()


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

        val partStr = VarUtil.glob.recordPartList[VarUtil.glob.partPos]
        binding.detailWorkRecordSecondPartTv.text = partStr
    }

}