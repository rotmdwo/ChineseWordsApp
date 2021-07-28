package edu.skku.chinesewords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*
import java.util.*

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var activity: OnBoardingActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_on_boarding)

        activity = this

        // 비동기 실행
        CoroutineScope(Dispatchers.IO).async {

            val startTime = System.currentTimeMillis()

            // install
            AppPreference.install(activity)

            // set Words
            setHSKWords()

            val takenTime = System.currentTimeMillis() - startTime
            val timerTask: TimerTask = MyTimerTask(activity)
            val timer = Timer()

            if (takenTime >= 1000L) {
                timer.schedule(timerTask, 0L)
            } else {
                timer.schedule(timerTask, 1000L - takenTime)
            }
        }



//        MainActivity.start(this)
//        finish()
    }

    class MyTimerTask(private val activity: OnBoardingActivity): TimerTask() {
        override fun run() {
            MainActivity.start(activity)
            activity.finish()
        }

    }

    private fun setHSKWords() {
        val pref = AppPreference.get()

        if (!pref.getExist(AppPreference.EXIST_HSK_1)) {
            pref.setWordAtHSK1("爱", "사랑하다")
            pref.setWordAtHSK1("八", "8")
            pref.setWordAtHSK1("爸爸", "아빠")
            pref.setWordAtHSK1("杯子", "잔")

            pref.setExist(AppPreference.EXIST_HSK_1)
        }

        if (!pref.getExist(AppPreference.EXIST_HSK_2)) {


            pref.setExist(AppPreference.EXIST_HSK_2)
        }

        if (!pref.getExist(AppPreference.EXIST_HSK_3)) {


            pref.setExist(AppPreference.EXIST_HSK_3)
        }

        if (!pref.getExist(AppPreference.EXIST_HSK_4)) {


            pref.setExist(AppPreference.EXIST_HSK_4)
        }

        if (!pref.getExist(AppPreference.EXIST_HSK_5)) {


            pref.setExist(AppPreference.EXIST_HSK_5)
        }

        if (!pref.getExist(AppPreference.EXIST_HSK_6)) {


            pref.setExist(AppPreference.EXIST_HSK_6)
        }
    }
}