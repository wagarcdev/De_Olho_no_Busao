package com.wagarcdev.deolhonobusao.domain.model

data class Line(
    var id: Int,
    var isCircular: Boolean,
    var lineLetters: String,
    var lineNumbers: Int,
    var direction: Int,
    var toMain: String,
    var toSecondary: String
)
