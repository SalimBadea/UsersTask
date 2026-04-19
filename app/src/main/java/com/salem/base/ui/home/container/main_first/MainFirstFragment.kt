package com.salem.base.ui.home.container.main_first

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.salem.base.R
import com.salem.base.base.BaseFragment
import com.salem.base.databinding.FragmentMainFirstBinding
import com.salem.base.util.setToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFirstFragment : BaseFragment<FragmentMainFirstBinding>(FragmentMainFirstBinding::inflate) {

    private val viewModel: MainFirstViewModel by viewModels()
    private val adapter = UsersAdapter()

    override fun onViewCreated() {
        binding.iToolbar.setToolbar(R.string.Home, true)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun startObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.users.collect { users ->
                    adapter.submitList(users)
                }
            }
        }
    }
}