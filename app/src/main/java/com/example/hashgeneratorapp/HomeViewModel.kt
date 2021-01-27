package com.example.hashgeneratorapp

import android.util.Log
import androidx.lifecycle.ViewModel
import java.security.MessageDigest.*

class HomeViewModel : ViewModel() {

    fun getHash(plainText: String, algorithms: String): String{
        val byte = getInstance(algorithms).digest(plainText.toByteArray())
        return toHex(byte)
    }

    private fun toHex(byteArray: ByteArray): String {
        Log.d("ViewModel", byteArray.joinToString { "%02x".format(it) })
        return byteArray.joinToString { "%02x".format(it) }
    }

}