<<<<<<< HEAD
<<<<<<< HEAD:app/src/main/java/com/umc/healthper/ui/mypage/view/MusicMypageFragment.kt
package com.umc.healthper.ui.mypage.view
=======
package com.umc.Healthper.ui.mypage.view
>>>>>>> caf636f1beb9a3664e34b2a50dec9bb24ee4486a:app/src/main/java/com/umc/healthper/ui/mypage/view/WorkmusicMypageFragment.kt
=======
package com.umc.Healthper.ui.mypage.view
>>>>>>> caf636f1beb9a3664e34b2a50dec9bb24ee4486a

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
<<<<<<< HEAD
<<<<<<< HEAD:app/src/main/java/com/umc/healthper/ui/mypage/view/MusicMypageFragment.kt
import com.umc.healthper.databinding.FragmentMypageMusicBinding
=======
import com.umc.healthper.databinding.FragmentMypageWorkmusicBinding
>>>>>>> caf636f1beb9a3664e34b2a50dec9bb24ee4486a:app/src/main/java/com/umc/healthper/ui/mypage/view/WorkmusicMypageFragment.kt

class WorkmusicMypageFragment : Fragment() {

    lateinit var binding : FragmentMypageWorkmusicBinding
=======
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.healthper.databinding.FragmentMypageMusicBinding
import com.umc.Healthper.ui.mypage.adapter.MusicMypageVPAdapter

class MusicMypageFragment : Fragment() {

    lateinit var binding : FragmentMypageMusicBinding
    private val information = arrayListOf("운동 음악 모음", "휴식 음악 모음")

>>>>>>> caf636f1beb9a3664e34b2a50dec9bb24ee4486a
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
<<<<<<< HEAD
        binding = FragmentMypageWorkmusicBinding.inflate(inflater, container, false)
=======
        binding = FragmentMypageMusicBinding.inflate(inflater, container, false)

        val musicMypageAdapter = MusicMypageVPAdapter(this)
        binding.mypagemusicContentVp.adapter = musicMypageAdapter
        TabLayoutMediator(binding.mypagemusicContentTb, binding.mypagemusicContentVp) {
                tab, position ->
            tab.text = information[position]
        }.attach()

>>>>>>> caf636f1beb9a3664e34b2a50dec9bb24ee4486a
        return binding.root
    }
}