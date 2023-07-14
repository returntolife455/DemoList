package com.returntolife.jjcode.mydemolist.kt

import android.util.Log
import kotlinx.coroutines.delay
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.intrinsics.createCoroutineUnintercepted
import kotlin.coroutines.intrinsics.intercepted
import kotlin.coroutines.resume
import kotlin.coroutines.startCoroutine

fun main() {
    println("Hello Kotlin")
    createContinuationTest()
}


private fun createContinuationTest() {
    val continuation = suspend {
        println("In Coroutine.")
        throw IllegalArgumentException("exception my")
        5
    }.createCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println("Continuation end $result")

        }

    })

    continuation.resume(Unit)
}

private fun continuationStartTest() {
    val continuation = suspend {
        println("In Coroutine.")
        throw IllegalArgumentException("exception my")
        5
    }

    continuation.startCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println("Continuation end $result")

        }

    })
}

fun <R, T> launchCoroutine(receiver: R, block: suspend R.() -> T) {
    block.startCoroutine(receiver, object : Continuation<T> {
        override fun resumeWith(result: Result<T>) {
            println("Coroutine End: $result")
        }

        override val context = EmptyCoroutineContext
    })
}

class ProducerScope<T> {
    suspend fun produce(value: T) {

    }
}

fun callLaunchCoroutine() {
    launchCoroutine(ProducerScope<Int>())
    {
        println("In Coroutine.")
        produce(1024)
        delay(1000)
        produce(2048)
    }
}