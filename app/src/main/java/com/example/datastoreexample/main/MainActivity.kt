package com.example.datastoreexample.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.datastoreexample.R
import com.example.datastoreexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val mUiModel by lazy {
        ViewModelProvider(this,MainViewModelFactory(this)).get(MainUIModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = mUiModel

        mUiModel.isSaveButtonClicked.observe(this, {
            if(it){
                Toast.makeText(
                    this,
                    "Data are saved!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        mUiModel.user.asLiveData().observe(this, { user ->
            if(user != null){
                mUiModel.nameObservable.set(user.name)
                mUiModel.emailObservable.set(user.email)
                mUiModel.isRegisterUser.set(user.isRegister)
            }
        })
    }
}