package com.y.medicinesound_compose.utils

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AssetFilePathProvider(private val context: Context) {

    fun assetFilePath(assetName: String): String {
        val file = File(context.filesDir, assetName)
        if (file.exists() && file.length() > 0) {
            return file.absolutePath
        }

        try {
            context.assets.open(assetName).use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    val buffer = ByteArray(4 * 1024)
                    var read: Int
                    while (inputStream.read(buffer).also { read = it } != -1) {
                        outputStream.write(buffer, 0, read)
                    }
                    outputStream.flush()
                }
            }
            return file.absolutePath
        } catch (e: IOException) {
            throw RuntimeException("Error reading asset file!", e)
        }
    }


}