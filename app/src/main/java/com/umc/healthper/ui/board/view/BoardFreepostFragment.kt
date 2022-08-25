package com.umc.healthper.ui.board.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.data.remote.*
import com.umc.healthper.databinding.FragmentBoardFreepostBinding
import com.umc.healthper.ui.board.adapter.BoardFreepostRVAdapter
import com.umc.healthper.util.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BoardFreepostFragment : Fragment() {
    lateinit var binding : FragmentBoardFreepostBinding
    var post : PostsResponse = PostsResponse(listOf(Contents(0, "", WriterInfo(0, "", ""), "", 0, 0, "")))

    override fun onResume() {
        super.onResume()
        val adapter = BoardFreepostRVAdapter(post.content)
        binding.boardFreepostRv.adapter = adapter

        adapter.setListener(object: BoardFreepostRVAdapter.onClickListener {
            override fun onClick(pos: Int) {
                Log.d("pos", pos.toString())
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardFreepostBinding.inflate(inflater, container, false)
        getPosts("LATEST", 0)

//        val adapter = BoardFreepostRVAdapter(post.content)
//        binding.boardFreepostRv.adapter = adapter
//
//        adapter.setListener(object: BoardFreepostRVAdapter.onClickListener {
//            override fun onClick(pos: Int) {
//                Log.d("pos", pos.toString())
//            }
//        })

        return binding.root
    }

    /** 게시글 목록 조회
     * @param sortType = {LATEST(최신순), LIKE(추천순), COMMENT(댓글순)}
     * @return postsResponse = 게시글 목록 30개의 정보
     * */
    fun getPosts(sortType : String, page :Int) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.getPosts(sortType, page).enqueue(object : Callback<PostsResponse> {
            override fun onResponse(
                call: Call<PostsResponse>,
                response: Response<PostsResponse>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d("posts/SUCCESS", response.toString())
                        Log.d("posts/resp", response.body().toString())
                        post = response.body()!!
                    }
                    else -> {
                        Log.d("posts/FAILURE", response.toString())
                    }
                }
            }

            override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
                Log.d("posts/fail in onFailure", t.toString())
            }
        })
    }
}