package com.example.demo300.service

import com.example.demo300.model.Slope
import com.example.demo300.repository.SlopeRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SlopeService {

    @Autowired
    private lateinit var slopeRepository: SlopeRepository

    fun setSlope(slope: Slope) {
        slopeRepository.save(slope)
    }

    @Transactional
    fun update(slope: Slope) {
        // 변경된 슬로프
        val slopeName: String = slope.slopeName

        @Transactional
        fun update(slope: Slope) {
            // 변경된 슬로프
            val slopeName = slope.slopeName

            val origSlope = slopeRepository.findBySlopeName(slopeName)
            origSlope?.apply {
                주간 = slope.주간
                야간 = slope.야간
                심야 = slope.심야
                비고 = slope.비고
            }
        }
    }
}