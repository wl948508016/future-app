package com.future.components.client.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/4/25 17:02
 */
open class BaseViewModel:ViewModel() {

    var messageLive= MutableLiveData<Any>()

    fun postMessage(message:Any?){
        messageLive.postValue(message)
    }

    override fun onCleared() {
        super.onCleared()
        //
    }
}