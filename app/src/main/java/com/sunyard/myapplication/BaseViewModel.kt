package com.sunyard.myapplication

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel

/**
 * @package name：com.sunyard.myapplication
 * @describe
 * @anthor jokerlover
 *@email:shengj.chen@sunyard.com
 * @time 2022/8/27 21:03
 */
open class BaseViewModel :ViewModel(),LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
   open fun onCreate(){
        Log.e("BaseViewModel", "onCreate: ", )

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy(){

    }
}