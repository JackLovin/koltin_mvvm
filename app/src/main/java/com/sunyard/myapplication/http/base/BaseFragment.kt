package com.example.myapplication.http.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.http.ProgressDialog
import com.example.myapplication.http.util.MyLog

abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    protected lateinit var binding: V
    protected lateinit var mViewModel: VM
    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //不能用这个 会报The specified child already has a parent. You must call removeView() on the child's parent first.错误
       // binding = DataBindingUtil.setContentView(activity!!, getContentViewId());
        binding = DataBindingUtil.inflate<V>(
            inflater,
            initLayoutId(savedInstanceState),
            container,
            false
        )
        binding.lifecycleOwner = this
        createViewModel()
        return binding.root
    }
    abstract fun initLayoutId(savedInstanceState: Bundle?): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog.Builder(requireContext()).noClose().get()


    }

    open fun createViewModel() {
        mViewModel = ViewModelProvider(this)[viewModelClass()]
    }
    abstract fun initView()
    abstract fun viewModelClass(): Class<VM>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        mViewModel.uiChangeLiveData.showDialogEvent.observe(viewLifecycleOwner, Observer {
            MyLog.e( "showDialogEvent  $it")
            if (it != null) {
                if (progressDialog != null) {
                    progressDialog?.show()
                } else {
                    progressDialog = ProgressDialog.newBuilder(activity).noClose().get()
                    progressDialog?.show()
                }
            } else {
                progressDialog?.dismiss()

            }

        })
        mViewModel.uiChangeLiveData.toastEvent.observe(viewLifecycleOwner, Observer {
            ToastUtils.showShort(it)
        })
    }


}