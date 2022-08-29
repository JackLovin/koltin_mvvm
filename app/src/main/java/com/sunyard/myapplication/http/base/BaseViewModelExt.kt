package com.example.myapplication.http.base

import androidx.lifecycle.viewModelScope
import com.example.myapplication.http.error.AppException
import com.example.myapplication.http.error.ExceptionHandle
import com.example.myapplication.http.util.MyLog


import kotlinx.coroutines.*

/**
 * 过滤服务器结果，失败直接抛异常，没有回到异常信息
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 * @param isShowDialog 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,
    success: (T) -> Unit,
    isShowDialog: Boolean = true,
    loadingMessage: String = "请求网络中..."
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) uiChangeLiveData.showDialogEvent.postValue(loadingMessage)
            //请求体
            block()
        }.onSuccess {
            //网络请求成功 关闭弹窗
            uiChangeLiveData.showDialogEvent.postValue(null)
            MyLog.e("csj","网络请求成功 关闭弹窗")
            runCatching {
                //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                executeResponse(it) { t ->
                    success(t)
                }
            }.onFailure {
                //失败回调
                ExceptionHandle.handleException(it).message?.apply {
                    //打印错误消息
                    MyLog.e(this)
                    uiChangeLiveData.toastEvent.postValue(this)
                }
            }
        }.onFailure {
            //网络请求异常 关闭弹窗
            uiChangeLiveData.showDialogEvent.postValue(null)
            //失败回调
            ExceptionHandle.handleException(it).message?.apply {
                //打印错误消息
                MyLog.e(this)
                uiChangeLiveData.toastEvent.postValue(this)
            }
        }
    }
}

/**
 * 过滤服务器结果，失败抛异常
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不传
 * @param isShowDialog 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,
    success: (T) -> Unit,
    error: (AppException) -> Unit = {},
    isShowDialog: Boolean = true,
    loadingMessage: String = "请求网络中..."
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) uiChangeLiveData.showDialogEvent.postValue(loadingMessage)
            //请求体
            block()
        }.onSuccess {
            MyLog.e("返回结果：${it.statusCode}")
            //网络请求成功 关闭弹窗
            uiChangeLiveData.showDialogEvent.postValue(null)
            runCatching {
                //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                executeResponse(it) { t ->
                    MyLog.e("返回结果：executeResponse ${t.toString()}")
                    success(t)
                }
            }.onFailure {
                MyLog.e("返回结果：onFailure ${it}")

                error(ExceptionHandle.handleException(it))
                //打印错误消息
                ExceptionHandle.handleException(it).message?.apply {
                    //打印错误消息
                    MyLog.e(this)
                    uiChangeLiveData.toastEvent.postValue(this)
                }
            }
        }.onFailure {
            //网络请求异常 关闭弹窗
            uiChangeLiveData.showDialogEvent.postValue(null)
            error(ExceptionHandle.handleException(it))
            //失败回调
            ExceptionHandle.handleException(it).message?.apply {
                //打印错误消息
                MyLog.e(this)
                uiChangeLiveData.toastEvent.postValue(this)
            }
        }
    }
}

/**
 *  不过滤请求结果
 * @param block 请求体 必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不给
 * @param isShowDialog 是否显示加载框
 * @param loadingMessage 加载框提示内容
 */
fun <T> BaseViewModel.requestNoCheck(
    block: suspend () -> T,
    success: (T) -> Unit,
    error: (AppException) -> Unit = {},
    isShowDialog: Boolean = false,
    loadingMessage: String = "请求网络中..."
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    if (isShowDialog) uiChangeLiveData.showDialogEvent.postValue(loadingMessage)
    return viewModelScope.launch {
        runCatching {
            //请求体
            block()
        }.onSuccess {
            //网络请求成功 关闭弹窗
            uiChangeLiveData.showDialogEvent.postValue(null)
            //成功回调
            success(it)
        }.onFailure {
            //网络请求异常 关闭弹窗
            uiChangeLiveData.showDialogEvent.postValue(null)
            error(ExceptionHandle.handleException(it))
            //失败回调
            ExceptionHandle.handleException(it).message?.apply {
                //打印错误消息
                MyLog.e(this)
                uiChangeLiveData.toastEvent.postValue(this)
            }
        }
    }
}

/**
 * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
 */
suspend fun <T> executeResponse(
    response: BaseResponse<T>,
    success: suspend CoroutineScope.(T) -> Unit,

    ) {
    coroutineScope {
        when (response.statusCode) {
            0, 200 -> {
                MyLog.e("${response.result}")

                response.result?.let {
                    success(it)
                }
            }
            else -> {
                throw AppException(response.statusCode, response.message)
            }
        }
    }
}

/**
 *  调用协程
 * @param block 操作耗时操作任务
 * @param success 成功回调
 * @param error 失败回调 可不给
 */
fun <T> BaseViewModel.launch(
    block: () -> T,
    success: (T) -> Unit,
    error: (Throwable) -> Unit = {}
) {
    viewModelScope.launch {
        kotlin.runCatching {
            withContext(Dispatchers.IO) {
                block()
            }
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }
}
