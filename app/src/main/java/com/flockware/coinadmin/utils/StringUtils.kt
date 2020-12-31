package com.flockware.coinadmin.utils

import java.math.BigInteger
import java.security.MessageDigest

fun String.getSHA512(): String {
    val md = MessageDigest.getInstance("SHA-512").digest(this.toByteArray())

    // convert byte array to signum representation
    // and then into hex value
    var hashtext = BigInteger(1, md).toString(16)

    // add preceding 0s to make it 32 bit
    while (hashtext.length < 32) {
        hashtext = "0${hashtext}"
    }

    return hashtext
}