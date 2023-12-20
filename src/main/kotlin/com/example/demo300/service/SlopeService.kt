package com.example.demo300.service

import com.example.demo300.model.Slope
import com.example.demo300.repository.SlopeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SlopeService {

    @Autowired
    private lateinit var slopeRepository: SlopeRepository

    fun updateSlope(slope: Slope) {
        slopeRepository.save(slope)
    }
}