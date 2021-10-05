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
            tvNumber.text = "${position + 1}"
            tvHanzi.text = wordTree.allList[position].hanzi
            //tvNumber.isSelected = wordTree.allList[position].selected
            //tvHanzi.isSelected = wordTree.allList[position].selected
            itemView.isSelected = wordTree.allList[position].selected

            itemView.setOnClickListener {
                if (itemView.isSelected) {
                    // 단어 제거

                    AppPreference.get().deleteWordFromMyWords(tvHanzi.text.toString())

                    myWordsTree.deleteItem(tvHanzi.text.toString())
                } else {
                    // 단어 추가

                    val pinyin = allWordsTree.allList[position].pinyin
                    val translations = allWordsTree.allList[position].translations
                    AppPreference.get().setWordAtMyWords(tvHanzi.text.toString(), "${pinyin}\n${translations}\r${0}\t${0}")

                    myWordsTree.addItem(allWordsTree.allList[position])
                    myWordsTree.shuffle()
                }
                itemView.isSelected = !itemView.isSelected
                (context as MainActivity).runGame(myWordsTree)
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
        return allWordsTree.allList.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        Log.d("asdf", "onBindViewHolder")
        holder.bind(allWordsTree, position)
    }
}