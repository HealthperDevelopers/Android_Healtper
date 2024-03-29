package com.umc.healthper.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.data.remote.AuthService
import com.umc.healthper.data.remote.CalendarResponse
import com.umc.healthper.data.remote.GetDayDetailFirst
import com.umc.healthper.databinding.FragmentMainBinding
import com.umc.healthper.ui.main.adapter.MainRVAdapter
import com.umc.healthper.ui.main.adapter.WorkReadyListAdapter
import com.umc.healthper.util.GlobVar
import com.umc.healthper.util.VarUtil
import java.util.*
import kotlin.collections.ArrayList

class MainFragment: Fragment(), DetailFirstView, CalendarDataView {

    lateinit var binding: FragmentMainBinding
    lateinit var adapter: MainRVAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        val now = Calendar.getInstance()
        VarUtil.glob.selectedDate = now.get(Calendar.DATE).toString()
        val authService = AuthService()
        authService.calendarData = this
        authService.calenderInfo(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1)

        authService.dayInfoData = this
        val y = now.get(Calendar.YEAR)
        val m = now.get(Calendar.MONTH) + 1
        val d = now.get(Calendar.DATE)
        val selectedDay = String.format("%04d-%02d-%02d", y, m, d)
        authService.dayInfo(selectedDay)

        VarUtil.glob.mainFragment = this
        adapter = MainRVAdapter(now)
        binding.mainRv.adapter = adapter
        return binding.root
    }

    fun setAuth(auth: AuthService) {
        auth.calendarData = this
    }
    override fun onDetailFirstGetSuccess(data: ArrayList<GetDayDetailFirst>) {
        Log.d("detailData", data.toString())
        VarUtil.glob.detailFirstList = data

        val siz = data.size
        val originSiz = VarUtil.glob.mainCompList.size - 3


        adapter.notifyItemRangeRemoved(2, originSiz)

        if (siz != originSiz) {
            if (siz > originSiz) {
                for (i in originSiz until siz) {
                    VarUtil.glob.mainCompList.add(2, 3)
                }


            }
            else {
                for (i in siz until originSiz) {
                    VarUtil.glob.mainCompList.removeAt(2)
                }
            }
        }

        adapter.notifyItemRangeInserted(2, siz)
    }

    override fun onDetailFirstGetFailure() {
        TODO("Not yet implemented")
    }

    override fun calendarDataGetSuccess(data: ArrayList<CalendarResponse>) {
        VarUtil.glob.calData = data
        Log.d("calData", data.toString())

    }
}