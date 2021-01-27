package com.example.hashgeneratorapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hashgeneratorapp.databinding.FragmentSuccessBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log

class SuccessFragment : Fragment() {

    private val args: SuccessFragmentArgs by navArgs()

    private var _binding: FragmentSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuccessBinding.inflate(layoutInflater)

        binding.hashTextView.text = args.hash

        binding.copyButton.setOnClickListener {
            onCopyClicked()
        }
        
        return binding.root
    }

    private fun onCopyClicked() {
        lifecycleScope.launch {
            copyToClickboard(args.hash)
            applyAnimations()
        }
    }

    private fun copyToClickboard(hash: String) {
        val clipboardManager = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Texte crypté", hash)
        clipboardManager.setPrimaryClip(clipData)
    }

    private suspend fun applyAnimations() {
        binding.include.messageBg.animate().translationY(80f).duration = 200L
        binding.include.messageTextView.animate().translationY(80f).duration = 200L

        delay(2000L)

        binding.include.messageBg.animate().translationY(-80f).duration = 500L
        binding.include.messageTextView.animate().translationY(-80f).duration = 500L

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}