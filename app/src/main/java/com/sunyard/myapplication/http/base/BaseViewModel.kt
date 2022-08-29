//package com.example.myapplication.http.base
//
//import androidx.lifecycle.*
//import com.example.myapplication.http.SingleLiveEvent
//
//open class BaseViewModel : ViewModel() , LifecycleObserver {
//    companion object {
//        class UIChangeLiveData : SingleLiveEvent<Void>() {
//            val showDialogEvent = SingleLiveEvent<String>()
//            val toastEvent = SingleLiveEvent<String>()
//        }
//    }
//    val uiChangeLiveData = UIChangeLiveData()
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    open fun onCreate(){
//
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    open fun onResume(){
//
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//    open fun onDestroy(){
//
//    }
//
//}