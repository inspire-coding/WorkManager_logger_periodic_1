package com.inspirecoding.workmanager_logger_periodic_1

import android.content.SharedPreferences

class SharedPreferencesRepository
{
    val KEY_INFO = "info"

    private lateinit var mySharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    fun initFilterSharedPreferences(sharedPreferences: SharedPreferences)
    {
        mySharedPreferences = sharedPreferences
    }

    fun setInfo (info : String)
    {
        editor = mySharedPreferences.edit()
        editor.putString(KEY_INFO, info)
        editor.apply()
    }
    fun getInfo() : String?
    {
        return mySharedPreferences.getString(KEY_INFO, "")
    }
}