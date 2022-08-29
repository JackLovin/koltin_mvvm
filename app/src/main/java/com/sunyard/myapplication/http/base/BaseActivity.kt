//package com.example.myapplication.http.base
//
//import android.graphics.Color
//import android.os.Build
//import android.os.Bundle
//import android.view.View
//import android.view.Window
//import android.view.WindowManager
//import androidx.appcompat.app.AppCompatActivity
//import androidx.databinding.DataBindingUtil
//import androidx.databinding.ViewDataBinding
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import com.blankj.utilcode.util.ToastUtils
//import com.example.myapplication.http.LoadingDialog
//import com.example.myapplication.http.util.MyLog
//
//
//abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {
//
//    protected lateinit var binding: V
//    protected lateinit var mViewModel: VM
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        //initStatusBar()
//        binding = DataBindingUtil.setContentView(this, getContentViewId());
//        binding.lifecycleOwner = this
//        createViewModel()
//        viewBindViewModel()
//        onViewCreated()
//        lifecycle.addObserver(mViewModel)
//        initView()
//
//    }
//
//    //获取当前activity布局文件,并初始化我们的binding
//    protected abstract fun getContentViewId(): Int
//    open fun createViewModel() {
//        mViewModel = ViewModelProvider(this)[viewModelClass()]
//    }
//    /**
//     * 初始化沉浸式状态栏
//     */
//    private fun initStatusBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val window: Window = window
//            window.clearFlags(
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
//            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.statusBarColor = Color.TRANSPARENT
//            window.navigationBarColor = Color.TRANSPARENT
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //4.4到5.0
//            val localLayoutParams: WindowManager.LayoutParams = window.attributes
//            localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
//        }
//    }
//    abstract fun viewModelClass(): Class<VM>
//    //abstract fun getViewBinding(): V
//
//    abstract fun viewBindViewModel()
//    abstract fun initView()
//    private fun onViewCreated() {
//        mViewModel.uiChangeLiveData.showDialogEvent.observe(this, Observer {
//            MyLog.e( "showDialogEvent  $it")
//            if (it != null) {
//                LoadingDialog.getInstance(this).show()
//
//            } else {
//                LoadingDialog.getInstance(this).hide()
//            }
//
//        })
//        mViewModel.uiChangeLiveData.toastEvent.observe(this, Observer {
//            ToastUtils.showShort(it)
//        })
//    }
//}