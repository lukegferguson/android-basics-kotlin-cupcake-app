/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.icu.number.Notation.simple
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentFlavorBinding
import com.example.cupcake.model.OrderViewModel

/**
 * [FlavorFragment] allows a user to choose a cupcake flavor for the order.
 */
class FlavorFragment : Fragment() {

    // Binding object instance corresponding to the fragment_flavor.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentFlavorBinding? = null

    //Initialize value for the shared ViewModel
    private val sharedViewModel: OrderViewModel by activityViewModels()

    //Initialize spinners
    private lateinit var vanillaSpinner: Spinner
    private lateinit var chocolateSpinner: Spinner
    private lateinit var redVelvetSpinner: Spinner
    private lateinit var saltedCaramelSpinner: Spinner
    private lateinit var coffeeSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentFlavorBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            flavorFragment = this@FlavorFragment
        }

        //Set number to be displayed in  flavor spinners based on quantity selected
        val flavorArray =  when (sharedViewModel.quantity.value) {
            1 -> R.array.one_array
            6 -> R.array.six_array
            else -> R.array.twelve_array
        }

        //Create an ArrayAdapter using the string array selected by flavorArray and a custom spinner layout
        fun spinnerAdapter(spinner: Spinner) {
            ArrayAdapter.createFromResource(
                requireContext(), flavorArray, android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(R.layout.spinner_layout)
                //apply the adapter to the spinner
                spinner.adapter = adapter

            }
        }

        //Associate spinners with their view and create ArrayAdapters
        vanillaSpinner = view.findViewById(R.id.vanilla_spinner)
        spinnerAdapter(vanillaSpinner)

        chocolateSpinner = view.findViewById(R.id.chocolate_spinner)
        spinnerAdapter(chocolateSpinner)

        redVelvetSpinner = view.findViewById(R.id.red_velvet_spinner)
        spinnerAdapter(redVelvetSpinner)

        saltedCaramelSpinner = view.findViewById(R.id.salted_caramel_spinner)
        spinnerAdapter(saltedCaramelSpinner)

        coffeeSpinner = view.findViewById(R.id.coffee_spinner)
        spinnerAdapter(coffeeSpinner)

    }



    /**
     * Navigate to the next screen to choose pickup date.
     */
    fun goToNextScreen() {
        findNavController().navigate(R.id.action_flavorFragment_to_pickupFragment)
    }

    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_flavorFragment_to_startFragment)
    }


    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}