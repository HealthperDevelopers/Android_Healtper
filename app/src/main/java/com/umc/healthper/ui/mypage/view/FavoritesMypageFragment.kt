package com.umc.healthper.ui.mypage.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.umc.healthper.R
import com.umc.healthper.databinding.FragmentMypageFavoritesBinding

class FavoritesMypageFragment : Fragment() {

    lateinit var binding : FragmentMypageFavoritesBinding
    val numButton : Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageFavoritesBinding.inflate(inflater, container, false)
        binding.mypagefavPlusIv.setOnClickListener{
            val mypageWork = LayoutInflater.from(context).inflate(R.layout.item_mypage_favorites,null,false)
            mypageWork.findViewById<TextView>(R.id.mypagefav_work).text = "work"
            binding.mypagefavLlo.addView(mypageWork)
        }
        return binding.root
    }

}