package io.github.sgpublic.aidescit.api.core.util

import java.util.*

fun Long.random(): String {
    val random = Random(this)
    return StringBuilder().apply {
        for (i in 0 until 8){
            append(Integer.toHexString(random.nextInt(16)))
        }
    }.toString()
}