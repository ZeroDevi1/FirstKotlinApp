package com.zerodevi1.tools

import android.content.Context

object AesTools {

    /**
     * AES解密, CBC, PKCS5Padding
     */
    external fun method02(context: Context): Int

    /**
     * CRC16校验
     */
    external fun crc16(context: Context, data: String): Int

}