package com.example.hashgeneratorapp

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hashgeneratorapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        val hashAlgorithms = resources.getStringArray(R.array.hash_algorithms)
        val arrayAdapter = ArrayAdapter(
                requireContext(),
                R.layout.drop_down_items,
                hashAlgorithms
        )
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = FragmentHomeBinding.inflate(layoutInflater)

        setHasOptionsMenu(true) // Ajouter le menu


        // Animations

        binding.generateBtn.setOnClickListener {
            lifecycleScope.launch {
                applyAnimations()
                navigateToSuccess()
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    private suspend fun applyAnimations() {

        binding.generateBtn.isClickable = false

        val DURATION = 400L

        binding.titleTextView.animate()
            .alpha(0f)
            .duration = DURATION

        binding.view.animate()
            .alpha(0f)
            .duration = DURATION

        binding.generateBtn.animate()
            .alpha(0f)
            .duration = DURATION

        binding.textInputLayout.animate()
            .alpha(0f)
            .translationXBy(1200f)
            .duration = DURATION

        binding.plainText.animate()
            .alpha(0f)
            .translationXBy(-1200f)
            .duration = DURATION

        delay(300)

        binding.successBg.animate().alpha(1f).duration = 600L
        binding.successBg.animate().rotationBy(100f).duration = 600L
        binding.successBg.animate().scaleXBy(900f).duration = 800L
        binding.successBg.animate().scaleYBy(900f).duration = 600L

        delay(500)

        binding.successImg.animate().alpha(1f).duration = 2000L

        delay(1500L)

    }

    private fun navigateToSuccess(){
        findNavController().navigate(R.id.action_homeFragment_to_successFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}