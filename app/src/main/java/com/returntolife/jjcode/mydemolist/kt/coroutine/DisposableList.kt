package com.returntolife.jjcode.mydemolist.kt.coroutine

sealed class DisposableList {
    object Nil : DisposableList()
    class Cons(val head: Disposable, val tail: DisposableList) : DisposableList()
}

fun DisposableList.remove(disposable: Disposable): DisposableList {
    return when (this) {
        DisposableList.Nil -> this
        is DisposableList.Cons -> {
            if (head == disposable) {
                return tail
            } else {
                DisposableList.Cons(head, tail.remove(disposable))
            }
        }
    }
}

tailrec fun DisposableList.forEach(action: (Disposable) -> Unit): Unit = when (this) {
    DisposableList.Nil -> Unit
    is DisposableList.Cons -> {
        action(this.head)
        this.tail.forEach(action)
    }
}

inline fun <reified T : Disposable> DisposableList.loopOn(crossinline action: (T) -> Unit) =
    forEach {
        when (it) {
            is T -> action(it)
        }
    }