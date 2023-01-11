package com.example.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentUserdetailsBinding
import com.example.cupcake.model.OrderViewModel

/*
* [UserDetailsFragment] allows the user to enter name and phone number
* */
class UserDetailsFragment: Fragment() {

    private var binding: FragmentUserdetailsBinding? = null

    private val sharedViewModel: OrderViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentUserdetailsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply{
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            userDetailsFragment = this@UserDetailsFragment
        }

    }

    fun goToNextScreen(){
        findNavController().navigate(R.id.action_userDetailsFragment_to_summaryFragment)

        //Set user phone and name based on user entry
        sharedViewModel.setUserPhone(binding?.phoneInputEditText?.text.toString())
        sharedViewModel.setUserName(binding?.nameInputEditText?.text.toString())

        //Set text fields to display name/formatted number, or "None" if null
        binding!!.phoneInputEditText.setText(sharedViewModel.userPhone.value)
        binding!!.nameInputEditText.setText(sharedViewModel.userName.value)
    }

    fun cancelOrder(){
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_userDetailsFragment_to_startFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}