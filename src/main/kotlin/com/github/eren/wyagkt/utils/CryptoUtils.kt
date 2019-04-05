package com.github.eren.wyagkt.utils

import org.apache.commons.codec.digest.DigestUtils

/**
 * Get SHA1 sum of given string
 *
 * @param content Content to get sha1sum of
 * @return Hexdigest of the string
 */
fun sha1sum(content: String) : String {
    return DigestUtils.sha1Hex(content)
}