package com.international.wtw.lottery.api

import com.international.wtw.lottery.event.ProgressListener
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okio.*


class ProgressInterceptor(val listener: ProgressListener) : Interceptor {

    private var bufferedSource: BufferedSource? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val resp = chain.proceed(chain.request())
        return resp.newBuilder()
                .body(object : ResponseBody() {
                    override fun contentLength(): Long {
                        return if (resp.body() == null) {
                            -1L
                        } else {
                            resp.body()!!.contentLength()
                        }
                    }

                    override fun contentType(): MediaType? {
                        return if (resp.body() == null) {
                            null
                        } else {
                            resp.body()!!.contentType()
                        }
                    }

                    override fun source(): BufferedSource? {
                        if (resp.body() == null) {
                            return null
                        }
                        if (bufferedSource == null) {
                            bufferedSource = Okio.buffer(source(resp.body()!!))
                        }
                        return bufferedSource!!
                    }
                })
                .build()
    }

    private fun source(body: ResponseBody): Source {
        return object : ForwardingSource(body.source()) {
            internal var totalBytesRead = 0L

            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0

                listener.update(totalBytesRead, body.contentLength(), bytesRead == -1L)
                return bytesRead
            }
        }
    }

}