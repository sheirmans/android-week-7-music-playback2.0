package com.ucsdextandroid1.musicsearch2.utils

/**
 * Created by rjaylward on 2019-05-18
 */

fun <T> List<T>?.orEmpty(): List<T> {
    return this ?: emptyList()
}