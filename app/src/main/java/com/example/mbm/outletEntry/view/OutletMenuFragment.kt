package com.example.mbm.outletEntry.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.mbm.R
import com.example.mbm.databinding.FragmentOutletMenuBinding


class OutletMenuFragment : Fragment() {
    private lateinit var binding: FragmentOutletMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOutletMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEntryOutlet.setOnClickListener {
            findNavController().navigate(
                R.id.action_outletMenuFragment_to_outletEntryFragment,
                bundleOf(
                    OutletEntryFragment.EXTRA_KEY1 to 1,
                    OutletEntryFragment.EXTRA_KEY2 to 2
                )
            )
        }

    }

}