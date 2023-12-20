package com.example.demo300.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
class Slope {
    // 자동으로 넘버링을 한다.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column(name = "슬로프이름")
    var slopeName: String = ""

    var 주간: String = ""
    var 야간: String = ""
    var 심야: String = ""
    var 비고: String = ""

    @Column(name = "난이도")
    var difficulty: String = ""

    // 기본생성자는 항상 있어야 됨
    constructor()

    constructor(
        slopeName: String,
        주간: String,
        야간: String,
        심야: String,
        비고: String,
        difficulty: String
    ) {
        this.slopeName = slopeName
        this.주간 = 주간
        this.야간 = 야간
        this.심야 = 심야
        this.비고 = 비고
        this.difficulty = difficulty
    }
}
