package com.future.components.client.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.future.components.client.base.delegate.LFragment
import com.future.components.client.base.delegate.LView
import com.future.components.client.base.delegate.inflateBindingWithGeneric
import com.future.components.client.ext.getVmClazz
import com.future.components.client.viewmodel.BaseViewModel

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/4/26 9:59
 */
abstract class BaseFragment<VB: ViewBinding,VM: BaseViewModel>:Fragment(), LFragment, LView {

    //该类绑定的ViewDataBinding
    private var _binding: VB? = null
    val viewBinding: VB get() = _binding!!
    lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBindingWithGeneric(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = createViewModel()
        initData(savedInstanceState)
        createObserver()
        observeMessage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        viewModel.messageLive.observe(viewLifecycleOwner) {

            if (it == null) { return@observe }

            if(it is Int||it is String){
                showMessage(if(it is String) it else resources.getString(it as Int))
            }

        }
    }
}