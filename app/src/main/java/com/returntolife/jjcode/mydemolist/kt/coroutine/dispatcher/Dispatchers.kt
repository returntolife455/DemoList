package com.returntolife.jjcode.mydemolist.kt.coroutine.dispatcher

object Dispatchers {

    val Android by lazy {
        DispatcherContext(AndroidDispatcher)
    }


    val Default by lazy {
        DispatcherContext(DefaultDispatcher)
    }
}