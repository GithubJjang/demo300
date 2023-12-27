package com.example.demo300.config.crolling

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import com.example.demo300.model.YongPyongSlope
import com.example.demo300.service.SlopeService
import java.io.IOException

@Component
class YongPyong {

    private var status = false // 첫 create시에만, true를 사용

    @Autowired
    private lateinit var slopeService: SlopeService

    @Scheduled(fixedDelay = 20000)
    @Throws(IOException::class)
    fun crollingFunc1() {

        val d: Document = Jsoup.connect("https://www.yongpyong.co.kr/kor/skiNboard/slope/openStatusBoard.do").get()

        // 2. slopeStatus 부분 추출하기
        val tbody: Element? = d.select("tbody#slopeStatus").first()

        // 3. slopeStatus 내 tr태그들 추출하기(이름/주간/야간/심야/비고 정보 담고있음)
        val trElements: Elements = tbody!!.select("tr")

        // 4. 각종 변수들 선언
        var Difficulty: Element? = null

        var slopeName: String
        var 주간: String
        var 야간: String
        var 심야: String
        var 비고: String
        var difficulty: String =" "

        val dataBox = ArrayList<String>()

        for (e in trElements) {

            //전체 내용 확인
            //System.out.println(e.text());

            //난이도 추출
            if (e.select("th").first() != null) {
                Difficulty = e.select("th").first()
                difficulty = Difficulty!!.text()
            }
            // 슬로프 이름 추출하기.
            //Element slopeNameElement = e.select("td.conLeft").first();
            //slopeName = slopeNameElement.text();
            //System.out.println(slopeName);
            //System.out.println(difficulty);

            // 1. 한줄에 기본정보(이름,주간,야간,심야,비고)에다가 난이도까지 있는 것을 특수케이스로 취급을 하자.
            if (e.select("td[class='']").first() != null) {
                val slopeNameElement: Element = e.select("td.conLeft").first()!!
                slopeName = slopeNameElement.text()

                val tdElements: Elements = e.select("td[class='']")
                //System.out.println(slopeName);
                //System.out.println(difficulty);
                for (td in tdElements) {
                    val tdContent: String = td.text()
                    //System.out.print(tdContent);
                    //System.out.println();
                    // 주간/야간/심야/비고 순으로
                    dataBox.add(tdContent)
                }
                // 마지막 비고가 빈 경우는 빈문자 ""임.
                //System.out.println("크기:"+dataBox.size());
                //System.out.println();
                주간 = dataBox[0]
                야간 = dataBox[1]
                심야 = dataBox[2]
                비고 = dataBox[3]
                val slope = YongPyongSlope(slopeName, 주간, 야간, 심야, 비고, difficulty)
                if (status) {

                    slopeService.setSlope(slope)
                } else {
                    // 더티체킹을 통해서 변경사항을 update한다.
                    slopeService.update(slope)
                }
                dataBox.clear()
            }

            // 따로 특이케이스로 처리
            if (e.select("td.lineBold").first() != null) {

                val tsElements: Elements = e.select("td.lineBold")
                for (ts in tsElements) {
                    val tsContent: String = ts.text()
                    //System.out.print(tsContent);
                    //System.out.println();
                    // 이름/주간/야간/심야/비고  순으로
                    dataBox.add(tsContent)

                }
                slopeName = dataBox[0]
                주간 = dataBox[1]
                야간 = dataBox[2]
                심야 = dataBox[3]
                비고 = dataBox[4]
                val slope = YongPyongSlope(slopeName, 주간, 야간, 심야, 비고, difficulty)
                if (status) {
                    // 처음으로 초기화를 할 경우 -> Insert
                    slopeService.setSlope(slope)
                } else {
                    // 더티체킹을 통해서 변경사항을 update한다. -> Update
                    slopeService.update(slope)
                }
                dataBox.clear()
                //System.out.println();
            }
        }
        status = false
        slopeService.deleteSlope()
    }
}