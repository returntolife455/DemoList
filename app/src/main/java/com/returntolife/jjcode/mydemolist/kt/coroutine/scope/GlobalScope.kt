package com.returntolife.jjcode.mydemolist.kt.coroutine.scope

import com.returntolife.jjcode.mydemolist.kt.coroutine.element.CoroutineName
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


object GlobalScope : CoroutineScope {
    override val scopeContext: CoroutineContext
        get() = EmptyCoroutineContext+CoroutineName("My scope")
}