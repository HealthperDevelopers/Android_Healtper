<<<<<<< HEAD:app/src/main/java/com/umc/healthper/ui/mypage/view/FavoritesMypageFragment.kt
package com.umc.healthper.ui.mypage.view
=======
package com.umc.Healthper.ui.mypage.view
>>>>>>> caf636f1beb9a3664e34b2a50dec9bb24ee4486a:app/src/main/java/com/umc/healthper/ui/FavoritesMypageFragment.kt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umc.healthper.databinding.FragmentMypageFavoritesBinding

class FavoritesMypageFragment : Fragment() {

    lateinit var binding : FragmentMypageFavoritesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }
}