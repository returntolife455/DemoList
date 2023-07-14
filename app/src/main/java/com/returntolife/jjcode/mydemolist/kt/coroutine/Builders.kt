package com.returntolife.jjcode.mydemolist.kt.coroutine


import android.os.Build
import androidx.annotation.RequiresApi
import com.returntolife.jjcode.mydemolist.kt.coroutine.element.CoroutineName
import com.returntolife.jjcode.mydemolist.kt.coroutine.dispatcher.Dispatchers
import com.returntolife.jjcode.mydemolist.kt.coroutine.scope.CoroutineScope
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

private var coroutineIndex = AtomicInteger(0)

@RequiresApi(Build.VERSION_CODES.N)
fun CoroutineScope.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
): Job {
    val completion = StandaloneCoroutine(newCoroutineContext(context))

    block.startCoroutine(completion, completion)
    return completion
}

@RequiresApi(Build.VERSION_CODES.N)
fun <T> CoroutineScope.async(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> T
): Deferred<T> {
    val completion = DeferredCoroutine<T>(newCoroutineContext(context))
    block.startCoroutine(completion, completion)
    return completion
}


fun CoroutineScope.newCoroutineContext(context: CoroutineContext): CoroutineContext {
    val combined =
        scopeContext + context + CoroutineName("@coroutine#${coroutineIndex.getAndIncrement()}")
    return if (combined != Dispatchers.Default && combined[ContinuationInterceptor] == null) {
        combined + Dispatchers.Default
    } else combined
}

@RequiresApi(Build.VERSION_CODES.N)
suspend fun <R> coroutineScope(block: suspend CoroutineScope.() -> R): R =
    suspendCoroutine { continuation ->
        val coroutine = ScopeCoroutine(
            continuation.context,
            continuation
        )
        block.startCoroutine(coroutine, coroutine)
    }


@RequiresApi(Build.VERSION_CODES.N)
internal open class ScopeCoroutine<T>(
    context: CoroutineContext,
    protected val continuation: Continuation<T>
) :
    AbstractCoroutine<T>(context) {
    override fun resumeWith(result: Result<T>) {
        super.resumeWith(result)
        continuation.resumeWith(result)
    }
}
