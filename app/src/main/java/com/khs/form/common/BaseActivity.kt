package com.khs.form.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding


abstract class BaseActivity<VDB : ViewDataBinding> : AppCompatActivity() {

    abstract val binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}