package com.future.components.client.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.future.components.client.base.delegate.LFragment
import com.future.components.client.base.delegate.inflateBindingWithGeneric

/**
 *
 * @Description:    不强制要求实现ViewModel，大多数情况dialog都是简单逻辑操作
 * @Author:         future
 * @CreateDate:     2022/1/28 4:24 下午
 */
abstract class BaseOverDialog<VB: ViewBinding> : DialogFragment(), LFragment {

    lateinit var viewBinding: VB

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireActivity())
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = inflateBindingWithGeneric(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(savedInstanceState)
        initListener()
        createObserver()
    }

    override fun onStart() {
        super.onStart()
        val win = requireDialog().window
        win?.run {
            val params = attributes
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT
            attributes = params
        }
    }

    override fun onDestroyView() {
        if (view is ViewGroup) {
            (view as ViewGroup).removeAllViews()
        }
        super.onDestroyView()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().add(this,tag).commitAllowingStateLoss()
    }

    override fun dismiss() {
        dismissAllowingStateLoss()
    }

    /**
     * 初始化监听
     */
    abstract fun initListener()
}