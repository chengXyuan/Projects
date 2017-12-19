package com.international.wtw.lottery.event

interface ProgressListener {

    fun update(total: Long, contentLength: Long, done: Boolean)
}
