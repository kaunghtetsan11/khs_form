package com.khs.form.features.create_account

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

    private val _viewModel: CreateAccountViewModel by viewModels()
    private var _adapterNationality by autoCleared<ArrayAdapter<String>>()
    private var _adapterCountry by autoCleared<ArrayAdapter<String>>()

    override fun bindView(inflater: LayoutInflater): FragmentCreateAccountBinding {
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )
        return FragmentCreateAccountBinding.inflate(inflater).apply {
            viewModel = _viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        setListeners()
        setAdapters()
        setPhoneCodeByCountry()
    }

    private fun setListeners() {
        with(binding) {
            ivBack.setOnClickListener { findNavController().navigateUp() }
            rgGender.setOnCheckedChangeListener { radioGroup,radioButtonID ->
                val selectedRadioButton = radioGroup.findViewById<RadioButton>(radioButtonID)
                when (selectedRadioButton.text) {
                    "Female" -> _viewModel.gender.value = 0
                    else -> _viewModel.gender.value = 1
                }
            }
            btnCreate.setOnClickListener {
                vwFocusable.requestFocus()
                if (_viewModel.validate())
                    showSuccessDialog()
            }
        }
    }

    private fun setAdapters() {
        val nationalityAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            _viewModel.nationalities
        )
        _adapterNationality = nationalityAdapter
        val countryAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            _viewModel.countries
        )
        _adapterCountry = countryAdapter
        with(binding) {
            (tlNationality.editText as? AutoCompleteTextView)?.apply {
                setOnClickListener { vwFocusable.hideSoftKeyboard() }
                setAdapter(_adapterNationality)
            }
            (tlCountry.editText as? AutoCompleteTextView)?.apply {
                setOnClickListener { vwFocusable.hideSoftKeyboard() }
                setAdapter(_adapterCountry)
            }
        }
    }

    private fun setPhoneCodeByCountry()
    {
        with(_viewModel){
            country.observe(viewLifecycleOwner) {
                setPhoneCode(it!!)
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