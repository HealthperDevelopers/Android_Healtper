<<<<<<< HEAD:app/src/main/java/com/umc/healthper/ui/mypage/view/MypageFragment.kt
package com.umc.healthper.ui.mypage.view
=======
package com.umc.Healthper.ui.mypage.view
>>>>>>> caf636f1beb9a3664e34b2a50dec9bb24ee4486a:app/src/main/java/com/umc/healthper/ui/MypageFragment.kt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentMypageBinding
<<<<<<< HEAD:app/src/main/java/com/umc/healthper/ui/mypage/view/MypageFragment.kt
import com.umc.healthper.ui.MainActivity
=======
>>>>>>> caf636f1beb9a3664e34b2a50dec9bb24ee4486a:app/src/main/java/com/umc/healthper/ui/MypageFragment.kt

class MypageFragment : Fragment() {

    lateinit var binding : FragmentMypageBinding
    var mainActivity: com.umc.Healthper.ui.MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as com.umc.Healthper.ui.MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        binding.mypageFavoritesIv.setOnClickListener{
            mainActivity!!.changeMypageFragment(0)
        }
        binding.mypageMusicIv.setOnClickListener{
            mainActivity!!.changeMypageFragment(1)
        }
        return binding.root
    }
}