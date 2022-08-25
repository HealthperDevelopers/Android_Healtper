package com.umc.healthper.ui.board.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.data.remote.APostResponse
import com.umc.healthper.data.remote.AuthRetrofitInterface
import com.umc.healthper.databinding.FragmentBoardFreepostContentBinding
import com.umc.healthper.util.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardFreepostContentFragment : Fragment() {
    lateinit var binding : FragmentBoardFreepostContentBinding
    var postId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardFreepostContentBinding.inflate(inflater, container, false)

        viewPost(postId)

        return binding.root
    }

    /** 게시판 정보 가져오는 함수
     * postID를 주어야함.
     * */
    fun viewPost(postId: Int){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.viewPost(postId).enqueue(object: Callback<APostResponse> {
            override fun onResponse(
                call: Call<APostResponse>,
                response: Response<APostResponse>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d("postPost/success", response.body().toString())
                        var resp = response.body()
                        binding.boardFreepostContentWriterTv.text = resp!!.writer.nickName
                        binding.boardFreepostContentPostingTimeTv.text = resp.createdAt
                        binding.boardFreepostContentPostTitleTv.text = resp.title
                        binding.boardFreepostContentPostContentTv.text = resp.content
                    }
                    else -> {
                        Log.d("postPost/fail", response.body().toString())
                    }
                }
            }

            override fun onFailure(call: Call<APostResponse>, t: Throwable) {
                Log.d("postPost/fail onfailure", t.toString())
            }

        })
    }
}