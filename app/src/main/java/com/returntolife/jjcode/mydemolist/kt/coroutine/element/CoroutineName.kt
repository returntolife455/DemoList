package com.returntolife.jjcode.mydemolist.kt.coroutine.element


import kotlin.coroutines.CoroutineContext

class CoroutineName(val name:String) : CoroutineContext.Element {

    companion object Key:CoroutineContext.Key<CoroutineName>

    override val key: CoroutineContext.Key<*>
        get() = CoroutineName

    override fun toString(): String {
        return name
    }
}