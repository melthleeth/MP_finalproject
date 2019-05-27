package com.example.webcrawler

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_board.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import android.support.v7.widget.LinearLayoutManager
import java.io.IOException


class Board : AppCompatActivity() {
    var list:ArrayList<ItemObject> = ArrayList()
    var data1:ArrayList<Data1> = ArrayList()
    var data2:ArrayList<Data1> = ArrayList()

    val addr1 = "http://home.konkuk.ac.kr/cms/Site/ControlReader/MiniBoardList/miniboard_list_etype_ku_board.jsp?siteId=im&menuId=11892&menuId=11896&forumId=11914&forumId=18240&titleImg=/cms/Site/UserFiles/Image/internet/main_notice_title.gif&tabImg=/cms/Site/UserFiles/Image/internet/main_notice_tab0&rowsNum=6&moreImg=/cms/Site/UserFiles/Image/internet/btn_more.gif"
    val addr2 = "http://home.konkuk.ac.kr/cms/Site/ControlReader/MiniBoardList/miniboard_list_etype_ku_board.jsp?siteId=im&menuId=3266851&menuId=12351727&forumId=12368452&forumId=12368521&titleImg=/cms/Site/UserFiles/Image/internet/main_board_title.gif&tabImg=/cms/Site/UserFiles/Image/internet/main_board_tab0&rowsNum=6&moreImg=/cms/Site/UserFiles/Image/internet/btn_more.gif"


//    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

//        sample()
//        Description().execute()
        doParse().execute()
        doParse2().execute()

    }



    fun sample() {
        val html =
            "<html><head><title>첫번째 예제입니다.</title></head>" +
                    "<body><h1>테스트</h1><p>간단히 HTML을 파싱해 보는 샘플예제입니다.</p></body></html>"

        val doc = Jsoup.parse(html)

        val title = doc.select("title")
        Log.d("result: ", "doc= $doc")
        Log.d("result: ", "title= $title")
    }

    inner class doParse : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            try {
                // KU 행정실 공지사항
                val doc1 = Jsoup.connect(addr1).get()
                // Seoul Accord
                val doc2 = Jsoup.connect(addr2).get()

                val entries1 = doc1.select("div#board1").select("dl")
                val entries2 = doc1.select("div#board2").select("dl")
                val entries3 = doc2.select("div#board1").select("dl")
                val entries4 = doc2.select("div#board2").select("dl")
                val eSize = entries1.size // 공지사항 개수
                Log.v("table", eSize.toString()) // 6개로 잘 받아짐

                for (e in entries1) {
                    val title = e.select("dt").text()
                    val date = e.select("dd").text()
//                    Log.v("entry", title)
//                    Log.v("entry", date)
                    data1.add(Data1(1, title, date))
                }

                for (e in entries2) {
                    val title = e.select("dt").text()
                    val date = e.select("dd").text()
//                    Log.v("entry", title)
//                    Log.v("entry", date)
                    data2.add(Data1(1, title, date))
                }

                for (e in entries3) {
                    val title = e.select("dt").text()
                    val date = e.select("dd").text()
//                    Log.v("entry", title)
//                    Log.v("entry", date)
                    data1.add(Data1(2, title, date))
                }

                for (e in entries4) {
                    val title = e.select("dt").text()
                    val date = e.select("dd").text()
//                    Log.v("entry", title)
//                    Log.v("entry", date)
                    data2.add(Data1(2, title, date))
                }



            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            //ArraList를 인자로 해서 어답터와 연결한다.
                var adapter1 = DataAdapter(data1)
                var adapter2 = DataAdapter(data2)
                val layoutManager = LinearLayoutManager(applicationContext)

                recyclerview.layoutManager = layoutManager
                //recyclerview2.layoutManager = layoutManager

                recyclerview.adapter = adapter1
                //recyclerview2.adapter = adapter2
        }
    }

    
   inner class doParse2 : AsyncTask<Void, Void, Void>() {
       override fun doInBackground(vararg params: Void): Void? {
           try {
               // KU 행정실 공지사항
               val doc1 = Jsoup.connect(addr1).get()
               // Seoul Accord
               val doc2 = Jsoup.connect(addr2).get()

               val entries1 = doc1.select("div#board1").select("dl")
               val entries2 = doc1.select("div#board2").select("dl")
               val entries3 = doc2.select("div#board1").select("dl")
               val entries4 = doc2.select("div#board2").select("dl")
               val eSize = entries1.size // 공지사항 개수
               Log.v("table", eSize.toString()) // 6개로 잘 받아짐

               for (e in entries1) {
                   val title = e.select("dt").text()
                   val date = e.select("dd").text()
//                    Log.v("entry", title)
//                    Log.v("entry", date)
                   data1.add(Data1(1, title, date))
               }

               for (e in entries2) {
                   val title = e.select("dt").text()
                   val date = e.select("dd").text()
//                    Log.v("entry", title)
//                    Log.v("entry", date)
                   data2.add(Data1(1, title, date))
               }

               for (e in entries3) {
                   val title = e.select("dt").text()
                   val date = e.select("dd").text()
//                    Log.v("entry", title)
//                    Log.v("entry", date)
                   data1.add(Data1(2, title, date))
               }

               for (e in entries4) {
                   val title = e.select("dt").text()
                   val date = e.select("dd").text()
//                    Log.v("entry", title)
//                    Log.v("entry", date)
                   data2.add(Data1(2, title, date))
               }



           } catch (e: IOException) {
               e.printStackTrace()
           }

           return null
       }

       override fun onPostExecute(result: Void?) {
           //ArraList를 인자로 해서 어답터와 연결한다.
           //var adapter1 = DataAdapter(data1)
           var adapter2 = DataAdapter(data2)
           val layoutManager = LinearLayoutManager(applicationContext)

           //recyclerview.layoutManager = layoutManager
           recyclerview2.layoutManager = layoutManager

           //recyclerview.adapter = adapter1
           recyclerview2.adapter = adapter2
       }

   }



    // 성공!!!
    inner class Description : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            try {
                val doc = Jsoup.connect("https://movie.naver.com/movie/running/current.nhn").get()

                val mElementDataSize = doc.select("ul[class=lst_detail_t1]").select("li") //필요한 부분 tag 지정
                val mElementSize = mElementDataSize.size //목록이 몇개인지 알아낸다.

                for (elem in mElementDataSize) {
                    //영화목록 <li> 에서 다시 원하는 데이터를 추출해 낸다.
                    val my_title = elem.select("li dt[class=tit] a").text()
                    val my_link = elem.select("li div[class=thumb] a").attr("href")
                    val my_imgUrl = elem.select("li div[class=thumb] a img").attr("src")
                    //특정하기 힘들다... 저 앞에 있는집의 오른쪽으로 두번째집의 건너집이 바로 우리집이야 하는 식이다.
                    val rElem = elem.select("dl[class=info_txt1] dt").next().first()
                    val my_release = rElem.select("dd").text()
                    val dElem = elem.select("dt[class=tit_t2]").next().first()
                    val my_director = "감독: " + dElem.select("a").text()
                    //Log.d("test", "test" + mTitle);
                    //ArrayList에 계속 추가한다.
                    list.add(ItemObject(my_title, my_imgUrl, my_link, my_release, my_director))
                    //Log.v("list", list[0].title)
                    //Log.v("list", list[0].img_url)
                    //Log.v("list", list[0].release)
                }

                //추출한 전체 <li> 출력
                //Log.d("debug :", "List $mElementDataSize")
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: Void?) { // 와씨... 이거 계속 ? 안해서 오류났던거였음
            //ArraList를 인자로 해서 어답터와 연결한다.
            if (list != null) {
                var adapter = MyAdapter(list)
                val layoutManager = LinearLayoutManager(applicationContext)
                recyclerview.layoutManager = layoutManager
                recyclerview.adapter = adapter
            }

        }
    }




    val wiki = "https://en.wikipedia.org"

    fun main() {
        val doc = Jsoup.connect("$wiki/wiki/List_of_films_with_a_100%25_rating_on_Rotten_Tomatoes").get()    // <1>
        doc.select(".wikitable:first-of-type tr td:first-of-type a")    // <2>
            .map { col -> col.attr("href") }    // <3>
            .parallelStream()    // <4>
            .map { extractMovieData(it) }    // <5>
            .filter { it != null }
            .forEach { println(it) }
    }

    fun extractMovieData(url: String): Movie? { // <1>
        val doc: Document
        try {
            doc = Jsoup.connect("$wiki$url").get()  // <2>
        }catch (e: Exception){
            return null
        }

        val movie = Movie() // <3>
        doc.select(".infobox tr")   // <4>
            .forEach { ele ->   // <5>
                when {
                    ele.getElementsByTag("th")?.hasClass("summary") ?: false -> {   // <6>
                        movie.title = ele.getElementsByTag("th")?.text()
                    }
                    else -> {
                        val value: String? = if (ele.getElementsByTag("li").size > 1)
                            ele.getElementsByTag("li")
                                .map(Element::text)
                                .filter(String::isNotEmpty)
                                .joinToString(", ") else
                            ele.getElementsByTag("td")?.first()?.text() // <7>

                        when (ele.getElementsByTag("th")?.first()?.text()) {    // <8>
                            "Directed by" -> movie.directedBy = value ?: ""
                            "Produced by" -> movie.producedBy = value ?: ""
                            "Written by" -> movie.writtenBy = value ?: ""
                            "Starring" -> movie.starring = value ?: ""
                            "Music by" -> movie.musicBy = value ?: ""
                            "Release date" -> movie.releaseDate = value ?: ""
                            "title" -> movie.title = value ?: ""
                        }
                    }
                }
            }
        return movie
    }


//    @Test
//    fun shouldParseHTML() {
//        //1. Fetching the HTML from a given URL
//        Jsoup.connect("https://www.google.co.in/search?q=this+is+a+test").get().run {
//            //2. Parses and scrapes the HTML response
//            select("div.rc").forEachIndexed { index, element ->
//                val titleAnchor = element.select("h3 a")
//                val title = titleAnchor.text()
//                val url = titleAnchor.attr("href")
//                //3. Dumping Search Index, Title and URL on the stdout.
//                println("$index. $title ($url)")
//
//                //txt1.text = index.toString() + "." + title.toString() + "(" + url.toString() + ")"
//
//            }
//        }
//    }
}
