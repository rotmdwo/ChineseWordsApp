package edu.skku.chinesewords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.IOException
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

            // initialize AD
            MobileAds.initialize(activity)

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
            var json = ""

            try {
                val inputStream = assets.open("hsk1.json")
                val fileSize = inputStream.available()
                val buffer = ByteArray(fileSize)
                inputStream.read(buffer)
                inputStream.close()

                json = String(buffer, Charsets.UTF_8)
                val jsonObject = JSONObject(json)
                val jsonArray = jsonObject.getJSONArray("hsk1")

                for (i in 0 until jsonArray.length()) {
                    val wordObject = jsonArray.getJSONObject(i)
                    val id = wordObject.getString("id")
                    val hanzi = wordObject.getString("hanzi")
                    val pinyin = wordObject.getString("pinyin")
                    val translations = wordObject.getString("translations")

                    pref.setWordAtHSK1(id, "${hanzi}@${pinyin}\n${translations}\r${0}\t${0}")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            pref.setExist(AppPreference.EXIST_HSK_1)
        }

        if (!pref.getExist(AppPreference.EXIST_HSK_2)) {

            var json = ""

            try {
                val inputStream = assets.open("hsk2.json")
                val fileSize = inputStream.available()
                val buffer = ByteArray(fileSize)
                inputStream.read(buffer)
                inputStream.close()

                json = String(buffer, Charsets.UTF_8)
                val jsonObject = JSONObject(json)
                val jsonArray = jsonObject.getJSONArray("hsk2")

                for (i in 0 until jsonArray.length()) {
                    val wordObject = jsonArray.getJSONObject(i)
                    val id = wordObject.getString("id")
                    val hanzi = wordObject.getString("hanzi")
                    val pinyin = wordObject.getString("pinyin")
                    val translations = wordObject.getString("translations")

                    pref.setWordAtHSK2(id, "${hanzi}@${pinyin}\n${translations}\r${0}\t${0}")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            pref.setExist(AppPreference.EXIST_HSK_2)
        }

        if (!pref.getExist(AppPreference.EXIST_HSK_3)) {

            var json = ""

            try {
                val inputStream = assets.open("hsk3.json")
                val fileSize = inputStream.available()
                val buffer = ByteArray(fileSize)
                inputStream.read(buffer)
                inputStream.close()

                json = String(buffer, Charsets.UTF_8)
                val jsonObject = JSONObject(json)
                val jsonArray = jsonObject.getJSONArray("hsk3")

                for (i in 0 until jsonArray.length()) {
                    val wordObject = jsonArray.getJSONObject(i)
                    val id = wordObject.getString("id")
                    val hanzi = wordObject.getString("hanzi")
                    val pinyin = wordObject.getString("pinyin")
                    val translations = wordObject.getString("translations")

                    pref.setWordAtHSK3(id, "${hanzi}@${pinyin}\n${translations}\r${0}\t${0}")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            pref.setExist(AppPreference.EXIST_HSK_3)
        }

        if (!pref.getExist(AppPreference.EXIST_HSK_4)) {

            var json = ""

            try {
                val inputStream = assets.open("hsk4.json")
                val fileSize = inputStream.available()
                val buffer = ByteArray(fileSize)
                inputStream.read(buffer)
                inputStream.close()

                json = String(buffer, Charsets.UTF_8)
                val jsonObject = JSONObject(json)
                val jsonArray = jsonObject.getJSONArray("hsk4")

                for (i in 0 until jsonArray.length()) {
                    val wordObject = jsonArray.getJSONObject(i)
                    val id = wordObject.getString("id")
                    val hanzi = wordObject.getString("hanzi")
                    val pinyin = wordObject.getString("pinyin")
                    val translations = wordObject.getString("translations")

                    pref.setWordAtHSK4(id, "${hanzi}@${pinyin}\n${translations}\r${0}\t${0}")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            pref.setExist(AppPreference.EXIST_HSK_4)
        }

        if (!pref.getExist(AppPreference.EXIST_HSK_5)) {

            var json = ""

            try {
                val inputStream = assets.open("hsk5.json")
                val fileSize = inputStream.available()
                val buffer = ByteArray(fileSize)
                inputStream.read(buffer)
                inputStream.close()

                json = String(buffer, Charsets.UTF_8)
                val jsonObject = JSONObject(json)
                val jsonArray = jsonObject.getJSONArray("hsk5")

                for (i in 0 until jsonArray.length()) {
                    val wordObject = jsonArray.getJSONObject(i)
                    val id = wordObject.getString("id")
                    val hanzi = wordObject.getString("hanzi")
                    val pinyin = wordObject.getString("pinyin")
                    val translations = wordObject.getString("translations")

                    pref.setWordAtHSK5(id, "${hanzi}@${pinyin}\n${translations}\r${0}\t${0}")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            pref.setExist(AppPreference.EXIST_HSK_5)
        }

        if (!pref.getExist(AppPreference.EXIST_HSK_6)) {


            pref.setExist(AppPreference.EXIST_HSK_6)
        }

//        if (!pref.getExist(AppPreference.EXIST_MY_WORDS)) {
//
//            var json = ""
//
//            try {
//                val inputStream = assets.open("mywords.json")
//                val fileSize = inputStream.available()
//                val buffer = ByteArray(fileSize)
//                inputStream.read(buffer)
//                inputStream.close()
//
//                json = String(buffer, Charsets.UTF_8)
//                val jsonObject = JSONObject(json)
//                val jsonArray = jsonObject.getJSONArray("mywords")
//
//                for (i in 0 until jsonArray.length()) {
//                    val wordObject = jsonArray.getJSONObject(i)
//                    val hanzi = wordObject.getString("hanzi")
//                    val pinyin = wordObject.getString("pinyin")
//                    val translations = wordObject.getString("translations")
//
//                    pref.setWordAtMyWords(hanzi, "${pinyin}\n${translations}\r${0}\t${0}")
//                }
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//
//            pref.setExist(AppPreference.EXIST_MY_WORDS)
//        }
    }
}