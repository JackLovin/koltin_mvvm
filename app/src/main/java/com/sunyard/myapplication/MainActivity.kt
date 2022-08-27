package com.sunyard.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.sunyard.myapplication.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding,MyViewModel>(MyViewModel()) {
    private  val TAG = "MainActivity"
    override fun layoutId(): Int {
     return  R.layout.activity_main
    }

    override fun bindViewModel() {
        Log.e(TAG, "bindViewModel: " )
         binding.myViewModel= MyViewModel()
        lifecycle.addObserver(MyViewModel())
    }


}