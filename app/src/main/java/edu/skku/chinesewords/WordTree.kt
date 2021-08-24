package edu.skku.chinesewords

import java.util.*

class WordTree {
    val allList = ArrayList<Word>()
    val length1List = ArrayList<Word>()
    val length2List = ArrayList<Word>()
    val length3List = ArrayList<Word>()
    val length4List = ArrayList<Word>()

    var currentIndex = 0

    fun shuffle() {
        allList.shuffle()
    }

    fun addItem(word: Word) {
        allList.add(word)

        when (word.hanzi.length) {
            1 -> length1List.add(word)
            2 -> length2List.add(word)
            3 -> length3List.add(word)
            4 -> length4List.add(word)
            else -> length4List.add(word)
        }
    }

    fun next(): Word {
        if (currentIndex >= allList.size) currentIndex = 0

        return allList[currentIndex++]
    }

    class Word(val hanzi: String, val pinyin: String, val translations: String)
}