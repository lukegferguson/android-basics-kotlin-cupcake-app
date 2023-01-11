package com.example.cupcake

import android.content.Context
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

        sharedViewModel.formatPhone()
        sharedViewModel.formatName()
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