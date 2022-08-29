package com.example.myapplication.http.error

import com.example.myapplication.http.error.Error

/**
 * 自定义错误信息异常
 */
class AppException : Exception {

    var status: Int = 0 //错误码
    override var message: String? = null //错误日志
    var throwable: Throwable? = null

    constructor(status: Int, message: String? = "", throwable: Throwable? = null) : super(message) {
        this.message = message ?: "请求失败，请稍后再试"
        this.status = status
        this.throwable = throwable
    }

    constructor(error: Error, e: Throwable?) {
        status = error.getKey()
        message = e?.message
        throwable = e
    }
}