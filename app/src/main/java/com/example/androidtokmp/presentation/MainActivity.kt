package com.example.androidtokmp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.androidtokmp.R
import com.example.androidtokmp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val viewModel: NumbersInfoViewModel by viewModel()
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            viewModel.error
                .collect {
                    Log.e(TAG, "Error: $it")
                    onError(it)
                }
        }
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle)
                .collect { state ->
                    Log.d(TAG, "State: $state")
                    when (state) {
                        is NumbersInfoState.Initial -> {
                            binding.progress.isGone = true
                        }

                        is NumbersInfoState.Loading -> {
                            binding.progress.isVisible = true
                        }

                        is NumbersInfoState.Success -> {
                            binding.progress.isGone = true
                            binding.numberTv.text = state.numberInfo.number.toString()
                            binding.descriptionTv.text = state.numberInfo.info
                        }

                        is NumbersInfoState.Error -> onError(state)
                    }
                }
        }

        viewModel.loadNumberInfo()

        binding.newNumberBtn.setOnClickListener {
            viewModel.generateNewNumber()
        }
        binding.previousNumberBtn.setOnClickListener {
            viewModel.loadPreviousNumber()
        }
    }

    private fun onError(errorState: NumbersInfoState.Error) {
        binding.progress.isGone = true
        showErrorSnackBar(errorState.message)
    }


    private fun showErrorSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).apply {
            setBackgroundTint(getColor(R.color.holo_red_dark))
        }.show()
    }
}