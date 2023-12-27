package com.example.demo300.repository

import com.example.demo300.model.YongPyongSlope
import org.springframework.data.jpa.repository.JpaRepository

interface SlopeRepository : JpaRepository<YongPyongSlope, Int> {
    fun findBySlopeName(slopeName: String): YongPyongSlope?
    fun deleteBySlopeName(slopeName: String)

}
