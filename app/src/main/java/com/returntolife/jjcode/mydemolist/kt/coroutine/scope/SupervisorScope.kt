package com.returntolife.jjcode.mydemolist.kt.coroutine.scope

import android.os.Build
import androidx.annotation.RequiresApi
import com.returntolife.jjcode.mydemolist.kt.coroutine.ScopeCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

@RequiresApi(Build.VERSION_CODES.N)
private class SupervisorCoroutine<T>(
        context: CoroutineContext,
        continuation: Continuation<T>
) : ScopeCoroutine<T>(context, continuation) {

    override fun handleChildException(e: Throwable): Boolean {
        return false
    }

}

@RequiresApi(Build.VERSION_CODES.N)
suspend fun <R> supervisorScope(block: suspend CoroutineScope.() -> R): R =
        suspendCoroutine { continuation ->
            val coroutine = SupervisorCoroutine(continuation.context, continuation)
            block.startCoroutine(coroutine, coroutine)
        }