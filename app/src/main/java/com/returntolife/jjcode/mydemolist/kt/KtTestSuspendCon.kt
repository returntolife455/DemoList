package com.returntolife.jjcode.mydemolist.kt


import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

suspend fun suspendFunc01(a: Int):Int {
    return 1
}

suspend fun suspendFunc02(a: String, b: String) {
    val test = suspendCoroutine<Int> { continuation ->
        thread {
            continuation.resumeWith(Result.success(5)) // ... ①
        }
    }
    println("test=${test}")
}


fun main() {
    println("Hello Kotlin")

    suspend {
        notSuspend()

    }.startCoroutine(object:Continuation<Int>{
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println("resumeWith end result=${result}")
        }

    })

}
suspend fun notSuspend() = suspendCoroutine<Int> { continuation ->
    thread {
        continuation.resume(100) // ... ①
    }
}