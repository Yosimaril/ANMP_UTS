package com.an.habittracker.util

import android.content.Context
import java.io.File
import java.io.IOException
import java.io.FileOutputStream

class FileHelper(val context: Context) {
    private val folderName = "habitTracker"
    private val fileName = "myHabit.txt"

    private fun getFile(): File {
        val dir = File(context.filesDir, folderName)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return File(dir, fileName)
    }

    fun writeToFile(data: String) {
        try {
            val file = getFile()
            FileOutputStream(file, false).use { output ->
                output.write(data.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun readFromFile(): String {
        val file = getFile()
        if (!file.exists()) return ""

        return try {
            file.readText()
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }

    fun deleteFile(): Boolean {
        return getFile().delete()
    }
}