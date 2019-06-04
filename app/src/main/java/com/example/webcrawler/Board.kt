package com.example.webcrawler

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_board.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import java.io.IOException

// ?
class Board : AppCompatActivity() {
    var list:ArrayList<ItemObject> = ArrayList()
    var boardData:ArrayList<BoardData> = ArrayList() // 공지사항
    var boardData2:ArrayList<BoardData> = ArrayList() // 취업공지
    var boardData3:ArrayList<BoardData> = ArrayList() // 공지사항
    var boardData4:ArrayList<BoardData> = ArrayList() // 특강공지

    val addr1 = "http://home.konkuk.ac.kr/cms/Site/ControlReader/MiniBoardList/miniboard_list_etype_ku_board.jsp?siteId=im&menuId=11892&menuId=11896&forumId=11914&forumId=18240&titleImg=/cms/Site/UserFiles/Image/internet/main_notice_title.gif&tabImg=/cms/Site/UserFiles/Image/internet/main_notice_tab0&rowsNum=6&moreImg=/cms/Site/UserFiles/Image/internet/btn_more.gif"
    val addr2 = "http://home.konkuk.ac.kr/cms/Site/ControlReader/MiniBoardList/miniboard_list_etype_ku_board.jsp?siteId=im&menuId=3266851&menuId=12351727&forumId=12368452&forumId=12368521&titleImg=/cms/Site/UserFiles/Image/internet/main_board_title.gif&tabImg=/cms/Site/UserFiles/Image/internet/main_board_tab0&rowsNum=6&moreImg=/cms/Site/UserFiles/Image/internet/btn_more.gif"

    val linkStr = "http://home.konkuk.ac.kr:80/cms/Common/MessageBoard/ArticleRead.do?forum=11914&id=" // 뒤에 아이디값 추가하면 접속됨
    val linkStr2 = "http://home.konkuk.ac.kr:80/cms/Common/MessageBoard/ArticleRead.do?forum="

    // KU 행정실 공지사항
    val id1 = 11914 // 공지사항
    val id2 = 18240 // 취업공지
    // SEOUL ACCORD
    val id3 = 12368452 // 공지사항
    val id4 = 12368521 // 특강공지

    lateinit var adapter1Board: BoardDataAdapter
    lateinit var adapter2Board: BoardDataAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        init()
        Log.i("on", "onCreate()")
        //adapterClick()
    }

    fun init() {
        // 데이터 web에서 읽어와서 내부 자료구조(ArrayList)에 저장
        doParse().execute()
        doParse2().execute()


        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerview.layoutManager = layoutManager

        val layoutManager2 = LinearLayoutManager(applicationContext)
        recyclerview2.layoutManager = layoutManager2

        adapter1Board = BoardDataAdapter(boardData)
        recyclerview.adapter = adapter1Board

        adapter2Board = BoardDataAdapter(boardData2)
        recyclerview2.adapter = adapter2Board

        spinner_board.onItemSelectedListener = SpinnerSelectedListener()

    }


    // 각 메뉴에 맞는 게시판으로 연결
    fun txt_moreClick(view: View) {
        var url = ""

        when (view.id) {
            R.id.txt_more1 -> {
                if (spinner_board.selectedItem.toString() == "KU 행정실 공지사항") {
                    url = linkStr2 + id1 // 행정실 공지
                } else {
                    url = linkStr2 + id3 // 서울어코드 공지
                }
            }

            R.id.txt_more2 -> {
                if (spinner_board.selectedItem.toString() == "KU 행정실 공지사항") {
                    url = linkStr2 + id2 // 취업공지
                } else {
                    url = linkStr2 + id4 // 특강공지
                }
            }
        }

        val i = Intent(applicationContext, Board_more::class.java)
        i.putExtra("url", url)
        startActivity(i)
    }


    // adapter listener
    fun adapterClick() {
        // recyclerview가 아니라 adapter..
        adapter1Board.itemClickListener = object: BoardDataAdapter.OnItemClickListener {
            override fun OnItemClick(holder: BoardDataAdapter.ViewHolder, view: View, data: BoardData, position: Int) {
//                Toast.makeText(applicationContext, data.dId, Toast.LENGTH_SHORT).show()
//                Log.v("test1", data.dId)

                val i = Intent(applicationContext, Board_detail::class.java)
                i.putExtra("menuId", data.dId)
                startActivity(i)

            }
        }
        recyclerview.adapter = adapter1Board

        adapter2Board.itemClickListener = object: BoardDataAdapter.OnItemClickListener {
            override fun OnItemClick(holder: BoardDataAdapter.ViewHolder, view: View, data: BoardData, position: Int) {
//                Toast.makeText(applicationContext, data.dId, Toast.LENGTH_SHORT).show()
//                Log.v("test2", data.dId)

                val i = Intent(applicationContext, Board_detail::class.java)
                i.putExtra("menuId", data.dId)
                startActivity(i)
            }
        }
        recyclerview2.adapter = adapter2Board
    }


    // spinner 선택되어있는데 안뜨는 문제 해결: 네트워크 문제임
   inner class SpinnerSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            Toast.makeText(parent?.context, parent?.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()

            // spinner_board.selectedItem.toString()
            if (parent?.getItemAtPosition(position).toString() == "KU 행정실 공지사항") {
                adapter1Board = BoardDataAdapter(boardData)
                recyclerview.adapter = adapter1Board
                adapter1Board.notifyDataSetChanged()

                adapter2Board = BoardDataAdapter(boardData2)
                recyclerview2.adapter = adapter2Board
                adapter2Board.notifyDataSetChanged()
                txt_recyclerView2.text = "취업공지"
                txt_recyclerView1.setBackgroundResource(R.drawable.bg_txt1)
                txt_recyclerView2.setBackgroundResource(R.drawable.bg_txt1)

                adapterClick()
            } else {
                adapter1Board = BoardDataAdapter(boardData3)
                recyclerview.adapter = adapter1Board
                adapter1Board.notifyDataSetChanged()

                adapter2Board = BoardDataAdapter(boardData4)
                recyclerview2.adapter = adapter2Board
                adapter2Board.notifyDataSetChanged()
                txt_recyclerView2.text = "특강공지"
                txt_recyclerView1.setBackgroundResource(R.drawable.bg_txt2)
                txt_recyclerView2.setBackgroundResource(R.drawable.bg_txt2)

                adapterClick()
            }

        }

    }


    inner class doParse : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            try {
                // KU 행정실 공지사항
                val doc1 = Jsoup.connect(addr1).get()
                // Seoul Accord
                val doc2 = Jsoup.connect(addr2).get()

                val entries1 = doc1.select("div#board1").select("dl")
                val entries2 = doc2.select("div#board1").select("dl")
//                val eSize = entries1.size // 공지사항 개수
//                Log.v("table", eSize.toString()) // 6개로 잘 받아짐

                // 공지사항
                for (e in entries1) {
                    val title = e.select("dt").text()
                    val date = e.select("dd").text()
                    val hrefStr = e.select("dt a").attr("href")
                    val hrefSize = hrefStr.length
                    val menuId = hrefStr.substring(hrefSize-10 .. hrefSize-3) // 각 페이지 링크 접속하게 해주는 id 추출
//                    Log.v("entry", title)
//                    Log.v("entry", date)
//                    Log.v("menuId", menuId)

                    boardData.add(BoardData(menuId, title, date))
                }

                // 취업공지
                for (e in entries2) {
                    val title = e.select("dt").text()
                    val date = e.select("dd").text()
                    val hrefStr = e.select("dt a").attr("href")
                    val hrefSize = hrefStr.length
                    val menuId = hrefStr.substring(hrefSize-10 .. hrefSize-3)

                    boardData2.add(BoardData(menuId, title, date))
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            //ArraList를 인자로 해서 어답터와 연결한다.
//                var adapter1 = BoardDataAdapter(boardData)
//                val layoutManager = LinearLayoutManager(applicationContext)
//
//                recyclerview.layoutManager = layoutManager
//                recyclerview.adapter = adapter1
        }
    }

    
   inner class doParse2 : AsyncTask<Void, Void, Void>() {
       override fun doInBackground(vararg params: Void): Void? {
           try {
               // KU 행정실 공지사항
               val doc1 = Jsoup.connect(addr1).get()
               // Seoul Accord
               val doc2 = Jsoup.connect(addr2).get()

               val entries3 = doc1.select("div#board2").select("dl")
               val entries4 = doc2.select("div#board2").select("dl")
//               val eSize = entries3.size // 공지사항 개수
//               Log.v("table", eSize.toString()) // 6개로 잘 받아짐

               for (e in entries3) {
                   val title = e.select("dt").text()
                   val date = e.select("dd").text()
                   val hrefStr = e.select("dt a").attr("href")
                   val hrefSize = hrefStr.length
                   val menuId = hrefStr.substring(hrefSize-10 .. hrefSize-3)

                   boardData3.add(BoardData(menuId, title, date))
               }

               for (e in entries4) {
                   val title = e.select("dt").text()
                   val date = e.select("dd").text()
                   val hrefStr = e.select("dt a").attr("href")
                   val hrefSize = hrefStr.length
                   val menuId = hrefStr.substring(hrefSize-9 .. hrefSize-3) // 이것만 7개

                   boardData4.add(BoardData(menuId, title, date))
               }



           } catch (e: IOException) {
               e.printStackTrace()
           }

           return null
       }

       override fun onPostExecute(result: Void?) {
//           var adapter2 = BoardDataAdapter(boardData3)
//           val layoutManager = LinearLayoutManager(applicationContext)
//
//           recyclerview2.layoutManager = layoutManager
//           recyclerview2.adapter = adapter2
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
