package com.daking.lottery.util

import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


object EncryptUtils {

    var AES_MODE = "AES/ECB/PKCS5Padding"
    private val AES = "AES"
    private val MD5 = "MD5"
    private val hexDigits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')


    /**
     * 描述：MD5加密.
     *
     * @param str 要加密的字符串
     * @return String 加密的字符串
     */
    fun MD5(str: String): String? {
        try {
            val strTemp = str.toByteArray()
            val mdTemp = MessageDigest.getInstance(MD5)
            mdTemp.update(strTemp)
            val tmp = mdTemp.digest() // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            val strs = CharArray(16 * 2) // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            var k = 0 // 表示转换结果中对应的字符位置
            for (i in 0..15) { // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                val byte0 = tmp[i].toInt() // 取第 i 个字节
                strs[k++] = hexDigits[byte0.ushr(4) and 0xf] // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                strs[k++] = hexDigits[byte0 and 0xf] // 取字节中低 4 位的数字转换
            }
            return String(strs).toLowerCase() // 换后的结果转换为字符串
        } catch (e: Exception) {
            return null
        }
    }


    /**
     * AES加密
     *
     * @param content 明文
     * @param pass  秘钥
     * @return 密文
     */
    @Throws(Exception::class)
    fun encryptAES(content: String, pass: String): String {
        val aesECB = Cipher.getInstance(AES_MODE)
        val key = getSecretKeySpec(pass)
        aesECB.init(Cipher.ENCRYPT_MODE, key)
        val result = aesECB.doFinal(content.toByteArray())
        return parseByte2HexStr(result)
    }

    /**
     * AES解密
     *
     * @param content 密文
     * @param pass  秘钥
     * @return 明文
     */
    @Throws(Exception::class)
    fun decryptAES(content: String, pass: String): String {
        val cipher = Cipher.getInstance(AES_MODE)
        val key = getSecretKeySpec(pass)
        cipher.init(Cipher.DECRYPT_MODE, key)
        val result = parseHexStr2Byte(content)
        return String(cipher.doFinal(result))
    }

    @Throws(Exception::class)
    private fun getSecretKeySpec(key: String): SecretKeySpec {
        val arrBTmp = key.toByteArray()
        val arrB = ByteArray(16)
        var i = 0
        while (i < arrBTmp.size && i < arrB.size) {
            arrB[i] = arrBTmp[i]
            i++
        }
        return SecretKeySpec(arrB, AES)
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    private fun parseByte2HexStr(buf: ByteArray): String {
        val sb = StringBuilder()
        for (i in buf.indices) {
            var hex = Integer.toHexString(buf[i].toInt() and 0xFF)
            if (hex.length == 1) {
                hex = '0' + hex
            }
            sb.append(hex.toUpperCase())
        }
        return sb.toString()
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    private fun parseHexStr2Byte(hexStr: String): ByteArray? {
        if (hexStr.isEmpty())
            return null
        val result = ByteArray(hexStr.length / 2)
        for (i in 0 until hexStr.length / 2) {
            val high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16)
            val low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16)
            result[i] = (high * 16 + low).toByte()
        }
        return result
    }
}