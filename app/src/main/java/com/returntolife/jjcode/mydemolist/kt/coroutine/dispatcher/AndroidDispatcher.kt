package com.returntolife.jjcode.mydemolist.kt.coroutine.dispatcher

import android.os.Handler
import android.os.Looper


object AndroidDispatcher:Dispatcher {

    private val handler = Handler(Looper.getMainLooper())

    override fun dispatch(block: () -> Unit) {
        handler.post(block)
    }
}