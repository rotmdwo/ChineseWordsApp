package edu.skku.chinesewords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import edu.skku.chinesewords.WordTree.Word
import edu.skku.chinesewords.databinding.ActivityPracticeBinding

class PracticeActivity : AppCompatActivity() {

    lateinit var binding: ActivityPracticeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_practice)

        val word: Word? = intent.getParcelableExtra("word")

        if (word == null) finish()
        else {
            // 정상 실행

            binding.word = word
            binding.view = this
        }
    }

    fun closeButtonClicked() {
        finish()
    }
}