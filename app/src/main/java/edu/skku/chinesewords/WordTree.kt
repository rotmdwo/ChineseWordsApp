package edu.skku.chinesewords

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class WordTree {
    val allList = ArrayList<Word>()
    val questionList = ArrayList<Word>()
    val length1List = ArrayList<Word>()
    val length2List = ArrayList<Word>()
    val length3List = ArrayList<Word>()
    val length4List = ArrayList<Word>()

    var currentIndex = 0

    fun shuffle() {
        questionList.shuffle()
    }

    fun addItem(word: Word) {
        allList.add(word)

        when ((word.accurateRatio * 100).toInt()) {
            in 0..24 -> {
                for (i in 1..4) questionList.add(word)
            }
            in 25..49 -> {
                for (i in 1..3) questionList.add(word)
            }
            in 50..74 -> {
                questionList.add(word)
                questionList.add(word)
            }
            in 75..100 -> {
                questionList.add(word)
            }
        }

        when (word.hanzi.length) {
            1 -> length1List.add(word)
            2 -> length2List.add(word)
            3 -> length3List.add(word)
            4 -> length4List.add(word)
            else -> length4List.add(word)
        }
    }

    fun deleteItem(id: String, hanzi: String) {
        for (i in 0 until allList.size) {
            if (allList[i].id == id) {
                allList.removeAt(i)
                break
            }
        }

        when (hanzi.length) {
            1 -> {
                for (i in 0 until length1List.size) {
                    if (length1List[i].id == id) {
                        length1List.removeAt(i)
                        break
                    }
                }
            }
            2 -> {
                for (i in 0 until length2List.size) {
                    if (length2List[i].id == id) {
                        length2List.removeAt(i)
                        break
                    }
                }
            }
            3 -> {
                for (i in 0 until length3List.size) {
                    if (length3List[i].id == id) {
                        length3List.removeAt(i)
                        break
                    }
                }
            }
            4 -> {
                for (i in 0 until length4List.size) {
                    if (length4List[i].id == id) {
                        length4List.removeAt(i)
                        break
                    }
                }
            }
            else -> {
                for (i in 0 until length4List.size) {
                    if (length4List[i].id == id) {
                        length4List.removeAt(i)
                        break
                    }
                }
            }
        }

        var i = 0
        while (i < questionList.size) {
            if (questionList[i].id == id) questionList.removeAt(i)
            else i++
        }
    }

    fun next(): Word {
        if (currentIndex >= questionList.size) currentIndex = 0

        return questionList[currentIndex++]
    }

    class Word(val id: String, val hanzi: String, val pinyin: String, val translations: String, var correct: Int, var wrong: Int, var accurateRatio: Double) : Parcelable {
        var selected = false

        constructor(parcel: Parcel) : this(
                parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readInt(),
            parcel.readInt(),
            parcel.readDouble()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(hanzi)
            parcel.writeString(pinyin)
            parcel.writeString(translations)
            parcel.writeInt(correct)
            parcel.writeInt(wrong)
            parcel.writeDouble(accurateRatio)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Word> {
            override fun createFromParcel(parcel: Parcel): Word {
                return Word(parcel)
            }

            override fun newArray(size: Int): Array<Word?> {
                return arrayOfNulls(size)
            }
        }

    }
}