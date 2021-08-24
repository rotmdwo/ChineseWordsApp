package edu.skku.chinesewords

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
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

        val wordTree = WordTree()
        var answerLocation = 0
        lateinit var tts: TextToSpeech
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

        while (iterator.hasNext()) {
            val key = iterator.next()
            val value = words.getOrDefault(key, "")
            if ("" != value) {
                wordTree.addItem(WordTree.Word(key, value.substring(0, value.indexOf("\n")), value.substring(value.indexOf("\n") + 1)))
            }
        }

        // 랜덤 섞기
        wordTree.shuffle()

        llAnswer1.setOnClickListener {
            toggleAnswersClickable(false)
            setllQuestionOnClick {}

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

                runGame(wordTree)
                toggleAnswersClickable(true)
            }, 1000)
        }
        llAnswer2.setOnClickListener {
            toggleAnswersClickable(false)
            setllQuestionOnClick {}

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

                runGame(wordTree)
                toggleAnswersClickable(true)
            }, 1000)
        }
        llAnswer3.setOnClickListener {
            toggleAnswersClickable(false)
            setllQuestionOnClick {}

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

                runGame(wordTree)
                toggleAnswersClickable(true)
            }, 1000)
        }
        llAnswer4.setOnClickListener {
            toggleAnswersClickable(false)
            setllQuestionOnClick {}

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

                runGame(wordTree)
                toggleAnswersClickable(true)
            }, 1000)
        }

        // 게임 시작
        runGame(wordTree)
    }

    private fun loadAd(adView: AdView) {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun runGame(wordTree: WordTree) {
        when (getRandomNumber1toN(8)) {
            1 -> {
                // 문제: 한자, 답: 발음
                setQuestionType(isText = true)
                val word = wordTree.next()
                val question = word.hanzi
                val answer = word.pinyin

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

                val set = HashSet<WordTree.Word>()
                set.add(word)
                var i = 1

                while (i <= 4) {
                    if (i == answerLocation) {
                        i++
                        continue
                    }

                    val wrongWord = getRandomWord(word)

                    if (!set.contains(wrongWord)) {
                        set.add(wrongWord)

                        val answerWrong = wrongWord.pinyin

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
                setQuestionType(isText = true)
                val word = wordTree.next()
                val question = word.hanzi
                val answer = word.translations

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

                val set = HashSet<WordTree.Word>()
                set.add(word)
                var i = 1

                while (i <= 4) {
                    if (i == answerLocation) {
                        i++
                        continue
                    }

                    val wrongWord = getRandomWord()

                    if (!set.contains(wrongWord)) {
                        set.add(wrongWord)

                        val answerWrong = wrongWord.translations

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
                // 문제: 발음, 답: 한자
                setQuestionType(isText = true)
                val word = wordTree.next()
                val question = word.pinyin
                val answer = word.hanzi

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

                val set = HashSet<WordTree.Word>()
                set.add(word)
                var i = 1

                while (i <= 4) {
                    if (i == answerLocation) {
                        i++
                        continue
                    }

                    val wrongWord = getRandomWord(word)

                    if (!set.contains(wrongWord)) {
                        set.add(wrongWord)

                        val answerWrong = wrongWord.hanzi

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
                // 문제: 발음, 답: 뜻
                setQuestionType(isText = true)
                val word = wordTree.next()
                val question = word.pinyin
                val answer = word.translations

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

                val set = HashSet<WordTree.Word>()
                set.add(word)
                var i = 1

                while (i <= 4) {
                    if (i == answerLocation) {
                        i++
                        continue
                    }

                    val wrongWord = getRandomWord()

                    if (!set.contains(wrongWord)) {
                        set.add(wrongWord)

                        val answerWrong = wrongWord.translations

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
                // 문제: 뜻, 답: 한자
                setQuestionType(isText = true)
                val word = wordTree.next()
                val question = word.translations
                val answer = word.hanzi

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

                val set = HashSet<WordTree.Word>()
                set.add(word)
                var i = 1

                while (i <= 4) {
                    if (i == answerLocation) {
                        i++
                        continue
                    }

                    val wrongWord = getRandomWord()

                    if (!set.contains(wrongWord)) {
                        set.add(wrongWord)

                        val answerWrong = wrongWord.hanzi

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
                // 문제: 뜻, 답: 발음
                setQuestionType(isText = true)
                val word = wordTree.next()
                val question = word.translations
                val answer = word.pinyin

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

                val set = HashSet<WordTree.Word>()
                set.add(word)
                var i = 1

                while (i <= 4) {
                    if (i == answerLocation) {
                        i++
                        continue
                    }

                    val wrongWord = getRandomWord()

                    if (!set.contains(wrongWord)) {
                        set.add(wrongWord)

                        val answerWrong = wrongWord.pinyin

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
            7 -> {
                // 문제: 한자발음소리, 답: 한자
                setQuestionType(isText = false)
                val word = wordTree.next()
                val question = word.hanzi
                val answer = word.hanzi
                tts = TextToSpeech(this, TextToSpeech.OnInitListener {
                    if (it != TextToSpeech.ERROR) {
                        tts.language = Locale.SIMPLIFIED_CHINESE
                        tts.speak(question,TextToSpeech.QUEUE_FLUSH, null)
                        setllQuestionOnClick {
                            tts.speak(question,TextToSpeech.QUEUE_FLUSH, null)
                        }
                    }
                })

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

                val set = HashSet<WordTree.Word>()
                set.add(word)
                var i = 1

                while (i <= 4) {
                    if (i == answerLocation) {
                        i++
                        continue
                    }

                    val wrongWord = getRandomWord(word)

                    if (!set.contains(wrongWord)) {
                        set.add(wrongWord)

                        val answerWrong = wrongWord.hanzi

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
            8 -> {
                // 문제: 한자발음소리, 답: 뜻
                setQuestionType(isText = false)
                val word = wordTree.next()
                val question = word.hanzi
                val answer = word.translations
                tts = TextToSpeech(this, TextToSpeech.OnInitListener {
                    if (it != TextToSpeech.ERROR) {
                        tts.language = Locale.SIMPLIFIED_CHINESE
                        tts.speak(question,TextToSpeech.QUEUE_FLUSH, null)
                        setllQuestionOnClick {
                            tts.speak(question,TextToSpeech.QUEUE_FLUSH, null)
                        }
                    }
                })

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

                val set = HashSet<WordTree.Word>()
                set.add(word)
                var i = 1

                while (i <= 4) {
                    if (i == answerLocation) {
                        i++
                        continue
                    }

                    val wrongWord = getRandomWord()

                    if (!set.contains(wrongWord)) {
                        set.add(wrongWord)

                        val answerWrong = wrongWord.translations

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
    }

    private fun getRandomNumber1toN(N: Int): Int {
        return abs(Random().nextInt()) % N + 1
    }

    private fun toggleAnswersClickable(on: Boolean) {
        if (on) {
            llAnswer1.isClickable = true
            llAnswer2.isClickable = true
            llAnswer3.isClickable = true
            llAnswer4.isClickable = true
        } else {
            llAnswer1.isClickable = false
            llAnswer2.isClickable = false
            llAnswer3.isClickable = false
            llAnswer4.isClickable = false
        }
    }

    private fun setllQuestionOnClick(body: () -> Unit) {
        llQuestion.setOnClickListener {
            body()
        }
    }

    private fun setQuestionType(isText: Boolean) {
        if (isText) {
            tvQuestion.visibility = View.VISIBLE
            ivSpeaker.visibility = View.GONE
        } else {
            tvQuestion.visibility = View.GONE
            ivSpeaker.visibility = View.VISIBLE
        }
    }

    private fun getRandomWord(word: WordTree.Word): WordTree.Word {
        return when (word.hanzi.length) {
            1 -> {
                if (wordTree.length1List.size < 4) wordTree.allList[getRandomNumber1toN(wordTree.allList.size) - 1]
                wordTree.length1List[getRandomNumber1toN(wordTree.length1List.size) - 1]
            }
            2 -> {
                if (wordTree.length2List.size < 4) wordTree.allList[getRandomNumber1toN(wordTree.allList.size) - 1]
                wordTree.length2List[getRandomNumber1toN(wordTree.length2List.size) - 1]
            }
            3 -> {
                if (wordTree.length3List.size < 4) wordTree.allList[getRandomNumber1toN(wordTree.allList.size) - 1]
                wordTree.length3List[getRandomNumber1toN(wordTree.length3List.size) - 1]
            }
            4 -> {
                if (wordTree.length4List.size < 4) wordTree.allList[getRandomNumber1toN(wordTree.allList.size) - 1]
                wordTree.length4List[getRandomNumber1toN(wordTree.length4List.size) - 1]
            }
            else -> {
                if (wordTree.length4List.size < 4) wordTree.allList[getRandomNumber1toN(wordTree.allList.size) - 1]
                wordTree.length4List[getRandomNumber1toN(wordTree.length4List.size) - 1]
            }
        }
    }

    private fun getRandomWord(): WordTree.Word {
        return wordTree.allList[getRandomNumber1toN(wordTree.allList.size) - 1]
    }
}