package com.umc.healthper.ui

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.umc.healthper.R
import com.umc.healthper.data.entity.Work
import com.umc.healthper.data.local.LocalDB
import com.umc.healthper.ui.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        },DURATION)

        initDb(applicationContext)

    }
    companion object {
        private const val DURATION : Long = 3000
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    fun initDb(context: Context) {
            val db = LocalDB.getInstance(context)!!
            if (db.WorkDao().getFirst() == null) {
                val manager: AssetManager = context.assets
                val input: InputStream = manager.open("workList.txt")

                var part = ""
                var next = false
                input.bufferedReader().readLines().forEach {
                    val work = it
                    if (next) {
                        part = work
                        next = false
                    }
                    else if (work == "-") next = true
                    else if (!next) {
                        CoroutineScope(Dispatchers.IO).launch {
                            if (!next) {
                                var inp = Work(
                                    0,work, part, 0, 0, 0
                                )
                                db.WorkDao().insert(inp)
                            }
                        }
                    }
                }
            }
    }
}