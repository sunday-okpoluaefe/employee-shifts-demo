package com.sunday.shiftsapplication.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sunday.shiftsapplication.R
import com.sunday.shiftsapplication.databinding.FragmentShiftsBinding
import com.sunday.shiftsapplication.ui.adapter.ShiftsAdapter
import com.sunday.shiftsapplication.ui.viewmodel.ShiftViewModel
import com.sunday.shiftsapplication.ui.viewmodel.ShiftViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ShiftsFragment : Fragment() {

    private var _binding: FragmentShiftsBinding? = null

    @Inject
    lateinit var viewModelFactory: ShiftViewModelFactory

    private val adapter = ShiftsAdapter()

    private val vm by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(ShiftViewModel::class.java)
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentShiftsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        this.binding.recyclerView.adapter = this.adapter

        this.binding.swipeContainer.setOnRefreshListener {
            this.vm.loadShifts()
        }

        setupObservers()
        return binding.root
    }

    private fun setupObservers(){
        this.vm.shifts.observe(this, Observer {
            this.adapter.load(it)
        })

        this.vm.loading.observe(this, Observer {
            this.binding.swipeContainer.isRefreshing = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shift_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_shift ->  findNavController().navigate(R.id.action_shiftFragment_to_newShiftFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}