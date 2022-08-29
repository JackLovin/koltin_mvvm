package com.example.myapplication.http.base

import com.example.myapplication.http.error.AppException


open class BaseResponse<T> {
    var message: String? = null
    var statusCode = 0
    var result: T? = null
}

class StartResponse<T> : BaseResponse<T>()

data class SuccessResponse<T>( var data1: T) : BaseResponse<T>()

class EmptyResponse<T> : BaseResponse<T>()

data class FailureResponse<T>(val exception: AppException) : BaseResponse<T>()