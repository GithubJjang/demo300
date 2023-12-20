package com.example.demo300.config.crolling

import com.example.demo300.model.Slope
import com.example.demo300.service.SlopeService
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class JsoupCrolling {

    @Autowired
    private lateinit var slopeService: SlopeService

    @Scheduled(fixedDelay = 60000)
    fun crawlingFunc1() {
        try {
            // 1. Get HTML document
            val d: Document = Jsoup.connect("https://www.yongpyong.co.kr/kor/skiNboard/slope/openStatusBoard.do").get()

            // 2. Extract slopeStatus section
            val tbody: Element? = d.select("tbody#slopeStatus").first()

            // 3. Extract tr tags within slopeStatus (contains information like Name/Day/Night/Late Night/Remarks)
            val trElements: Elements = tbody!!.select("tr")

            // 4. Declare various variables
            var difficulty: String = ""
            var slopeName: String = ""
            var day: String = ""
            var night: String = ""
            var lateNight: String = ""
            var remarks: String = ""

            for (e: Element in trElements) {
                // Extract difficulty
                val difficultyElement: Element? = e.select("th").first()
                difficulty = difficultyElement?.text() ?: ""

                // 1. Special case: Information in one line with basic details (Name, Day, Night, Late Night, Remarks) including difficulty
                if (e.select("td[class='']").first() != null) {
                    val slopeNameElement: Element? = e.select("td.conLeft").first()
                    slopeName = slopeNameElement?.text() ?: ""

                    val tdElements: Elements = e.select("td[class='']")
                    println(slopeName)
                    println(difficulty)
                    for (td: Element in tdElements) {
                        val tdContent: String = td.text()
                        println(tdContent)
                        // Day/Night/Late Night/Remarks in order
                        when (tdElements.indexOf(td)) {
                            0 -> day = tdContent
                            1 -> night = tdContent
                            2 -> lateNight = tdContent
                            3 -> remarks = tdContent
                        }
                    }

                    // Last Remarks might be empty
                    // println("Size: ${dataBox.size}")
                    // println()
                    val slope = Slope(slopeName, day, night, lateNight, remarks, difficulty)
                    slopeService.updateSlope(slope)
                }

                // 2. Separate handling for special cases
                if (e.select("td.lineBold").first() != null) {
                    val tsElements: Elements = e.select("td.lineBold")
                    for (ts: Element in tsElements) {
                        val tsContent: String = ts.text()
                        println(tsContent)
                        // Name/Day/Night/Late Night/Remarks in order
                        when (tsElements.indexOf(ts)) {
                            0 -> slopeName = tsContent
                            1 -> day = tsContent
                            2 -> night = tsContent
                            3 -> lateNight = tsContent
                            4 -> remarks = tsContent
                        }
                    }
                    val slope = Slope(slopeName, day, night, lateNight, remarks, difficulty)
                    slopeService.updateSlope(slope)
                }
            }
        } catch (e: Exception) {
            // Handle exceptions as needed
            e.printStackTrace()
        }
    }
}