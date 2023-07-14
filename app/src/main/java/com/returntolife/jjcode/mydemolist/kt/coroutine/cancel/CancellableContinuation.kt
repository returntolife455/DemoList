package com.returntolife.jjcode.mydemolist.kt.coroutine.cancel


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.returntolife.jjcode.mydemolist.kt.coroutine.CancellationException
import com.returntolife.jjcode.mydemolist.kt.coroutine.Job
import com.returntolife.jjcode.mydemolist.kt.coroutine.OnCancel
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.intercepted
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resumeWithException

class CancellableContinuation<T>(private val continuation: Continuation<T>) : Continuation<T> by continuation {

    private val state = AtomicReference<CancelState>(CancelState.InComplete)
    private val decision = AtomicReference(CancelDecision.UNDECIDED)

    val isCompleted: Boolean
        get() = when (state.get()) {
            CancelState.InComplete,
            is CancelState.CancelHandler -> false
            is CancelState.Complete<*>,
            CancelState.Cancelled -> true
        }

    @SuppressLint("NewApi")
    override fun resumeWith(result: Result<T>) {
        println("CancellableContinuation resumeWith")
        when {
            decision.compareAndSet(CancelDecision.UNDECIDED, CancelDecision.RESUMED) -> {
                // before getResult called.
                state.set(CancelState.Complete(result.getOrNull(), result.exceptionOrNull()))
            }
            decision.compareAndSet(CancelDecision.SUSPENDED, CancelDecision.RESUMED) -> {
                state.updateAndGet { prev ->
                    when (prev) {
                        is CancelState.Complete<*> -> {
                            throw IllegalStateException("Already completed.")
                        }
                        else -> {
                            CancelState.Complete(result.getOrNull(), result.exceptionOrNull())
                        }
                    }
                }
                continuation.resumeWith(result)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getResult(): Any? {
        println("getResult CancelableContinuation")
        installCancelHandler()

        if(decision.compareAndSet(CancelDecision.UNDECIDED, CancelDecision.SUSPENDED))
            return COROUTINE_SUSPENDED

        return when (val currentState = state.get()) {
            is CancelState.CancelHandler,
            CancelState.InComplete -> COROUTINE_SUSPENDED
            CancelState.Cancelled -> throw CancellationException("Continuation is cancelled.")
            is CancelState.Complete<*> -> {
                (currentState as CancelState.Complete<T>).let {
                    it.exception?.let { throw it } ?: it.value
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun installCancelHandler() {
        if (isCompleted) return
        val parent = continuation.context[Job] ?: return
        parent.invokeOnCancel {
            doCancel()
        }
    }

    fun cancel() {
        if (isCompleted) return
        val parent = continuation.context[Job] ?: return
        parent.cancel()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun invokeOnCancellation(onCancel: OnCancel) {
        val newState = state.updateAndGet { prev ->
            when (prev) {
                CancelState.InComplete -> CancelState.CancelHandler(onCancel)
                is CancelState.CancelHandler -> throw IllegalStateException("It's prohibited to register multiple handlers.")
                is CancelState.Complete<*>,
                CancelState.Cancelled -> prev
            }
        }
        if (newState is CancelState.Cancelled) {
            onCancel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun doCancel() {
        val prevState = state.getAndUpdate { prev ->
            when (prev) {
                is CancelState.CancelHandler,
                CancelState.InComplete -> {
                    CancelState.Cancelled
                }
                CancelState.Cancelled,
                is CancelState.Complete<*> -> {
                    prev
                }
            }
        }
        if (prevState is CancelState.CancelHandler) {
            prevState.onCancel()
            resumeWithException(CancellationException("Cancelled."))
        }
    }
}


@RequiresApi(Build.VERSION_CODES.N)
suspend inline fun <T> suspendCancellableCoroutine(
        crossinline block: (CancellableContinuation<T>) -> Unit
): T = suspendCoroutineUninterceptedOrReturn { continuation ->
    val cancellable = CancellableContinuation(continuation.intercepted())
    block(cancellable)
    cancellable.getResult()
}