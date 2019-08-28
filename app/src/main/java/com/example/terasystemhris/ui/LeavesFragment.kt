package com.example.bottomnavigation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.terasystemhris.R
import kotlinx.android.synthetic.main.fragment_main.*

class LeavesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.title = ""
        val view = inflater.inflate(R.layout.fragment_leaves, container, false)
        activity?.toolbar_title?.text = getString(R.string.leaves_title)
        return view
    }

    companion object {
        val TAG: String = LeavesFragment::class.java.simpleName
        fun newInstance() = LeavesFragment()
    }

}