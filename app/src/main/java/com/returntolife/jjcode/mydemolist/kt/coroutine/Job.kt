package com.returntolife.jjcode.mydemolist.kt.coroutine

import kotlin.coroutines.CoroutineContext


typealias CancellationException = java.util.concurrent.CancellationException

typealias OnComplete = () -> Unit
typealias OnCancel = () -> Unit

interface Job : CoroutineContext.Element {
    companion object Key : CoroutineContext.Key<Job>

    override val key: CoroutineContext.Key<*> get() = Job

    val isActive: Boolean

    fun invokeOnCancel(onCancel: OnCancel): Disposable

    fun invokeOnCompletion(onComplete: OnComplete): Disposable

    fun cancel()

    fun remove(disposable: Disposable)

    suspend fun join()
}