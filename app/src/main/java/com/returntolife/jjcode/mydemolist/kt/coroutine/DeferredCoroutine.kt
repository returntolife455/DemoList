package com.returntolife.jjcode.mydemolist.kt.coroutine

import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine

@RequiresApi(Build.VERSION_CODES.N)
class DeferredCoroutine<T>(context: CoroutineContext) : AbstractCoroutine<T>(context), Deferred<T> {
    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun await(): T {
        return when (val currentState = state.get()) {
            is CoroutineState.InComplete, is CoroutineState.Cancelling -> {
                awaitSuspend()
            }

            is CoroutineState.Complete<*> -> {
                currentState.exception?.let {
                    throw it
                } ?: (currentState.value as T)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private suspend fun awaitSuspend() = suspendCoroutine<T> {
        doOnCompleted { result ->
            it.resumeWith(result)
        }

    }


}