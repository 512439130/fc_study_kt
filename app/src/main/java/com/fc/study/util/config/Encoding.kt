package com.fc.study.util.config

import java.nio.charset.Charset

@Suppress("unused", "MemberVisibilityCanBePrivate")
object Encoding {
    const val ISO_8859_1 = "ISO-8859-1"
    const val US_ASCII = "US-ASCII"
    const val UTF_16 = "UTF-16"
    const val UTF_16BE = "UTF-16BE"
    const val UTF_16LE = "UTF-16LE"
    const val UTF_8 = "UTF-8"
    const val GB2312 = "GB2312"

    val CHARSET_ISO_8859_1: Charset = Charset.forName(ISO_8859_1)
    val CHARSET_US_ASCII: Charset = Charset.forName(US_ASCII)
    val CHARSET_UTF_16: Charset = Charset.forName(UTF_16)
    val CHARSET_UTF_16BE: Charset = Charset.forName(UTF_16BE)
    val CHARSET_UTF_16LE: Charset = Charset.forName(UTF_16LE)
    val CHARSET_UTF_8: Charset = Charset.forName(UTF_8)
    val CHARSET_GB2312: Charset = Charset.forName(GB2312)
}