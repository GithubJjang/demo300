package com.example.demo300.repository

import com.example.demo300.model.Slope
import org.springframework.data.jpa.repository.JpaRepository

interface SlopeRepository : JpaRepository<Slope, Int> {
    fun findBySlopeName(slopeName: String): Slope?
}