package com.example.datastoreexample

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.example.datastoreexample.interactors.ObservableString

class MainUIModel : ViewModel(){

    // Observables
    val nameObservable = ObservableString()
    val emailObservable = ObservableString()
    val isRegisterUser  = ObservableBoolean()
}