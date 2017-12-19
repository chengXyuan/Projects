package com.daking.lottery.util

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class FileUtils private constructor() {

    private object Holder {
        val INSTANCE = FileUtils()
    }

    companion object {
        val instance = Holder.INSTANCE
    }

    fun writeFile(stream: InputStream, file: File) {
        if (!file.parentFile.exists())
            file.parentFile.mkdirs()

        if (file.exists())
            file.delete()

        val out = FileOutputStream(file)
        val buffer = ByteArray(1024 * 128)
        var len: Int
        while (stream.read(buffer) != -1) {
            len = stream.read(buffer)
            out.write(buffer, 0, len)
        }
        out.flush()
        out.close()
        stream.close()
    }

}