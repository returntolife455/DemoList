package com.returntolife.jjcode.mydemolist.kt.coroutine

import android.os.Build
import androidx.annotation.RequiresApi
import com.returntolife.jjcode.mydemolist.kt.coroutine.cancel.suspendCancellableCoroutine
import com.returntolife.jjcode.mydemolist.kt.coroutine.scope.CoroutineScope
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@RequiresApi(Build.VERSION_CODES.N)
abstract class AbstractCoroutine<T>(context: CoroutineContext) : Job, Continuation<T>,
    CoroutineScope {

    protected val state = AtomicReference<CoroutineState>()

    override val context: CoroutineContext

    override val scopeContext: CoroutineContext
        get() = context

    protected val parentJob = context[Job]
    private var parentCancelDisposable: Disposable? = null

    init {
        state.set(CoroutineState.InComplete())
        this.context = context + this

        parentCancelDisposable = parentJob?.invokeOnCancel {
            println("parentJob invokeOnCancel this=${this}")
            cancel()
        }
    }

    val isCompleted get() = state.get() is CoroutineState.Complete<*>

    protected open fun handleJobException(e: Throwable) = false

    override val isActive: Boolean
        get() = when (state.get()) {
            is CoroutineState.Complete<*>, is CoroutineState.Cancelling -> false
            else -> true
        }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun resumeWith(result: Result<T>) {
        println("AbstractCoroutine resumeWith this=${this}")
        val newState = state.updateAndGet { prevState ->
            when (prevState) {
                is CoroutineState.Cancelling, is CoroutineState.InComplete -> {
                    CoroutineState.Complete(
                        result.getOrNull(),
                        result.exceptionOrNull()
                    ).from(prevState)
                }

                is CoroutineState.Complete<*> -> {
                    throw IllegalStateException("Already completed!")
                }
            }
        }

        (newState as CoroutineState.Complete<T>).exception?.let {
            tryHandleException(it)
        }

        newState.notifyCompletion(result)
        newState.clear()
        println("AbstractCoroutine notifyCompletion end")

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun invokeOnCancel(onCancel: OnCancel): Disposable {
        println("invokeOnCancel this=${this}")
        val disposable = CancellationHandlerDisposable(this, onCancel)
        val newState = state.updateAndGet { prev ->
            when (prev) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prev).with(disposable)
                }

                is CoroutineState.Cancelling, is CoroutineState.Complete<*> -> {
                    prev
                }
            }
        }

        (newState as? CoroutineState.Cancelling)?.let { onCancel() }
        return disposable
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun invokeOnCompletion(onComplete: OnComplete): Disposable {
        return doOnCompleted { onComplete() }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    protected fun doOnCompleted(block: (Result<T>) -> Unit): Disposable {
        val disposable = CompletionHandlerDisposable(this, block)

        val newState = state.updateAndGet { prev ->
            when (prev) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prev).with(disposable)
                }

                is CoroutineState.Cancelling -> {
                    CoroutineState.Cancelling().from(prev).with(disposable)
                }

                is CoroutineState.Complete<*> -> prev
            }
        }

        (newState as? CoroutineState.Complete<T>)?.let {
            block(
                when {
                    it.value != null -> Result.success(it.value)
                    it.exception != null -> Result.failure(it.exception)
                    else -> throw IllegalStateException("Won't happen.")
                }
            )
        }
        return disposable
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun cancel() {
        println("cancel this=${this}")
        val prevState = state.getAndUpdate { prev ->
            when (prev) {
                is CoroutineState.InComplete -> {
                    CoroutineState.Cancelling()
                }

                is CoroutineState.Cancelling, is CoroutineState.Complete<*> -> prev
            }
        }

        if (prevState is CoroutineState.InComplete) {
            prevState.notifyCancellation()
            prevState.clear()
        }
        parentCancelDisposable?.dispose()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun remove(disposable: Disposable) {
        state.updateAndGet { prev ->
            when (prev) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prev).without(disposable)
                }

                is CoroutineState.Cancelling -> {
                    CoroutineState.Cancelling().from(prev).without(disposable)
                }

                is CoroutineState.Complete<*> -> {
                    prev
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun join() {
        when (state.get()) {
            is CoroutineState.InComplete, is CoroutineState.Cancelling -> return joinSuspend()
            is CoroutineState.Complete<*> -> return
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private suspend fun joinSuspend() = suspendCancellableCoroutine<Unit> { continuation ->
        val disposable = doOnCompleted { result ->
            continuation.resume(Unit)
        }
        continuation.invokeOnCancellation { disposable.dispose() }
    }

    private fun tryHandleException(e: Throwable): Boolean {
        return when (e) {
            is CancellationException -> {
                false
            }

            else -> {
                (parentJob as? AbstractCoroutine<*>)?.handleChildException(e)?.takeIf { it }
                    ?: handleJobException(e)
            }
        }
    }

    protected open fun handleChildException(e: Throwable): Boolean {
        cancel()
        return tryHandleException(e)
    }
}

