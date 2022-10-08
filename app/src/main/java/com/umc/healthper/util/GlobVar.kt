package com.umc.healthper.util

import android.app.Application
import android.content.Context
import android.graphics.Point
import android.widget.TextView
import com.umc.healthper.data.entity.ExerciseInfo
import com.umc.healthper.data.entity.TotalData
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.remote.CalendarResponse
import com.umc.healthper.data.remote.GetDayDetailFirst
import com.umc.healthper.data.remote.GetDayDetailSecond
import com.umc.healthper.data.remote.SetDayDetailSecond
import com.umc.healthper.ui.MainActivity
import com.umc.healthper.ui.board.view.BoardFreepostFragment
import com.umc.healthper.ui.board.view.BoardQuestionpostFragment
import com.umc.healthper.ui.main.adapter.WorkReadyListAdapter
import com.umc.healthper.ui.main.view.MainFragment
import com.umc.healthper.ui.mypage.adapter.ShowFavWorkRVAdapter
import java.util.Calendar

class GlobVar: Application() {
    lateinit var mainContext: Context
    lateinit var mainActivity: MainActivity
    lateinit var mainFragment: MainFragment
    lateinit var boardFreepostFragment: BoardFreepostFragment
    lateinit var boardQuestionpostFragment : BoardQuestionpostFragment
    lateinit var today: TextView
    lateinit var selectedDay: TextView
    lateinit var size: Point
    var restMinutes : Int = 60
    var Nickname = ""
    var tutorial = false

    var selectedPart = ArrayList<String>()
    var unselectedPart = ArrayList<String>()
    var work : ArrayList<SetDayDetailSecond> = arrayListOf()
    var totalData : TotalData = TotalData("", ArrayList(), ExerciseInfo(0, 0))
    var setMain : Boolean = false
    var isWorkTime: Boolean = true // false -> partTime

    lateinit var workReadyAdapter: WorkReadyListAdapter
    var currentPart: String = ""
    var currentWork: String = ""

    //한달의 달력데이터
    var calData = ArrayList<CalendarResponse>()


    //사용자 즐겨찾기 페이지 데이터
    var favWorkList = ArrayList<Work>()
    lateinit var favPageWorkListAdapter: ShowFavWorkRVAdapter


    //달력각날짜의상세데이터
    var detailFirstList = ArrayList<GetDayDetailFirst>()
    var mainCompList = mutableListOf(1, 2, 4)
    var selectedDate = "1"

    //현재 열람중인 운동정보
    var recordId: Int = 0
    var recordList = ArrayList<GetDayDetailSecond>()
    var recordPartList = ArrayList<String>()
    var partPos: Int = 0
    var comm: String? = ""
    var totalTime: Int = 0
    var totalVol: Int = 0
}