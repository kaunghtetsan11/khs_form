package com.khs.form.form_features.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import com.khs.form.R
import com.khs.form.base.BaseFragment
import com.khs.form.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    override fun bindView(inflater: LayoutInflater): FragmentWelcomeBinding {
        return FragmentWelcomeBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            btnCrateAccount.setOnClickListener {
                findNavController().navigate(R.id.action_welcome_to_createAccount)
            }
        }
    }
}