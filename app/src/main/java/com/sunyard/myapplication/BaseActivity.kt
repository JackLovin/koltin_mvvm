package com.sunyard.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

/**
 * @package name：com.sunyard.myapplication
 * @describe
 * @anthor jokerlover
 * @time 2022/8/27 21:52
 */
abstract class BaseActivity<V:ViewDataBinding,VM:BaseViewModel>(var viewModel: VM) :AppCompatActivity() {
lateinit var binding:V
lateinit var  mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "BaseActivity onCreate: " )
        binding=DataBindingUtil.setContentView(this,layoutId())
        binding.lifecycleOwner=this
        mViewModel =  ViewModelProvider(this).get(viewModel::class.java);
//        binding.setVariable(BR.myViewModel,mViewModel)
        bindViewModel()
    }
   abstract fun layoutId():Int
   abstract fun bindViewModel()

}