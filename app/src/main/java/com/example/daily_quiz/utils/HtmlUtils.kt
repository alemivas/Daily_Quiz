package com.example.daily_quiz.utils

import org.apache.commons.text.StringEscapeUtils

fun String.cleanHtml(): String {
    return StringEscapeUtils.unescapeHtml4(this)
}