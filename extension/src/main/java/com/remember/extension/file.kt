package com.remember.extension

import android.content.Context
import android.util.Base64
import android.util.Base64OutputStream
import id.zelory.compressor.Compressor
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream

fun File.toBase64() : String {
    return FileInputStream(this).use { inputStream ->
        ByteArrayOutputStream().use { outputStream ->
            Base64OutputStream(outputStream, Base64.DEFAULT).use { base64FilterStream ->
                inputStream.copyTo(base64FilterStream)
                base64FilterStream.close()
                outputStream.toString()
            }
        }
    }
}

fun File.compress(context: Context) : File {
    return Compressor(context).setQuality(30).compressToFile(this)
}
