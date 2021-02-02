package com.example.datastoreexample.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MainViewModelFactory(val context: Context) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainUIModel::class.java)){
            return MainUIModel(context) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }

}