package edu.skku.chinesewords

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            starter.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var text = ""
        val pref = AppPreference.get()
        val hsk1Words = pref.getAllFromHSK1()
        hsk1Words.forEach { key, value ->
            text += "$key $value\n"
        }
    }
}