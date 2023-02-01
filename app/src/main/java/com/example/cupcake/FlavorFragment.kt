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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentFlavorBinding
import com.example.cupcake.model.OrderViewModel

/**
 *This is the first screen of the Cupcake app. [FlavorFragment] allows a user to choose cupcake flavors and quantities for the order.
 */
class FlavorFragment : Fragment() {

    // Binding object instance corresponding to the fragment_flavor.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentFlavorBinding? = null

    //Initialize value for the shared ViewModel
    private val sharedViewModel: OrderViewModel by activityViewModels()

    //Initialize spinners
    private lateinit var spinners: List<Spinner>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        //Create an ArrayAdapter with a custom spinner layout
        fun spinnerAdapter(spinner: Spinner) {
            ArrayAdapter.createFromResource(
                requireContext(), R.array.twelve_array, android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(R.layout.spinner_layout)
                //apply the adapter to the spinner
                spinner.adapter = adapter

            }
        }

        //Associate spinners with their view then create ArrayAdapters
        spinners = listOf(view.findViewById(R.id.vanilla_spinner),
            view.findViewById(R.id.chocolate_spinner),
            view.findViewById(R.id.red_velvet_spinner),
            view.findViewById(R.id.red_velvet_spinner),
            view.findViewById(R.id.salted_caramel_spinner),
            view.findViewById(R.id.coffee_spinner))

        for (spinner in spinners) spinnerAdapter(spinner)

    }



    /**
     * Navigate to the next screen to choose pickup date, if quantity is more than zero. Otherwise show toast/error
     */
    fun goToNextScreen() {
        val toast = Toast.makeText(requireContext(), R.string.quantity_toast, Toast.LENGTH_LONG)

        if (sharedViewModel.quantity.value!! <= 0) toast.show()
        else findNavController().navigate(R.id.action_flavorFragment_to_pickupFragment)
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