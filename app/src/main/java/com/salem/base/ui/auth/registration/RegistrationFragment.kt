package com.salem.base.ui.auth.registration

import android.view.View
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.salem.base.R
import com.salem.base.base.BaseFragment
import com.salem.base.databinding.FragmentRegistrationBinding
import com.salem.base.util.AutoCompleteUtil.setupAutoCompleteTextView
import com.salem.base.util.setToolbar
import com.salem.domain.entity.Gender
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment :
    BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate) {

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onViewCreated() {
        binding.iToolbar.setToolbar(R.string.new_registration, false)
        setupGenderAdapter()
    }

    override fun startObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.navigateToHome.collect {
                        clearFields()
                        findNavController().navigate(R.id.action_global_mainFirstFragment)
                    }
                }
                launch {
                    viewModel.validationErrors.collect { v ->
                        binding.tilFirstName.error = v.nameError?.let { getString(it) }
                        binding.tilAge.error = v.ageError?.let { getString(it) }
                        binding.tilJob.error = v.jobError?.let { getString(it) }
                        binding.tvGenderError.text = v.genderError?.let { getString(it) }
                        binding.tvGenderError.visibility = if (v.genderError != null) View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }

    override fun handleClicks() {
        super.handleClicks()

        with(binding) {
            btnRegister.setOnClickListener {
                register()
            }
        }
    }

    private fun setupGenderAdapter() {
        binding.apply {
            //Setup gender adapter
            setupAutoCompleteTextView(
                tilGender.editText as AutoCompleteTextView,
                viewModel.genders.map { getString(it) },
                null,
                onSelectValue = { _, position ->
                    when (position) {
                        Gender.MALE.ordinal -> Gender.MALE.getGenderId()
                        Gender.FEMALE.ordinal -> Gender.FEMALE.getGenderId()
                    }
                })
        }
    }

    private fun clearFields() {
        with(binding) {
            tilFirstName.editText?.text?.clear()
            tilAge.editText?.text?.clear()
            tilJob.editText?.text?.clear()
            tilGender.editText?.text?.clear()
        }
    }

    private fun register() {
        with(binding) {
            viewModel.register(
                name = tilFirstName.editText?.text.toString().trim(),
                age = tilAge.editText?.text.toString().trim(),
                jobTitle = tilJob.editText?.text.toString().trim(),
                gender = tilGender.editText?.text.toString().trim(),
            )
        }
    }

}