package com.future.components.client.databinding

import androidx.databinding.ObservableField

/**
  *
  * @Description:
  * @Author:         future
  * @CreateDate:     2022/6/9 11:40
 */
class StringObservableField(value: String = "") : ObservableField<String>(value) {

    override fun get(): String {
        return if(super.get()==null) "" else super.get().toString()
    }

}