package com.example.opiekun

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Communicator : ViewModel(){

    val message1=MutableLiveData<Any>()
    val message = MutableLiveData<Any>()

    fun setMsgCommunicator(msg:String){
        message.value = msg
    }
    fun setMsgCommunicator1(msg:String){
        message1.value = msg
    }
}