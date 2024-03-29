package edu.skku.chinesewords

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.view.Gravity
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.abs
import java.util.*
import edu.skku.chinesewords.WordTree.Word
import edu.skku.chinesewords.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            starter.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            context.startActivity(starter)
        }

        lateinit var currentWord: Word
        val allWordsTree = WordTree()
        val myWordsTree = WordTree()
        var answerLocation = 0
        lateinit var tts: TextToSpeech

        val pref = AppPreference.get()
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // load AD
        loadAd(adBottom)

        // TODO: 설정된 단어장 불러오기, 설정된 단어장 없으면 llEmptyWarning 띄우기
        val pref = AppPreference.get()
        val hsk1Words = pref.getAllFromHSK1()
        val hsk2Words = pref.getAllFromHSK2()
        val hsk3Words = pref.getAllFromHSK3()
        val hsk4Words = pref.getAllFromHSK4()
        val hsk5Words = pref.getAllFromHSK5()
        val allWords = hsk1Words.plus(hsk2Words).plus(hsk3Words).plus(hsk4Words).plus(hsk5Words)
        val allWordsIterator = allWords.keys.iterator()
        val myWords = pref.getAllFromMyWords()
        val myWordsIterator = myWords.keys.iterator()

        // 모든 단어 리스트
        while (allWordsIterator.hasNext()) {
            val key = allWordsIterator.next()
            val value = allWords.getOrDefault(key, "")
            if ("" != value) {
                val hanzi: String = value.substring(0, value.indexOf("@"))
                val pinyin: String = value.substring(value.indexOf("@") + 1, value.indexOf("\n"))
                val translations: String = value.substring(value.indexOf("\n") + 1, value.indexOf("\r"))
                val correct = value.substring(value.indexOf("\r") + 1, value.indexOf("\t")).toInt()
                val wrong = value.substring(value.indexOf("\t") + 1).toInt()
                val word = Word(key, hanzi, pinyin, translations, correct, wrong, if (correct == 0 && wrong == 0) 0.0 else if (wrong == 0) 1.0 else correct / (correct + wrong).toDouble())
                word.selected = myWords.containsKey(key)
                allWordsTree.addItem(word)
            }
        }

        // 나의 단어 리스트
        while (myWordsIterator.hasNext()) {
            val key = myWordsIterator.next()
            val value = myWords.getOrDefault(key, "")
            if ("" != value) {
                val hanzi: String = value.substring(0, value.indexOf("@"))
                val pinyin: String = value.substring(value.indexOf("@") + 1, value.indexOf("\n"))
                val translations: String = value.substring(value.indexOf("\n") + 1, value.indexOf("\r"))
                val correct = value.substring(value.indexOf("\r") + 1, value.indexOf("\t")).toInt()
                val wrong = value.substring(value.indexOf("\t") + 1).toInt()
                val word = Word(key, hanzi, pinyin, translations, correct, wrong, if (correct == 0 && wrong == 0) 0.0 else if (wrong == 0) 1.0 else correct / (correct + wrong).toDouble())
                word.selected = true
                myWordsTree.addItem(word)
            }
        }

        // 랜덤 섞기
        myWordsTree.shuffle()

        val adapter = WordAdapter(this)
        binding.adapter = adapter
        adapter.allWordsTree = allWordsTree
        adapter.myWordsTree = myWordsTree
        adapter.notifyDataSetChanged()

        // 리스너 등록
        addOnClickListeners()

        // Side Drawer 등록
        btnDrawer.setOnClickListener {
            if (!drawer.isDrawerOpen(Gravity.START)) {
                drawer.openDrawer(Gravity.START)
            } else {
                drawer.closeDrawer(Gravity.START)
            }
        }

        // 게임 시작
        if (myWordsTree.allList.size >= 4) runGame(myWordsTree)
        else {
            llEmptyWarning.isVisible = true
            clQuestion.isVisible = false
            btnNotKnowing.isVisible = false
        }
    }

    private fun loadAd(adView: AdView) {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    fun runGame(wordTree: WordTree) {
        if (wordTree.allList.size >= 4) {
            llEmptyWarning.isVisible = false
            clQuestion.isVisible = true
            btnNotKnowing.isVisible = true

            processGame(myWordsTree)
        }
        else {
            llEmptyWarning.isVisible = true
            clQuestion.isVisible = false
            btnNotKnowing.isVisible = false
        }
    }

    fun processGame(wordTree: WordTree) {
        when (getRandomNumber1toN(8)) {
            1 -> {
                // 문제: 한자, 답: 발음
                setQuestionType(isText = true)
                val word = wordTree.next()
                currentWord = word
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

                val set = HashSet<Word>()
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
                currentWord = word
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

                val set = HashSet<Word>()
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
                currentWord = word
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

                val set = HashSet<Word>()
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
                currentWord = word
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

                val set = HashSet<Word>()
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
                currentWord = word
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

                val set = HashSet<Word>()
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
                currentWord = word
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

                val set = HashSet<Word>()
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
                currentWord = word
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

                val set = HashSet<Word>()
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
                currentWord = word
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

                val set = HashSet<Word>()
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

    private val onClickListener = View.OnClickListener{
        toggleAnswersClickable(false)
        setllQuestionOnClick {}

        val clickedView: Int = when (it) {
            llAnswer1 -> 1
            llAnswer2 -> 2
            llAnswer3 -> 3
            llAnswer4 -> 4
            else -> 5
        }

        when (answerLocation) {
            1 -> llAnswer1.setBackgroundResource(R.color.color_5ec8df)
            2 -> llAnswer2.setBackgroundResource(R.color.color_5ec8df)
            3 -> llAnswer3.setBackgroundResource(R.color.color_5ec8df)
            4 -> llAnswer4.setBackgroundResource(R.color.color_5ec8df)
        }

        if (answerLocation == clickedView) {
            ivCorrect.visibility = View.VISIBLE
            ivIncorrect.visibility = View.INVISIBLE
            markCorrect(currentWord)
        } else {
            ivCorrect.visibility = View.INVISIBLE
            ivIncorrect.visibility = View.VISIBLE
            markWrong(currentWord)

            val intent = Intent(this, PracticeActivity::class.java)
            intent.putExtra("word", currentWord)
            startActivity(intent)
        }

        val handler = Handler()
        handler.postDelayed(Runnable {
            ivCorrect.visibility = View.INVISIBLE
            ivIncorrect.visibility = View.INVISIBLE
            llAnswer1.setBackgroundResource(R.color.white)
            llAnswer2.setBackgroundResource(R.color.white)
            llAnswer3.setBackgroundResource(R.color.white)
            llAnswer4.setBackgroundResource(R.color.white)

            runGame(myWordsTree)
            toggleAnswersClickable(true)
        }, 1000)
    }

    private fun addOnClickListeners() {
        llAnswer1.setOnClickListener(onClickListener)
        llAnswer2.setOnClickListener(onClickListener)
        llAnswer3.setOnClickListener(onClickListener)
        llAnswer4.setOnClickListener(onClickListener)
        btnNotKnowing.setOnClickListener(onClickListener)
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
            btnNotKnowing.isClickable = true
        } else {
            llAnswer1.isClickable = false
            llAnswer2.isClickable = false
            llAnswer3.isClickable = false
            llAnswer4.isClickable = false
            btnNotKnowing.isClickable = false
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

    private fun getRandomWord(word: Word): Word {
        return when (word.hanzi.length) {
            1 -> {
                if (myWordsTree.length1List.size < 4) myWordsTree.allList[getRandomNumber1toN(myWordsTree.allList.size) - 1]
                else myWordsTree.length1List[getRandomNumber1toN(myWordsTree.length1List.size) - 1]
            }
            2 -> {
                if (myWordsTree.length2List.size < 4) myWordsTree.allList[getRandomNumber1toN(myWordsTree.allList.size) - 1]
                else myWordsTree.length2List[getRandomNumber1toN(myWordsTree.length2List.size) - 1]
            }
            3 -> {
                if (myWordsTree.length3List.size < 4) myWordsTree.allList[getRandomNumber1toN(myWordsTree.allList.size) - 1]
                else myWordsTree.length3List[getRandomNumber1toN(myWordsTree.length3List.size) - 1]
            }
            4 -> {
                if (myWordsTree.length4List.size < 4) myWordsTree.allList[getRandomNumber1toN(myWordsTree.allList.size) - 1]
                else myWordsTree.length4List[getRandomNumber1toN(myWordsTree.length4List.size) - 1]
            }
            else -> {
                if (myWordsTree.length4List.size < 4) myWordsTree.allList[getRandomNumber1toN(myWordsTree.allList.size) - 1]
                else myWordsTree.length4List[getRandomNumber1toN(myWordsTree.length4List.size) - 1]
            }
        }
    }

    private fun getRandomWord(): Word {
        return myWordsTree.allList[getRandomNumber1toN(myWordsTree.allList.size) - 1]
    }

    private fun markCorrect(word: Word) {
        word.correct++
        if (word.wrong != 0) word.accurateRatio = word.correct / (word.correct + word.wrong).toDouble()
        pref.setWordAtMyWords(word.id, "${word.hanzi}@${word.pinyin}\n${word.translations}\r${word.correct}\t${word.wrong}")
    }

    private fun markWrong(word: Word) {
        word.wrong++
        if (word.correct != 0) word.accurateRatio = word.correct / (word.correct + word.wrong).toDouble()
        pref.setWordAtMyWords(word.id, "${word.hanzi}@${word.pinyin}\n${word.translations}\r${word.correct}\t${word.wrong}")
    }
}