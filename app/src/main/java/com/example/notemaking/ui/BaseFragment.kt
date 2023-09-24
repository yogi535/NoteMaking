package com.example.notemaking.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("Testing", "onCreate ${javaClass.simpleName}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("Testing", "onDestroyView ${javaClass.simpleName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("Testing", "onDestroy ${javaClass.simpleName}")
    }

    override fun onPause() {
        super.onPause()
        Log.e("Testing", "onPause ${javaClass.simpleName}")
    }

    override fun onResume() {
        super.onResume()
        Log.e("Testing", "onResume ${javaClass.simpleName}")
    }

    override fun onStart() {
        super.onStart()
        Log.e("Testing", "onStart ${javaClass.simpleName}")
    }

    override fun onStop() {
        super.onStop()
        Log.e("Testing", "onStop ${javaClass.simpleName}")
    }
}