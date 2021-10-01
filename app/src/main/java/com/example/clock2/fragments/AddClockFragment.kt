package com.example.clock2.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.clock2.R
import com.example.clock2.databinding.FragmentAddClockBinding

class AddClockFragment : Fragment() {

    private lateinit var binding: FragmentAddClockBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddClockBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
    }
}