package com.example.hashgeneratorapp

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.hashgeneratorapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentHomeBinding.inflate(layoutInflater)

        setHasOptionsMenu(true) // Ajouter le menu

        val hashAlgorithms = resources.getStringArray(R.array.hash_algorithms)
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.drop_down_items,
            hashAlgorithms)

        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}