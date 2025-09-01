package com.example.myapplication.result

import com.example.myapplication.exception.CommonException
import com.example.myapplication.model.response.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun<T> Flow<T>.asResult(): Flow<Result<T>> = map {
    if(it is NetworkResponse<*>){
        if (it.isSucceeded){
            Result.success(it)
        } else {
            Result.failure(CommonException(it))
        }
    } else {
        Result.success(it)
    }
}.onStart {

}.catch {
    emit(Result.failure(it))
}