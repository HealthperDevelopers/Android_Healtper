<<<<<<< HEAD
package com.umc.healthper.ui.mypage.view

class WorkmusicMypageFragment {
=======
<<<<<<< HEAD:app/src/main/java/com/umc/healthper/ui/mypage/view/MusicMypageFragment.kt
package com.umc.healthper.ui.mypage.view
=======
package com.umc.Healthper.ui.mypage.view
>>>>>>> caf636f1beb9a3664e34b2a50dec9bb24ee4486a:app/src/main/java/com/umc/healthper/ui/mypage/view/WorkmusicMypageFragment.kt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
<<<<<<< HEAD:app/src/main/java/com/umc/healthper/ui/mypage/view/MusicMypageFragment.kt
import com.umc.healthper.databinding.FragmentMypageMusicBinding
=======
import com.umc.healthper.databinding.FragmentMypageWorkmusicBinding
>>>>>>> caf636f1beb9a3664e34b2a50dec9bb24ee4486a:app/src/main/java/com/umc/healthper/ui/mypage/view/WorkmusicMypageFragment.kt

class WorkmusicMypageFragment : Fragment() {

    lateinit var binding : FragmentMypageWorkmusicBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageWorkmusicBinding.inflate(inflater, container, false)
        return binding.root
    }
>>>>>>> caf636f1beb9a3664e34b2a50dec9bb24ee4486a
}