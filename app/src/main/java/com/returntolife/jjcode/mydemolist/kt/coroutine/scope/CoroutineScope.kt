package com.returntolife.jjcode.mydemolist.kt.coroutine.scope


import com.returntolife.jjcode.mydemolist.kt.coroutine.AbstractCoroutine
import com.returntolife.jjcode.mydemolist.kt.coroutine.Job
import kotlin.coroutines.*

interface CoroutineScope {
    val scopeContext: CoroutineContext
}

//internal class ContextScope(context: CoroutineContext) : CoroutineScope {
//    override val scopeContext: CoroutineContext = context
//}
//
//operator fun CoroutineScope.plus(context: CoroutineContext): CoroutineScope =
//        ContextScope(scopeContext + context)
//
//fun CoroutineScope.cancel() {
//    val job = scopeContext[Job]
//            ?: error("Scope cannot be cancelled because it does not have a job: $this")
//    job.cancel()
//}
//
//suspend fun <R> coroutineScope(block: suspend CoroutineScope.() -> R): R =
//        suspendCoroutine { continuation ->
//            val coroutine = ScopeCoroutine(continuation.context, continuation)
//            block.startCoroutine(coroutine, coroutine)
//        }
//
//internal open class ScopeCoroutine<T>(
//        context: CoroutineContext,
//        protected val continuation: Continuation<T>
//) : AbstractCoroutine<T>(context) {
//
//    override fun resumeWith(result: Result<T>) {
//        super.resumeWith(result)
//        continuation.resumeWith(result)
//    }
//}
//
