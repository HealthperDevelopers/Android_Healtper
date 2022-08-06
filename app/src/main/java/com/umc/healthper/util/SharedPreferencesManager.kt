package com.umc.healthper.util

import com.umc.healthper.util.VarUtil.Companion.mSharedPreferences

fun saveAutoLogin(isAuto: Boolean) {
    val editor = mSharedPreferences.edit()
    editor.putBoolean("isAuto", isAuto)

    editor.apply()
}

fun getAutoLogin(): Boolean = mSharedPreferences.getBoolean("isAuto", false)