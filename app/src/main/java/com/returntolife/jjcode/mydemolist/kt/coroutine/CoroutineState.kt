package com.returntolife.jjcode.mydemolist.kt.coroutine


sealed class CoroutineState {

    class InComplete : CoroutineState()
    class Cancelling : CoroutineState()
    class Complete<T>(val value: T? = null, val exception: Throwable? = null) : CoroutineState()

    private var disposableList: DisposableList = DisposableList.Nil

    fun from(state: CoroutineState): CoroutineState {
        this.disposableList = state.disposableList
        return this
    }

    fun with(disposable: Disposable): CoroutineState {
        this.disposableList = DisposableList.Cons(disposable, this.disposableList)
        return this
    }

    fun without(disposable: Disposable): CoroutineState {
        this.disposableList = this.disposableList.remove(disposable)
        return this
    }

    fun clear() {
        this.disposableList = DisposableList.Nil
    }

    fun <T> notifyCompletion(result: Result<T>) {
        this.disposableList.loopOn<CompletionHandlerDisposable<T>> { it.onComplete(result) }
    }

    fun notifyCancellation() {
        disposableList.loopOn<CancellationHandlerDisposable> {
            it.onCancel()
        }
    }
}