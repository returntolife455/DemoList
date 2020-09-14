package com.returntolife.jjcode.mydemolist.demo.function.lambdaTest

/**
 * Created by HeJiaJun on 2020/7/29.
 * Email:hejj@mama.cn
 * des:
 */



fun main() {



    html {
        print("aaa")
    }

}

class HTML {
    fun body() {
        println("body")
    }
}

fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()  // 创建接收者对象
    html.init()        // 将该接收者对象传给该 lambda
    return html
}



