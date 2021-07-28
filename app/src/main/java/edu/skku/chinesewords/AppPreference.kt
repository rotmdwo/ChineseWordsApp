package edu.skku.chinesewords

import android.content.Context
import android.content.SharedPreferences

class AppPreference {
    companion object {
        val PREFERENCE_NAME_EXIST = "PREFERENCE_NAME_EXIST"
        val PREFERENCE_NAME_HSK_1 = "PREFERENCE_NAME_HSK_1"
        val PREFERENCE_NAME_HSK_2 = "PREFERENCE_NAME_HSK_2"
        val PREFERENCE_NAME_HSK_3 = "PREFERENCE_NAME_HSK_3"
        val PREFERENCE_NAME_HSK_4 = "PREFERENCE_NAME_HSK_4"
        val PREFERENCE_NAME_HSK_5 = "PREFERENCE_NAME_HSK_5"
        val PREFERENCE_NAME_HSK_6 = "PREFERENCE_NAME_HSK_6"
        val PREFERENCE_NAME_MY_WORDS = "PREFERENCE_NAME_MY_WORDS"

        val EXIST_HSK_1 = "EXIST_HSK_1"
        val EXIST_HSK_2 = "EXIST_HSK_2"
        val EXIST_HSK_3 = "EXIST_HSK_3"
        val EXIST_HSK_4 = "EXIST_HSK_4"
        val EXIST_HSK_5 = "EXIST_HSK_5"
        val EXIST_HSK_6 = "EXIST_HSK_6"


        private var INSTANCE: AppPreference? = null

        fun install(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = AppPreference(context)
            }
        }

        fun get(): AppPreference {
            return INSTANCE!!
        }
    }

    private var mSharedPreferencsExist: SharedPreferences
    private var mSharedPreferencsHSK1: SharedPreferences
    private var mSharedPreferencsHSK2: SharedPreferences
    private var mSharedPreferencsHSK3: SharedPreferences
    private var mSharedPreferencsHSK4: SharedPreferences
    private var mSharedPreferencsHSK5: SharedPreferences
    private var mSharedPreferencsHSK6: SharedPreferences


    private constructor(context: Context) {
        mSharedPreferencsExist = context.getSharedPreferences(PREFERENCE_NAME_EXIST, Context.MODE_PRIVATE)
        mSharedPreferencsHSK1 = context.getSharedPreferences(PREFERENCE_NAME_HSK_1, Context.MODE_PRIVATE)
        mSharedPreferencsHSK2 = context.getSharedPreferences(PREFERENCE_NAME_HSK_2, Context.MODE_PRIVATE)
        mSharedPreferencsHSK3 = context.getSharedPreferences(PREFERENCE_NAME_HSK_3, Context.MODE_PRIVATE)
        mSharedPreferencsHSK4 = context.getSharedPreferences(PREFERENCE_NAME_HSK_4, Context.MODE_PRIVATE)
        mSharedPreferencsHSK5 = context.getSharedPreferences(PREFERENCE_NAME_HSK_5, Context.MODE_PRIVATE)
        mSharedPreferencsHSK6 = context.getSharedPreferences(PREFERENCE_NAME_HSK_6, Context.MODE_PRIVATE)
    }

    fun getSharedPreferencesHSK1(): SharedPreferences {
        return mSharedPreferencsHSK1
    }


    //////////////////////////////////////////////////
    // set 메서드
    //////////////////////////////////////////////////

    fun setExist(name: String) {
        mSharedPreferencsExist.edit().putBoolean(name, true).apply()
    }

    fun getExist(name: String): Boolean {
        return mSharedPreferencsExist.getBoolean(name, false)
    }

    fun setWordAtHSK1(character: String, meaning: String) {
        mSharedPreferencsHSK1.edit().putString(character, meaning).apply()
    }

    fun getWordFromHSK1(character: String): String {
        return mSharedPreferencsHSK1.getString(character, "")!!
    }

    fun getAllFromHSK1(): Map<String, String> {
        return mSharedPreferencsHSK1.all as Map<String, String>
    }
}