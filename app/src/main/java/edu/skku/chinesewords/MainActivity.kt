package edu.skku.chinesewords

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.abs
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            starter.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            context.startActivity(starter)
        }

        var cursor = 0
        var answerLocation = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // load AD
        loadAd(adBottom)

        // TODO: 설정된 단어장 불러오기, 설정된 단어장 없으면 llEmptyWarning 띄우기
        val pref = AppPreference.get()
        val words = pref.getAllFromMyWords()
        val iterator = words.keys.iterator()
        val list = ArrayList<Array<String>>()

        while (iterator.hasNext()) {
            val key = iterator.next()
            val value = words.getOrDefault(key, "")
            if ("" != value) list.add(arrayOf(key, value))
        }

        // 랜덤 섞기
        list.shuffle()

        llAnswer1.setOnClickListener {

            when (answerLocation) {
                1 -> {
                    ivCorrect.visibility = View.VISIBLE
                    ivIncorrect.visibility = View.INVISIBLE
                    llAnswer1.setBackgroundResource(R.color.color_5ec8df)
                }
                2 -> {
                    ivCorrect.visibility = View.INVISIBLE
                    ivIncorrect.visibility = View.VISIBLE
                    llAnswer2.setBackgroundResource(R.color.color_5ec8df)
                }
                3 -> {
                    ivCorrect.visibility = View.INVISIBLE
                    ivIncorrect.visibility = View.VISIBLE
                    llAnswer3.setBackgroundResource(R.color.color_5ec8df)
                }
                4 -> {
                    ivCorrect.visibility = View.INVISIBLE
                    ivIncorrect.visibility = View.VISIBLE
                    llAnswer4.setBackgroundResource(R.color.color_5ec8df)
                }
            }

            val handler = Handler()
            handler.postDelayed(Runnable {
                ivCorrect.visibility = View.INVISIBLE
                ivIncorrect.visibility = View.INVISIBLE
                llAnswer1.setBackgroundResource(R.color.white)
                llAnswer2.setBackgroundResource(R.color.white)
                llAnswer3.setBackgroundResource(R.color.white)
                llAnswer4.setBackgroundResource(R.color.white)

                runGame(list)
            }, 1000)
        }
        llAnswer2.setOnClickListener {

            when (answerLocation) {
                1 -> {
                    ivCorrect.visibility = View.INVISIBLE
                    ivIncorrect.visibility = View.VISIBLE
                    llAnswer1.setBackgroundResource(R.color.color_5ec8df)
                }
                2 -> {
                    ivCorrect.visibility = View.VISIBLE
                    ivIncorrect.visibility = View.INVISIBLE
                    llAnswer2.setBackgroundResource(R.color.color_5ec8df)
                }
                3 -> {
                    ivCorrect.visibility = View.INVISIBLE
                    ivIncorrect.visibility = View.VISIBLE
                    llAnswer3.setBackgroundResource(R.color.color_5ec8df)
                }
                4 -> {
                    ivCorrect.visibility = View.INVISIBLE
                    ivIncorrect.visibility = View.VISIBLE
                    llAnswer4.setBackgroundResource(R.color.color_5ec8df)
                }
            }

            val handler = Handler()
            handler.postDelayed(Runnable {
                ivCorrect.visibility = View.INVISIBLE
                ivIncorrect.visibility = View.INVISIBLE
                llAnswer1.setBackgroundResource(R.color.white)
                llAnswer2.setBackgroundResource(R.color.white)
                llAnswer3.setBackgroundResource(R.color.white)
                llAnswer4.setBackgroundResource(R.color.white)

                runGame(list)
            }, 1000)
        }
        llAnswer3.setOnClickListener {

            when (answerLocation) {
                1 -> {
                    ivCorrect.visibility = View.INVISIBLE
                    ivIncorrect.visibility = View.VISIBLE
                    llAnswer1.setBackgroundResource(R.color.color_5ec8df)
                }
                2 -> {
                    ivCorrect.visibility = View.INVISIBLE
                    ivIncorrect.visibility = View.VISIBLE
                    llAnswer2.setBackgroundResource(R.color.color_5ec8df)
                }
                3 -> {
                    ivCorrect.visibility = View.VISIBLE
                    ivIncorrect.visibility = View.INVISIBLE
                    llAnswer3.setBackgroundResource(R.color.color_5ec8df)
                }
                4 -> {
                    ivCorrect.visibility = View.INVISIBLE
                    ivIncorrect.visibility = View.VISIBLE
                    llAnswer4.setBackgroundResource(R.color.color_5ec8df)
                }
            }

            val handler = Handler()
            handler.postDelayed(Runnable {
                ivCorrect.visibility = View.INVISIBLE
                ivIncorrect.visibility = View.INVISIBLE
                llAnswer1.setBackgroundResource(R.color.white)
                llAnswer2.setBackgroundResource(R.color.white)
                llAnswer3.setBackgroundResource(R.color.white)
                llAnswer4.setBackgroundResource(R.color.white)

                runGame(list)
            }, 1000)
        }
        llAnswer4.setOnClickListener {

            when (answerLocation) {
                1 -> {
                    ivCorrect.visibility = View.INVISIBLE
                    ivIncorrect.visibility = View.VISIBLE
                    llAnswer1.setBackgroundResource(R.color.color_5ec8df)
                }
                2 -> {
                    ivCorrect.visibility = View.INVISIBLE
                    ivIncorrect.visibility = View.VISIBLE
                    llAnswer2.setBackgroundResource(R.color.color_5ec8df)
                }
                3 -> {
                    ivCorrect.visibility = View.INVISIBLE
                    ivIncorrect.visibility = View.VISIBLE
                    llAnswer3.setBackgroundResource(R.color.color_5ec8df)
                }
                4 -> {
                    ivCorrect.visibility = View.VISIBLE
                    ivIncorrect.visibility = View.INVISIBLE
                    llAnswer4.setBackgroundResource(R.color.color_5ec8df)
                }
            }

            val handler = Handler()
            handler.postDelayed(Runnable {
                ivCorrect.visibility = View.INVISIBLE
                ivIncorrect.visibility = View.INVISIBLE
                llAnswer1.setBackgroundResource(R.color.white)
                llAnswer2.setBackgroundResource(R.color.white)
                llAnswer3.setBackgroundResource(R.color.white)
                llAnswer4.setBackgroundResource(R.color.white)

                runGame(list)
            }, 1000)
        }

        // 게임 시작
        runGame(list)
    }

    private fun loadAd(adView: AdView) {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun runGame(list: ArrayList<Array<String>>) {
        when (getRandomNumber1to6()) {
            1 -> {
                // 문제: 한자, 답: 발음
                val question = list[cursor][0]
                val answer: String = list[cursor][1].substring(0, list[cursor][1].indexOf("\n"))

                tvQuestion.text = question
                answerLocation = getRandomNumber1toN(4)

                when (answerLocation) {
                    1 -> {
                        tvAnswer1.text = answer
                    }
                    2 -> {
                        tvAnswer2.text = answer
                    }
                    3 -> {
                        tvAnswer3.text = answer
                    }
                    4 -> {
                        tvAnswer4.text = answer
                    }
                }

                var howManyAnswersSet = 1
                val set = HashSet<Int>()
                set.add(cursor)
                var i = 1

                while (i <= 4) {
                    if (i == answerLocation) {
                        i++
                        continue
                    }

                    val index = getRandomNumber1toN(list.size) - 1

                    if (!set.contains(index)) {
                        set.add(index)

                        Log.d("asdf", list[index][1])
                        val answerWrong = list[index][1].substring(0, list[index][1].indexOf("\n"))

                        when (i) {
                            1 -> {
                                tvAnswer1.text = answerWrong
                            }
                            2 -> {
                                tvAnswer2.text = answerWrong
                            }
                            3 -> {
                                tvAnswer3.text = answerWrong
                            }
                            4 -> {
                                tvAnswer4.text = answerWrong
                            }
                        }

                        i++
                    }
                }
            }
            2 -> {
                // 문제: 한자, 답: 뜻
                val question = list[cursor][0]
                val answer: String = list[cursor][1].substring(list[cursor][1].indexOf("\n") + 1)

                tvQuestion.text = question
                answerLocation = getRandomNumber1toN(4)

                when (answerLocation) {
                    1 -> {
                        tvAnswer1.text = answer
                    }
                    2 -> {
                        tvAnswer2.text = answer
                    }
                    3 -> {
                        tvAnswer3.text = answer
                    }
                    4 -> {
                        tvAnswer4.text = answer
                    }
                }

                var howManyAnswersSet = 1
                val set = HashSet<Int>()
                set.add(cursor)
                var i = 1

                while (i <= 4) {
                    if (i == answerLocation) {
                        i++
                        continue
                    }

                    val index = getRandomNumber1toN(list.size) - 1

                    if (!set.contains(index)) {
                        set.add(index)

                        Log.d("asdf", list[index][1])
                        val answerWrong = list[index][1].substring(list[index][1].indexOf("\n") + 1)

                        when (i) {
                            1 -> {
                                tvAnswer1.text = answerWrong
                            }
                            2 -> {
                                tvAnswer2.text = answerWrong
                            }
                            3 -> {
                                tvAnswer3.text = answerWrong
                            }
                            4 -> {
                                tvAnswer4.text = answerWrong
                            }
                        }

                        i++
                    }
                }
            }
            3 -> {
// 문제: 한자, 답: 발음
                val question = list[cursor][0]
                val answer: String = list[cursor][1].substring(0, list[cursor][1].indexOf("\n"))

                tvQuestion.text = question
                answerLocation = getRandomNumber1toN(4)

                when (answerLocation) {
                    1 -> {
                        tvAnswer1.text = answer
                    }
                    2 -> {
                        tvAnswer2.text = answer
                    }
                    3 -> {
                        tvAnswer3.text = answer
                    }
                    4 -> {
                        tvAnswer4.text = answer
                    }
                }

                var howManyAnswersSet = 1
                val set = HashSet<Int>()
                set.add(cursor)
                var i = 1

                while (i <= 4) {
                    if (i == answerLocation) {
                        i++
                        continue
                    }

                    val index = getRandomNumber1toN(list.size) - 1

                    if (!set.contains(index)) {
                        set.add(index)

                        Log.d("asdf", list[index][1])
                        val answerWrong = list[index][1].substring(0, list[index][1].indexOf("\n"))

                        when (i) {
                            1 -> {
                                tvAnswer1.text = answerWrong
                            }
                            2 -> {
                                tvAnswer2.text = answerWrong
                            }
                            3 -> {
                                tvAnswer3.text = answerWrong
                            }
                            4 -> {
                                tvAnswer4.text = answerWrong
                            }
                        }

                        i++
                    }
                }
            }
            4 -> {
// 문제: 한자, 답: 뜻
                val question = list[cursor][0]
                val answer: String = list[cursor][1].substring(list[cursor][1].indexOf("\n") + 1)

                tvQuestion.text = question
                answerLocation = getRandomNumber1toN(4)

                when (answerLocation) {
                    1 -> {
                        tvAnswer1.text = answer
                    }
                    2 -> {
                        tvAnswer2.text = answer
                    }
                    3 -> {
                        tvAnswer3.text = answer
                    }
                    4 -> {
                        tvAnswer4.text = answer
                    }
                }

                var howManyAnswersSet = 1
                val set = HashSet<Int>()
                set.add(cursor)
                var i = 1

                while (i <= 4) {
                    if (i == answerLocation) {
                        i++
                        continue
                    }

                    val index = getRandomNumber1toN(list.size) - 1

                    if (!set.contains(index)) {
                        set.add(index)

                        Log.d("asdf", list[index][1])
                        val answerWrong = list[index][1].substring(list[index][1].indexOf("\n") + 1)

                        when (i) {
                            1 -> {
                                tvAnswer1.text = answerWrong
                            }
                            2 -> {
                                tvAnswer2.text = answerWrong
                            }
                            3 -> {
                                tvAnswer3.text = answerWrong
                            }
                            4 -> {
                                tvAnswer4.text = answerWrong
                            }
                        }

                        i++
                    }
                }
            }
            5 -> {
// 문제: 한자, 답: 발음
                val question = list[cursor][0]
                val answer: String = list[cursor][1].substring(0, list[cursor][1].indexOf("\n"))

                tvQuestion.text = question
                answerLocation = getRandomNumber1toN(4)

                when (answerLocation) {
                    1 -> {
                        tvAnswer1.text = answer
                    }
                    2 -> {
                        tvAnswer2.text = answer
                    }
                    3 -> {
                        tvAnswer3.text = answer
                    }
                    4 -> {
                        tvAnswer4.text = answer
                    }
                }

                var howManyAnswersSet = 1
                val set = HashSet<Int>()
                set.add(cursor)
                var i = 1

                while (i <= 4) {
                    if (i == answerLocation) {
                        i++
                        continue
                    }

                    val index = getRandomNumber1toN(list.size) - 1

                    if (!set.contains(index)) {
                        set.add(index)

                        Log.d("asdf", list[index][1])
                        val answerWrong = list[index][1].substring(0, list[index][1].indexOf("\n"))

                        when (i) {
                            1 -> {
                                tvAnswer1.text = answerWrong
                            }
                            2 -> {
                                tvAnswer2.text = answerWrong
                            }
                            3 -> {
                                tvAnswer3.text = answerWrong
                            }
                            4 -> {
                                tvAnswer4.text = answerWrong
                            }
                        }

                        i++
                    }
                }
            }
            6 -> {
// 문제: 한자, 답: 뜻
                val question = list[cursor][0]
                val answer: String = list[cursor][1].substring(list[cursor][1].indexOf("\n") + 1)

                tvQuestion.text = question
                answerLocation = getRandomNumber1toN(4)

                when (answerLocation) {
                    1 -> {
                        tvAnswer1.text = answer
                    }
                    2 -> {
                        tvAnswer2.text = answer
                    }
                    3 -> {
                        tvAnswer3.text = answer
                    }
                    4 -> {
                        tvAnswer4.text = answer
                    }
                }

                var howManyAnswersSet = 1
                val set = HashSet<Int>()
                set.add(cursor)
                var i = 1

                while (i <= 4) {
                    if (i == answerLocation) {
                        i++
                        continue
                    }

                    val index = getRandomNumber1toN(list.size) - 1

                    if (!set.contains(index)) {
                        set.add(index)

                        Log.d("asdf", list[index][1])
                        val answerWrong = list[index][1].substring(list[index][1].indexOf("\n") + 1)

                        when (i) {
                            1 -> {
                                tvAnswer1.text = answerWrong
                            }
                            2 -> {
                                tvAnswer2.text = answerWrong
                            }
                            3 -> {
                                tvAnswer3.text = answerWrong
                            }
                            4 -> {
                                tvAnswer4.text = answerWrong
                            }
                        }

                        i++
                    }
                }
            }
        }

        // 커서 세팅
        if (cursor < list.size - 1) cursor++
        else cursor = 0
    }

    private fun getRandomNumber1to6(): Int {
        return abs(Random().nextInt()) % 6 + 1
    }

    private fun getRandomNumber1toN(N: Int): Int {
        return abs(Random().nextInt()) % N + 1
    }
}