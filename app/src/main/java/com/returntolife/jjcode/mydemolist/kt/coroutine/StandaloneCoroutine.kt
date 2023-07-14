package com.returntolife.jjcode.mydemolist.kt.coroutine

import android.os.Build
import androidx.annotation.RequiresApi
import com.returntolife.jjcode.mydemolist.kt.coroutine.element.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

@RequiresApi(Build.VERSION_CODES.N)
class StandaloneCoroutine(context: CoroutineContext) : AbstractCoroutine<Unit>(context) {

    override fun handleJobException(e: Throwable): Boolean {
        super.handleJobException(e)
        context[CoroutineExceptionHandler]?.handleException(context, e)
            ?: Thread.currentThread()
                .let { it.uncaughtExceptionHandler.uncaughtException(it, e) }
        return true
    }
}