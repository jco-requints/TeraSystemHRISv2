package com.example.bottomnavigation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.terasystemhris.R
import kotlinx.android.synthetic.main.fragment_main.*

class LogDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.title = ""
        val view = inflater.inflate(R.layout.fragment_logdetails, container, false)
        activity?.toolbar_title?.text = getString(R.string.log_date)
        activity?.toolbar_button?.text = null
        return view
    }

    companion object {
        val TAG: String = LogDetailsFragment::class.java.simpleName
        fun newInstance(bundle: Bundle) = LogDetailsFragment().apply {
            this.arguments = bundle
        }
    }

}