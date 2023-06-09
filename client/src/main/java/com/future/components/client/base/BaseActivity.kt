package com.future.components.client.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.future.components.client.base.delegate.LActivity
import com.future.components.client.base.delegate.LView
import com.future.components.client.base.delegate.inflateBindingWithGeneric
import com.future.components.client.ext.getVmClazz
import com.future.components.client.viewmodel.BaseViewModel

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/4/25 16:48
 */
abstract class BaseActivity<VB:ViewBinding,VM:BaseViewModel>:AppCompatActivity(), LActivity,LView{

    lateinit var viewBinding: VB
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initDataBinding())
        viewModel = createViewModel()
        initData(savedInstanceState)
        createObserver()
        observeMessage()
    }

    /**
     * 绑定ViewBinding
     */
    private fun initDataBinding(): View {
        viewBinding = inflateBindingWithGeneric(layoutInflater)
        return viewBinding.root
    }

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    /**
     * 监听消息
     */
    private fun observeMessage() {
        viewModel.messageLive.observe(this) {

            if (it == null) { return@observe }

            if(it is Int||it is String){
                showMessage(if(it is String) it else resources.getString(it as Int))
            }

        }
    }
}