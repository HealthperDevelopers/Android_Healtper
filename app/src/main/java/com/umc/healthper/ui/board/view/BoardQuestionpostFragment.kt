package com.umc.healthper.ui.board.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc.healthper.data.remote.AuthRetrofitInterface
import com.umc.healthper.data.remote.Contents
import com.umc.healthper.data.remote.PostsResponse
import com.umc.healthper.data.remote.WriterInfo
import com.umc.healthper.databinding.FragmentBoardQuestionpostBinding
import com.umc.healthper.ui.board.adapter.BoardFreepostRVAdapter
import com.umc.healthper.ui.board.adapter.BoardQuestionpostRVAdapter
import com.umc.healthper.util.VarUtil
import com.umc.healthper.util.getRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardQuestionpostFragment : Fragment() {
    lateinit var binding : FragmentBoardQuestionpostBinding
    var post : PostsResponse = PostsResponse(arrayListOf(Contents(0, "", WriterInfo(0, "", ""), "", 0, 0, "")))
    var adapter = BoardQuestionpostRVAdapter()
    val bundle = Bundle()
    var page = 1

    override fun onResume() {
        super.onResume()
        page = 1
        VarUtil.glob.mainActivity.BoardFragment!!.tabPosition = "QUESTION"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getPosts("QUESTION", "LATEST", 0)

        binding = FragmentBoardQuestionpostBinding.inflate(inflater, container, false)
        val linearLayoutManagerWrapepr = LinearLayoutManagerWrapper(VarUtil.glob.mainContext, LinearLayoutManager.VERTICAL, false) // 이걸 만들어서
        binding.boardQuestionpostRv.layoutManager = linearLayoutManagerWrapepr // 이걸 넣는다.
        binding.boardQuestionpostRv.adapter = adapter

        binding.boardQuestionpostRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // 스크롤이 끝에 도달했는지 확인
                if (!binding.boardQuestionpostRv.canScrollVertically(1)) {
                    Log.d("end", "end")
                    CoroutineScope(Dispatchers.IO).launch {
                        adapter.deleteLoading(bundle.getString("sortType", "LATEST"), page++)
                    }
                }
            }
        })
        adapter.setListener(object: BoardQuestionpostRVAdapter.onClickListener {
            override fun onClick(postId: Int, likeCount:Int, CommentCount : Int, pos : Int) {
                Log.d("postId/Question", postId.toString())
                // post 조회
                CoroutineScope(Dispatchers.IO).launch {
                    bundle.putIntegerArrayList("like&commentCount", arrayListOf(likeCount, CommentCount, pos))
                    VarUtil.glob.mainActivity.boardFreepostContentFragment = BoardFreepostContentFragment()
                    VarUtil.glob.mainActivity.boardFreepostContentFragment!!.arguments = bundle
                    VarUtil.glob.mainActivity.boardFreepostContentFragment!!.postId = postId
                    VarUtil.glob.mainActivity.changeBoardFragment(2)
                }
            }
        })

        VarUtil.glob.boardQuestionpostFragment = this
        return binding.root
    }

    /** 게시글 목록 조회
     * @param sortType = {LATEST(최신순), LIKE(추천순), COMMENT(댓글순)}
     * @return postsResponse = 게시글 목록 30개의 정보
     * */
//    fun getPosts(type: String, sortType : String, page :Int) {
//        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
//
//        authService.getPosts(type, sortType, page).enqueue(object : Callback<PostsResponse> {
//            override fun onResponse(
//                call: Call<PostsResponse>,
//                response: Response<PostsResponse>
//            ) {
//                when (response.code()) {
//                    200 -> {
//                        Log.d("posts/SUCCESS", response.toString())
//                        Log.d("posts/resp", response.body().toString())
//                        post = response.body()!!
//                        val adapter = BoardQuestionpostRVAdapter(post.content)
//                        binding.boardFreepostRv.adapter = adapter
//
//                        adapter.setListener(object: BoardQuestionpostRVAdapter.onClickListener {
//                            override fun onClick(pos: Int) {
//                                Log.d("pos", pos.toString())
//                            }
//                        })
//                    }
//                    else -> {
//                        Log.d("posts/FAILURE", response.toString())
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
//                Log.d("posts/fail in onFailure", t.toString())
//            }
//        })
//    }

    fun getPosts(type : String, sortType : String, page :Int) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.getPosts(type, sortType, page).enqueue(object : Callback<PostsResponse> {
            override fun onResponse(
                call: Call<PostsResponse>,
                response: Response<PostsResponse>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d("posts/SUCCESS", response.toString())
                        Log.d("posts/resp", response.body().toString())

                        if (page == 0)
                            adapter.setList(true)

                        var post = response.body()!!
                        CoroutineScope(Dispatchers.Main).launch {
                            adapter.setList(post.content)
                            adapter.notifyItemRangeInserted(page * 30, (post.content.size + 1))
                        }
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