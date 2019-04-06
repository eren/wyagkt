package com.github.eren.wyagkt.utils

import org.apache.commons.codec.digest.DigestUtils

val String.sha1sum: String
    get() = DigestUtils.sha1Hex(this)