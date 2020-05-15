package com.hobbydobby.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.*
import kotlin.experimental.and

object EncryptUtil {
    fun encryptSHA512(target: String): String {

        val hashCodeBuffer = StringBuffer()
        try {
            val md = MessageDigest.getInstance("SHA-512")
            md.update(target.toByteArray())
            val byteData = md.digest()
            for (i in byteData.indices) {
                hashCodeBuffer.append(Integer.toString((byteData[i] and 0xff.toByte()) + 0x100, 16).substring(1))
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return hashCodeBuffer.toString()
    }

    fun getSalt(): String {
        val random = SecureRandom.getInstance("SHA1PRNG")
        val bytes = byteArrayOf(16)
        random.nextBytes(bytes)
        val salt = String(Base64.getEncoder().encode(bytes))
        return salt
    }

}

fun main() {
    EncryptUtil.encryptSHA512("dddd")
}