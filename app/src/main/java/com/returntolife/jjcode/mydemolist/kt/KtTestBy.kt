package com.returntolife.jjcode.mydemolist.kt

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * Created by HeJiaJun on 2021/2/22.
 * Email:hejj@mama.cn
 * des:
 */
class KtTestBy {

    interface  IByListener{
        fun showTip()
    }


    class ByBase():IByListener{

        val tip:String by lazy { "lazy tip" }
        var name: String by Delegates.observable("hello", { kProperty: KProperty<*>, oldName: String, newName: String ->
            println("${kProperty.name}---${oldName}--${newName}")
        })

        override fun showTip() {
            println("name=${name} tip=${tip}")
        }
    }

    class ByProxy(private val base:IByListener):IByListener by base{
        override fun showTip() {
            base.showTip()
            println("hello proxy")
        }
    }
}



fun main(){
    println("Hello Kotlin")
    val base= KtTestBy.ByBase()
    val proxy=KtTestBy.ByProxy(base)
    proxy.showTip()

    println("--------------")
    base.name="haha"
}