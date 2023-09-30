package com.example.notemaking.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.notemaking.R
import com.example.notemaking.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.e("Testing", "onCreate MainActivity")
        addFragment(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        Log.e("Testing", "onStart MainActivity")
    }

    override fun onResume() {
        super.onResume()
        Log.e("Testing", "onResume MainActivity")
    }

    override fun onPause() {
        super.onPause()
        Log.e("Testing", "onPause MainActivity")
    }

    override fun onStop() {
        super.onStop()
        Log.e("Testing", "onStop MainActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("Testing", "onDestroy MainActivity")
    }

    private fun addFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            Log.e("Testing", "addFragment MainActivity")
            val noteFragment = ShowAllNotesFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, noteFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
