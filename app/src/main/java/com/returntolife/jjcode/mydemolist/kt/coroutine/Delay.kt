package com.returntolife.jjcode.mydemolist.kt.coroutine

import android.os.Build
import androidx.annotation.RequiresApi
import com.returntolife.jjcode.mydemolist.kt.coroutine.cancel.suspendCancellableCoroutine
import java.lang.Exception
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private val executor = Executors.newScheduledThreadPool(1) { runnable ->
    Thread(runnable, "Scheduler").apply { isDaemon = true }
}

@RequiresApi(Build.VERSION_CODES.N)
suspend fun delay(time: Long, unit: TimeUnit = TimeUnit.MILLISECONDS) {
    if (time <= 0) {
        return
    }

    suspendCoroutine<Unit> { continuation ->
        Thread({
            println("thread before name=${Thread.currentThread().name}")
            continuation.resume(Unit)
        },"my thread").start()
//        val future = executor.schedule({ continuation.resume(Unit) }, time, unit)
//        continuation.invokeOnCancellation { future.cancel(true) }
    }


}

suspend fun notSuspend()=suspendCoroutine<Int> {
    it.resume(10)
}