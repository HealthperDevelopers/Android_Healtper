package com.umc.healthper.ui.board.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.healthper.R
import com.umc.healthper.databinding.FragmentBoardBinding
import com.umc.healthper.ui.MainActivity
import com.umc.healthper.ui.board.adapter.BoardVPAdapter
import com.umc.healthper.util.VarUtil

class BoardFragment : Fragment() {
    lateinit var binding: FragmentBoardBinding
    var mainActivity: MainActivity? = null
    var tabPosition : String = "NORMAL"

    private val information = arrayListOf("자유 게시판", "질문 게시판")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onResume() {
        super.onResume()
        BoardSpinner()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardBinding.inflate(inflater, container, false)
        BoardSpinner()

        val boardVPAdapter = BoardVPAdapter(this)
        binding.boardFreePostVp.adapter = boardVPAdapter
        TabLayoutMediator(binding.boardTb, binding.boardFreePostVp)
        { tab, position ->
            tab.text = information[position]
        }.attach()

        binding.boardMyboardIv.setOnClickListener {
            mainActivity!!.changeBoardFragment(0)
        }

        binding.boardWritePostIv.setOnClickListener {
            mainActivity!!.changeBoardFragment(1)
        }
        return binding.root
    }

    fun BoardSpinner() {
        val spinner = binding.boardSortedSpinner
        spinner.adapter = ArrayAdapter.createFromResource(
            VarUtil.glob.mainContext,
            R.array.itemBoardList,
            android.R.layout.simple_spinner_item
        )
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                VarUtil.glob.boardFreepostFragment.getPosts("NORMAL", "LATEST", 0)
                VarUtil.glob.boardFreepostFragment.bundle.putString(
                    "sortType",
                    "LATEST"
                )
                VarUtil.glob.boardFreepostFragment.page = 1

                VarUtil.glob.boardQuestionpostFragment.getPosts(
                    "QUESTION",
                    "LATEST",
                    0
                )
                VarUtil.glob.boardQuestionpostFragment.bundle.putString(
                    "sortType",
                    "LATEST"
                )
                VarUtil.glob.boardQuestionpostFragment.page = 1
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (tabPosition == "NORMAL") {
                    when (position) {
                        //최신순
                        0 -> {
                            VarUtil.glob.boardFreepostFragment.getPosts("NORMAL", "LATEST", 0)
                            VarUtil.glob.boardFreepostFragment.bundle.putString(
                                "sortType",
                                "LATEST"
                            )
                            VarUtil.glob.boardFreepostFragment.page = 1
                        }
                        //추천순
                        1 -> {
                            VarUtil.glob.boardFreepostFragment.getPosts("NORMAL", "LIKE", 0)
                            VarUtil.glob.boardFreepostFragment.bundle.putString(
                                "sortType",
                                "LIKE"
                            )
                            VarUtil.glob.boardFreepostFragment.page = 1
                        }
                        //댓글순
                        2 -> {
                            VarUtil.glob.boardFreepostFragment.getPosts("NORMAL", "COMMENT", 0)
                            VarUtil.glob.boardFreepostFragment.bundle.putString(
                                "sortType",
                                "COMMENT"
                            )
                            VarUtil.glob.boardFreepostFragment.page = 1
                        }
                    }
                }
                if (tabPosition == "QUESTION") {
                    when (position) {
                        //최신순
                        0 -> {
                            VarUtil.glob.boardQuestionpostFragment.getPosts(
                                "QUESTION",
                                "LATEST",
                                0
                            )
                            VarUtil.glob.boardQuestionpostFragment.bundle.putString(
                                "sortType",
                                "LATEST"
                            )
                            VarUtil.glob.boardQuestionpostFragment.page = 1
                        }
                        //추천순
                        1 -> {
                            VarUtil.glob.boardQuestionpostFragment.getPosts(
                                "QUESTION",
                                "LIKE",
                                0
                            )
                            VarUtil.glob.boardQuestionpostFragment.bundle.putString(
                                "sortType",
                                "LIKE"
                            )
                            VarUtil.glob.boardQuestionpostFragment.page = 1
                        }
                        //댓글순
                        2 -> {
                            VarUtil.glob.boardQuestionpostFragment.getPosts(
                                "QUESTION",
                                "COMMENT",
                                0
                            )
                            VarUtil.glob.boardQuestionpostFragment.bundle.putString(
                                "sortType",
                                "COMMENT"
                            )
                            VarUtil.glob.boardQuestionpostFragment.page = 1
                        }
                    }
                }
            }
        }
    }
}


