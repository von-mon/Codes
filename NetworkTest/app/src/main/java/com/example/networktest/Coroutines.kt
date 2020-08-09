package com.example.networktest

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(){
    runBlocking {
        launch {
            println("code run is coroutines scope1")
            delay(1500)
            println("codes run is coroutine scope finished1")
        }
        launch {
            println("code run is coroutines scope2")
            delay(1500)
            println("codes run is coroutine scope finished2")
        }
    }
}