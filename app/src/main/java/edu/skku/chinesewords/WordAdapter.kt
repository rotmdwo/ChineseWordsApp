package edu.skku.chinesewords

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordAdapter(val context: Context) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    var allWordsTree: WordTree = WordTree()
    var myWordsTree: WordTree = WordTree()

    inner class WordViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvNumber = itemView.findViewById<TextView>(R.id.tvNumber)
        val tvHanzi = itemView.findViewById<TextView>(R.id.tvHanzi)

        fun bind(wordTree: WordTree, position: Int) {
            when (position) {
                0 -> {
                    // HSK 1

                    tvNumber.text = "HSK 1급"
                    tvHanzi.text = ""

                    // TODO: itemView.isSelected 값 pref에서 가져오기

                    // TODO: setOnClickListener 구현

                }
                (1 + 150) -> {
                    // HSK 2

                    tvNumber.text = "HSK 2급"
                    tvHanzi.text = ""

                    // TODO: itemView.isSelected 값 pref에서 가져오기

                    // TODO: setOnClickListener 구현

                }
                (2 + 300) -> {
                    // HSK 3

                    tvNumber.text = "HSK 3급"
                    tvHanzi.text = ""

                    // TODO: itemView.isSelected 값 pref에서 가져오기

                    // TODO: setOnClickListener 구현

                }
                (3 + 599) -> {
                    // HSK 4

                    tvNumber.text = "HSK 4급"
                    tvHanzi.text = ""

                    // TODO: itemView.isSelected 값 pref에서 가져오기

                    // TODO: setOnClickListener 구현

                }
                else -> {
                    /*
                     * 단어들
                     */

                    val editedPosition = when (position) {
                        in 1..150 -> {
                            position - 1
                        }
                        in (1 + 151)..(1 + 300) -> {
                            position - 2
                        }
                        in (2 + 301)..(2 + 599) -> {
                            position - 3
                        }
                        else -> {
                            position - 4
                        }
                    }

                    tvNumber.text = "${editedPosition + 1}"
                    tvHanzi.text = wordTree.allList[editedPosition].hanzi
                    itemView.isSelected = wordTree.allList[editedPosition].selected

                    itemView.setOnClickListener {
                        if (itemView.isSelected) {
                            // 단어 제거

                            AppPreference.get().deleteWordFromMyWords(tvHanzi.text.toString())

                            myWordsTree.deleteItem("${editedPosition + 1}", tvHanzi.text.toString())
                        } else {
                            // 단어 추가

                            val id = allWordsTree.allList[editedPosition].id
                            val pinyin = allWordsTree.allList[editedPosition].pinyin
                            val translations = allWordsTree.allList[editedPosition].translations
                            AppPreference.get().setWordAtMyWords(id, "${tvHanzi.text}@${pinyin}\n${translations}\r${0}\t${0}")

                            myWordsTree.addItem(allWordsTree.allList[editedPosition])
                            myWordsTree.shuffle()
                        }
                        itemView.isSelected = !itemView.isSelected
                        (context as MainActivity).runGame(myWordsTree)
                    }
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        Log.d("asdf", "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_word, parent, false)
        return WordViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("asdf", "getItemCount")
        return allWordsTree.allList.size + 4 // HSK 1~4
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        Log.d("asdf", "onBindViewHolder")
        holder.bind(allWordsTree, position)
    }
}