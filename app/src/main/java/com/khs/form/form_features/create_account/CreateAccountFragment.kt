package com.khs.form.form_features.create_account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.khs.form.R
import com.khs.form.base.BaseFragment
import com.khs.form.databinding.FragmentCreateAccountBinding
import com.khs.form.utils.autoCleared
import com.khs.form.utils.hideSoftKeyboard

class CreateAccountFragment : BaseFragment<FragmentCreateAccountBinding>() {

    private val createAccountViewModel: CreateAccountViewModel by viewModels()
    private var nationalityAdp by autoCleared<ArrayAdapter<String>>()
    private var countryAdp by autoCleared<ArrayAdapter<String>>()

    override fun bindView(inflater: LayoutInflater): FragmentCreateAccountBinding {
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )
        return FragmentCreateAccountBinding.inflate(inflater).apply {
            viewModel = createAccountViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        setListeners()
        setAdapters()
    }

    private fun setListeners() {
        with(binding) {
            ivBack.setOnClickListener { findNavController().navigateUp() }
            rgGender.setOnCheckedChangeListener { radioGroup,radioButtonID ->
                val selectedRadioButton = radioGroup.findViewById<RadioButton>(radioButtonID)
                when (selectedRadioButton.text) {
                    "Female" -> createAccountViewModel.gender.value = 0
                    else -> createAccountViewModel.gender.value = 1
                }
            }
            btnCrateAccount.setOnClickListener {
                vwFocusable.requestFocus()
                if (createAccountViewModel.validate())
                    showSuccessDialog()
            }
        }
    }

    private fun setAdapters() {
        val nationalityAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            createAccountViewModel.nationalities
        )
        nationalityAdp = nationalityAdapter
        val countryAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            createAccountViewModel.countries
        )
        countryAdp = countryAdapter
        with(binding) {
            (tlNationality.editText as? AutoCompleteTextView)?.apply {
                setOnClickListener { vwFocusable.hideSoftKeyboard() }
                setAdapter(nationalityAdp)
            }
            (tlCountry.editText as? AutoCompleteTextView)?.apply {
                setOnClickListener { vwFocusable.hideSoftKeyboard() }
                setAdapter(countryAdp)
            }
        }
    }

    private fun showSuccessDialog() {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle(R.string.success)
            setMessage(R.string.account_created)
            setPositiveButton(R.string.text_ok) { _, _ -> }
            setCancelable(false)
        }.create().show()
    }
}