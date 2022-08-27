package com.sunyard.myapplication

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField

/**
 * @package name：com.sunyard.myapplication
 * @describe
 * @anthor jokerlover
 *@email:shengj.chen@sunyard.com
 * @time 2022/8/27 20:59
 */
class MyViewModel : BaseViewModel() {
    private val TAG = "MyViewModel"

    var textContent = ObservableField<String>()
    override fun onCreate() {
        super.onCreate()

        Log.e(TAG, "onCreate: ${textContent.get()}")

    }

    fun onClick(view: View) {
        textContent.set("chenshengjie")

    }
}