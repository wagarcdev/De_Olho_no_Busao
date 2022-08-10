package com.wagarcdev.deolhonobusao.data

import com.wagarcdev.deolhonobusao.data.remote.responses.LineEntity
import com.wagarcdev.deolhonobusao.domain.model.Line

fun LineEntity.toLine(): Line {
    return Line(
        id = cl,
        isCircular = lc,
        lineLetters = lt,
        lineNumbers = sl,
        direction = tl,
        toMain = tp,
        toSecondary = ts
    )
}

fun Line.toLineEntity(): LineEntity {
    return LineEntity(
        cl = id,
        lc = isCircular,
        lt = lineLetters,
        sl = lineNumbers,
        tl = direction,
        tp = toMain,
        ts = toSecondary

    )
}