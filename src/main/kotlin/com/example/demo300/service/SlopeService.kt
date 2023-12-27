package com.example.demo300.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.example.demo300.model.YongPyongSlope
import com.example.demo300.repository.SlopeRepository
import org.springframework.transaction.annotation.Transactional

@Service
class SlopeService {
    // insert, update are successful
    // If slope information is not in the browser -> Send a Delete query to the DB to remove it.

    @Autowired
    private lateinit var slopeRepository: SlopeRepository
    private var init = true

    // Map object to store the presence of slopes
    // Created every time? -> No. The problem is that it is not refreshable! -> Set as a reference variable
    private lateinit var lst: List<YongPyongSlope>
    private lateinit var isSlopeInlist: MutableMap<String, Boolean>

    // 1. Initialize the DB for the first time + Save to the DB (insert)
    fun setSlope(slope: YongPyongSlope) {
        slopeRepository.save(slope)
    }

    // 2. Update the DB (delete and update)
    @Transactional
    fun update(slope: YongPyongSlope) {

        // Only works on the first run <- Initialize all Map values to false
        if (init) {
            initMap()
        }

        // Is the slope found in the browser in the DB?
        isSlopein(slope.slopeName)

        // Update the slope <- Use Dirty checking (update)
        val slopeName = slope.slopeName
        try {
            // Update using Dirty checking
            val origSlope = slopeRepository.findBySlopeName(slopeName)
            origSlope!!.주간 = slope.주간
            origSlope!!.야간 = slope.야간
            origSlope!!.심야 = slope.심야
            origSlope!!.비고 = slope.비고
        } catch (e: Exception) {
            // If adding a new slope -> Exception occurs
            // insert query occurs

            // Register in the Map when inserting a new slope, it will be removed later.
            isSlopeInlist[slope.slopeName] = true
            slopeRepository.save(slope)
        }
    }

    // 3. Initialize the Map
    private fun initMap() {
        lst = slopeRepository.findAll()
        isSlopeInlist = mutableMapOf()

        for (y in lst) {
            isSlopeInlist[y.slopeName] = false // If the slope name is the same as in the browser -> set to true
        }

        init = false
    }

    // 3-1. Is there a slope?
    private fun isSlopein(s: String) {
        if (isSlopeInlist[s] != null) {
            // If the data exists -> set to true
            isSlopeInlist.replace(s, true)
        }
    }

    // 3-2. Delete only slopes that do not exist.
    // When? -> After the insert, update process is completed
    @Transactional
    fun deleteSlope() {
        for (key in isSlopeInlist.keys) {
            val value = isSlopeInlist[key]
            if (value == false) {
                // Delete it.
                slopeRepository.deleteBySlopeName(key)
            }
        }
        // Initialize for the next cycle (1 day later).
        init = true
    }
}