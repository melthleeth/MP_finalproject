package com.example.webcrawler

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.jsoup.Jsoup
import java.io.IOException

class Board_more : AppCompatActivity() {

    lateinit var url:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

    }

    fun init() {
        val i = intent
        url = i.getStringExtra("url")
        Log.v("url", url)

        doParse().execute()
    }

    inner class doParse : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void): Void? {
            try {
                val doc = Jsoup.connect(url).get()
                val entries = doc.select("tbody").select("dl")



            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }


    }
}
