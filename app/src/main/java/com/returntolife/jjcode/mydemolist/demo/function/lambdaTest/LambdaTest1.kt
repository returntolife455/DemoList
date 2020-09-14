package com.returntolife.jjcode.mydemolist.demo.function.lambdaTest

import android.widget.ImageView

/**
 * Created by HeJiaJun on 2020/7/21.
 * Email:hejj@mama.cn
 * des:函数返回函数实例-通过返回自定义表达式筛选集合
 */

fun main() {


    val personList:MutableList<Person> = ArrayList()

    personList.add(Person("hjj",18,"123456"))
    personList.add(Person("cgx",10,"123456"))
    personList.add(Person("aaa",10,""))

    val contactListFilters=ContactListFilters()
    with(contactListFilters){
        prefix="a"
        onlyWithPhoneNumber=true
        age=10
    }

    println(personList.filter(contactListFilters.getPredicate()))
}

private data class Person(
    val name: String,
    val age: Int,
    val phoneNumber: String?
)


private class ContactListFilters {
    var onlyWithPhoneNumber: Boolean = false
    var prefix: String = ""
    var age: Int = 0


    fun getPredicate():(Person)->Boolean{


        val checkFixAndAge:(Person)->Boolean={person->
            person.age>age&&person.name.startsWith(prefix)
        }


        if(!onlyWithPhoneNumber){
            return checkFixAndAge
        }

        return {person:Person->Boolean
             checkFixAndAge(person) && person.phoneNumber?.isNotEmpty()==true
        }

    }
}

