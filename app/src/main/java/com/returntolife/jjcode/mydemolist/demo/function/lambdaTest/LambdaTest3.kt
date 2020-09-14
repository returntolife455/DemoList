package com.returntolife.jjcode.mydemolist.demo.function.lambdaTest

/**
 * Created by HeJiaJun on 2020/7/30.
 * Email:hejj@mama.cn
 * des:
 */
fun main() {

// 调用
    test(10,sum(3,5)) // 结果为：18

// 调用
    test(10,{ num1: Int, num2: Int ->  num1 + num2 })  // 结果为：18
}

// 源代码
fun test(a : Int , b : Int) : Int{
    return a + b
}

fun sum(num1 : Int , num2 : Int) : Int{
    return num1 + num2
}

fun test2(num1 : Int , num2 : Int,body:(num1 : Int , num2 : Int) -> Int):Int{
    return body(num1,num2)
}


// lambda
fun test(a : Int , b : (num1 : Int , num2 : Int) -> Int) : Int{
    return a + b.invoke(3,5)
}

