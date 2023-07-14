package com.returntolife.jjcode.mydemolist.kt.coroutine

interface Deferred<T>: Job {

    suspend fun await(): T

}