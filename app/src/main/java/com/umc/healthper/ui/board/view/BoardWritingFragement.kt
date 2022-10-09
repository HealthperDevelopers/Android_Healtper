//package com.umc.healthper.ui.board.view
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import com.umc.healthper.data.entity.Post
//import com.umc.healthper.data.entity.modiPost
//import com.umc.healthper.data.remote.AuthService
//import com.umc.healthper.databinding.FragmentBoardWritingBinding
//import com.umc.healthper.util.VarUtil
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//class BoardWritingFragement : Fragment() {
//    lateinit var binding: FragmentBoardWritingBinding
//    var title = ""
//    var content = ""
//    var postId = 0
//    var modify = false
//    var postType = "NORMAL"
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentBoardWritingBinding.inflate(inflater, container, false)
//        setTypeView(postType)
//
//        if (title != "")
//        {
//            CoroutineScope(Dispatchers.Main).launch{
//                binding.boardWritingTitleEt.setText(title)
//                binding.boardWritingContentEt.setText(content)
//            }
//        }
//
//        binding.boardWritingCancelIv.setOnClickListener {
//            VarUtil.glob.mainActivity.supportFragmentManager.popBackStack(
//                "boardWrite",
//                FragmentManager.POP_BACK_STACK_INCLUSIVE
//            )
//        }
//
//        if (!modify) {
//            binding.boardWritingFreepostBtnIv.setOnClickListener {
//                postType = "NORMAL"
//                setTypeView("NORMAL")
//            }
//
//            binding.boardWritingQuestionpostBtnIv.setOnClickListener {
//                postType = "QUESTION"
//                setTypeView("QUESTION")
//            }
//        }
//
//        binding.boardMyboardWritingPostingBtnIv.setOnClickListener{
//            val authService = AuthService()
//
//            if (!modify){
//                authService.postPost(
//                    Post(
//                        postType,
//                        binding.boardWritingTitleEt.text.toString(),
//                        binding.boardWritingContentEt.text.toString()
//                    )
//                )
//                VarUtil.glob.mainActivity.supportFragmentManager.popBackStack(
//                    "boardWrite",
//                    FragmentManager.POP_BACK_STACK_INCLUSIVE
//                )
//            }
//            else {
//                Log.d("postId", postId.toString())
//                authService.modifyPost(postId, modiPost(binding.boardWritingTitleEt.text.toString(),
//                    binding.boardWritingContentEt.text.toString()))
//                VarUtil.glob.mainActivity.supportFragmentManager.popBackStack(
//                    "boardWrite",
//                    FragmentManager.POP_BACK_STACK_INCLUSIVE
//                )
//            }
//        }
//
//        return binding.root
//    }
//
//
//    fun setTypeView(postType:String){
//        if (postType == "NORMAL"){
//            binding.boardWritingFreepostRedbtnIv.visibility = View.VISIBLE
//            binding.boardWritingQuestionpostRedbtnIv.visibility = View.INVISIBLE
//        }
//        else {
//            binding.boardWritingFreepostRedbtnIv.visibility = View.INVISIBLE
//            binding.boardWritingQuestionpostRedbtnIv.visibility = View.VISIBLE
//        }
//    }
//}