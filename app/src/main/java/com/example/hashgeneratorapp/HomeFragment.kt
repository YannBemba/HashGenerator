package com.example.hashgeneratorapp

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hashgeneratorapp.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

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

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {

        _binding = FragmentHomeBinding.inflate(layoutInflater)

        setHasOptionsMenu(true) // Ajouter le menu

        // Animations
        binding.generateBtn.setOnClickListener {
            onGenerateClick()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.clear_menu){
            binding.plainText.text.clear()
            showSnackbar("Cleared")
            return true
        }
        return true
    }

    private fun onGenerateClick() {
        if(binding.plainText.text.isEmpty()){
            showSnackbar("Veuillez remplir le champ")
        } else {
            lifecycleScope.launch {
                applyAnimations()
                navigateToSuccess(getHashData())
            }
        }
    }

    private fun getHashData(): String {
        val algorithms = binding.autoCompleteTextView.text.toString()
        val plainText = binding.plainText.text.toString()

        return homeViewModel.getHash(algorithms, plainText)
    }

    private suspend fun applyAnimations() {

        binding.generateBtn.isClickable = false

        val DURATION = 400L

        binding.titleTextView.animate()
            .alpha(0f)
            .duration = DURATION

        binding.messageBg.animate()
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

    private fun showSnackbar(message: String){
        val snackBar = Snackbar.make(
                binding.rootLayout,
                message,
                Snackbar.LENGTH_LONG
        )
        snackBar.setAction("OK"){}
        snackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
        snackBar.show()
    }

    private fun navigateToSuccess(hash: String){
        val directions = HomeFragmentDirections.actionHomeFragmentToSuccessFragment(hash)
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}