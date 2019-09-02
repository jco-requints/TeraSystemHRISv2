package com.example.bottomnavigation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.terasystemhris.Logs
import com.example.terasystemhris.R
import kotlinx.android.synthetic.main.fragment_logdetails.view.*
import kotlinx.android.synthetic.main.fragment_main.*

class LogDetailsFragment : Fragment() {

    private var logDetails: Logs = Logs("","","","","","")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bundle = this.arguments
        if (bundle != null)
        {
            logDetails = bundle.getParcelable("item_selected_key")!!
        }
        activity?.title = ""
        val view = inflater.inflate(R.layout.fragment_logdetails, container, false)
        activity?.toolbar_title?.text = logDetails.date
        activity?.toolbar_button?.text = null
        view.timeIn?.text = logDetails.timeIn
        view.timeOut?.text = logDetails.timeOut
        view.breakIn?.text = logDetails.breakIn
        view.breakOut?.text = logDetails.breakOut
        return view
    }

    companion object {
        val TAG: String = LogDetailsFragment::class.java.simpleName
        fun newInstance(bundle: Bundle) = LogDetailsFragment().apply {
            this.arguments = bundle
        }
    }

}