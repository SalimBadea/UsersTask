package com.salem.base.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.salem.base.util.Inflate
import com.salem.base.util.hideKeyboard
import com.salem.domain.repository.PreferenceRepository
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment(){

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    private var _binding: VB? = null
    open val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startObserver()
        receiveData()
        handleClicks()
        onViewCreated()
    }

    open fun startObserver() {}

    abstract fun onViewCreated()

    open fun handleClicks() {}

    open fun receiveData() {}

    open fun onFailAll() {}

    override fun onPause() {
        super.onPause()

        val activity = activity ?: return
        val view = activity.currentFocus ?: return
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        requireView().hideKeyboard()
        _binding = null
    }
}