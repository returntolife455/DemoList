package com.returntolife.jjcode.mydemolist.kt.coroutine

import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

sealed class MyList<A> {
    abstract fun isEmpty(): Boolean

    // 扩展类在列表类内定义，并成为私有类
    private object Nil : MyList<Nothing>() {
        override fun isEmpty(): Boolean = true

        override fun toString(): String = "[NIL]"
    }


    private class Cons<A>(
        internal val head: A,
        internal val tail: MyList<A>
    ): MyList<A>() {
        override fun isEmpty(): Boolean = false

        override fun toString(): String = "[${toString("", this)}NIL]"

        tailrec fun toString(acc: String, myList: MyList<A>): String = when(myList) {
            is Nil -> acc
            is Cons -> toString("$acc${myList.head}, ", myList.tail)
        }
    }

    fun cons(a: A): MyList<A> = Cons(a, this)

    fun setHead(a: A): MyList<A> = when(this) {
        Nil -> throw IllegalStateException("setHead called on an empty list")
        is Cons -> tail.cons(a)
    }

    // 这里使用共递归优化堆栈，并且对类型做判断
    fun drop(n: Int): MyList<A> {
        tailrec fun drop(n: Int, list: MyList<A>): MyList<A> =
            if (n <= 0) list
            else when (list) {
                is Cons -> drop(n - 1, list.tail)
                is Nil -> list
            }
        return drop(n, this)
    }

    fun dropWhile(p: (A) -> Boolean): MyList<A> = dropWhile(this, p)

    fun concat(list: MyList<A>): MyList<A> = concat(this, list)

    fun reverse(): MyList<A> {
        tailrec fun <A> reverse(acc: MyList<A>, list: MyList<A>): MyList<A> = when(list) {
            Nil -> {
                println("acc=${acc} list=${list}")
                acc}
            is Cons -> {
                println("acc=${acc} list=${list}")
                reverse(acc.cons(list.head), list.tail)
            }
        }
        return reverse(invoke(), this)
    }


    fun init(): MyList<A> = reverse().drop(1).reverse()


    companion object {
        // foldRight 的第一个参数是将 Nil 显示转化为 list<A>
        operator fun <A> invoke(vararg az: A): MyList<A> =
            az.foldRight(Nil as MyList<A>) { a, acc ->
                // 使用反向递归从后包到前
                Cons(a, acc)
            }
        private tailrec fun <A> dropWhile(list: MyList<A>, p: (A) -> Boolean): MyList<A> = when (list) {
            Nil -> list
            is Cons -> if (p(list.head)) dropWhile(list.tail, p) else list
        }

        private fun <A> concat(list1: MyList<A>, list2: MyList<A>): MyList<A> = when (list1) {
            Nil -> list2
            is Cons -> concat(list1.tail, list2).cons(list1.head)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
fun main() {
    println("main start threadName=${Thread.currentThread().name}")

    suspend {
        timeConsumingMethod()
        ""
    }.startCoroutine(object:Continuation<String>{
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<String>) {

        }

    })

    println("main end threadName=${Thread.currentThread().name}")

    Thread.sleep(20000)
}

fun timeConsumingMethod() {
    val startTime = System.currentTimeMillis() // 记录方法开始执行的时间

    // 模拟一个需要耗费时间的操作，例如读取大量数据或进行复杂计算
    for (i in 0..999999999) {
        // do something
    }
    val endTime = System.currentTimeMillis() // 记录方法结束执行的时间
    val elapsedTime = endTime - startTime // 计算方法执行的时间
    println("Elapsed time: " + elapsedTime + "ms")
}



suspend fun requestTest():String{

    return suspendCoroutine<String> {
        "hahah"
    }
}

//suspend inline fun <reified T> CoroutineScope.commonRequest(timeOut: Long = 60 * 1000, block:()->T): Result<T>? {
//    return withTimeoutOrNull(2000) {
//        try {
//            Result.success(block)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//}


