package com.example.demo300.dummyController

import com.example.demo300.dummymodel.Resort
import com.example.demo300.dummymodel.Slope
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@Controller
class DummyUserController {

    @GetMapping("/resorts")
    fun getResorts(model: Model): String {
        //Client는 단순히 /resorts로 이동만 하면됨.

        // Server
        // Resort Entity를 반환한다 -> [PK,name]
        val resortEntity = Resort() // JPA를 통해서 가지고온 resort 엔티티
        model.addAttribute("resorts",resortEntity)
        // jsp의 <c:foreach> 구문처럼, 객체를 반복문을 이용해서 출력을 한다.
        return "/dummy/resorts/resorts.html"

    }

    /*
    @GetMapping("/resorts/{id}")
    fun getDetailResorts( @PathVariable(value = "id") id : String ): String{
        // Server
        // resorts의 PK를 파라미터로 넘겨받는다.
        return "/dummy/resorts/resorts_detail.html"
    }

    @GetMapping("/resorts/{id}/slopes")
    fun getSlopebyDay ( @PathVariable(value = "id") id : String,
                        @RequestParam(value = "day") @DateTimeFormat(pattern = "yyyy-MM-dd") localdate: LocalDate,
                        model: Model): String{
        // Server
        // resorts의 PK를 파라미터로 넘겨받는다.
        // 현재 날짜를 파라미터로 넘겨받는다.
        val year = localdate.year
        val month = localdate.monthValue
        val day = localdate.dayOfMonth

        println(year)
        println(month)
        println(day)

        // response
        // 1. 조회된 날짜를 넘겨준다.
        //model.addAttribute("year",year)
        //model.addAttribute("month",month)
        //model.addAttribute("day",day)

        // 2. Slope Entity를 넘겨준다.
        return "/dummy/slope/slope_detail.html"
    }


     */
}

@RestController
class DummyRestCon{
    @GetMapping("/resorts/{id}")
    fun getDetailResorts( @PathVariable(value = "id") id : String ): String{
        // Server
        // resorts의 PK를 파라미터로 넘겨받는다.
        val resort = Resort()
        val objectMapper = ObjectMapper()

        val jsonResort = objectMapper.writeValueAsString(resort)
        return jsonResort
    }

    @GetMapping("/resorts/{id}/slopes")
    fun getSlopebyDay ( @PathVariable(value = "id") id : String,
                        @RequestParam(value = "day") @DateTimeFormat(pattern = "yyyy-MM-dd") localdate: LocalDate,
                        model: Model): String{
        // Server
        // resorts의 PK를 파라미터로 넘겨받는다.
        // 현재 날짜를 파라미터로 넘겨받는다.
        val slope = Slope()
        val objectMapper = ObjectMapper()

        val jsonSlope = objectMapper.writeValueAsString(slope)
        // response
        // 1. 조회된 날짜를 넘겨준다.


        // 2. Slope Entity를 넘겨준다.
        return jsonSlope
    }

}

