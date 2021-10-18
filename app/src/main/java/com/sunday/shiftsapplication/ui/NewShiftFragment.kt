package com.sunday.shiftsapplication.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sunday.shiftsapplication.R
import com.sunday.shiftsapplication.data.factory.ColorFactory
import com.sunday.shiftsapplication.data.factory.EmployeeFactory
import com.sunday.shiftsapplication.data.factory.RoleFactory
import com.sunday.shiftsapplication.databinding.FragmentNewShiftBinding
import com.sunday.shiftsapplication.ui.viewmodel.ShiftViewModel
import com.sunday.shiftsapplication.ui.viewmodel.ShiftViewModelFactory
import com.sunday.shiftsapplication.utils.format
import com.sunday.shiftsapplication.utils.snack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class NewShiftFragment : Fragment() {

    private var _binding: FragmentNewShiftBinding? = null

    @Inject
    lateinit var viewModelFactory: ShiftViewModelFactory

    private val vm by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(ShiftViewModel::class.java)
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = FragmentNewShiftBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startDate.setOnClickListener {
            showDateTimePicker(::setStartDate)
        }

        binding.endDate.setOnClickListener {
            showDateTimePicker(::setEndDate)
        }

        setupMultiChoice()
        setupListeners()
    }

    private fun observeButton(){
        lifecycleScope.launch {
            vm.isSubmitEnabled.collect {
                binding.save.isEnabled = it
                requireActivity().toolbar.menu.findItem(R.id.create_shift).isVisible = it
            }
        }
    }

    private fun setupListeners(){

        binding.employee.addTextChangedListener {
            vm.setEmployee(it.toString())
        }

        binding.role.addTextChangedListener {
            vm.setRole(it.toString())
        }

        binding.color.addTextChangedListener {
            vm.setColor(it.toString())
        }

        binding.save.setOnClickListener {
            saveShift()
        }

        this.vm.success.observe(this, androidx.lifecycle.Observer {
            binding.container.snack("Shift has been saved successfully")
        })
    }

    private fun saveShift(){
        vm.createShift()
        resetForm()
    }

    private fun setupMultiChoice(){
        /// setup employee items
        val employeeAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, EmployeeFactory.list())
        binding.employee.setAdapter(employeeAdapter)

        /// setup role items
        val roleAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, RoleFactory.list())
        binding.role.setAdapter(roleAdapter)

        /// setup color items
        val colorAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, ColorFactory.list())
        binding.color.setAdapter(colorAdapter)
    }

   private fun setStartDate(date: Calendar){
        binding.startDate.setText(date.format())
       this.vm.setStartDate(date.timeInMillis)
    }

    private fun setEndDate(date: Calendar){
        binding.endDate.setText(date.format())
        this.vm.setEndDate(date.timeInMillis)
    }

    private fun showDateTimePicker(onSet: (date: Calendar)-> Unit){
        val calender = Calendar.getInstance()
        val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calender.apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, day)
            }

            val timeListener =  TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calender.apply {
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                }
                onSet(calender)
            }

            TimePickerDialog(requireActivity(), timeListener, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), false ).show()
        }

        DatePickerDialog(requireActivity(), listener, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun resetForm(){
        binding.startDate.text.clear()
        binding.endDate.text.clear()
        binding.employee.text.clear()
        binding.role.text.clear()
        binding.color.text.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_menu, menu)
        observeButton()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.create_shift -> saveShift()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}