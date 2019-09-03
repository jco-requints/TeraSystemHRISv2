package com.example.bottomnavigation.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.terasystemhris.AccountDetails
import com.example.terasystemhris.Logs
import com.example.terasystemhris.R
import kotlinx.android.synthetic.main.fragment_logdetails.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlin.math.log

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
        if(logDetails.timeIn.isNullOrEmpty() || logDetails.timeIn == "null")
        {
            view.timeIn?.text = "N/A"
            view.timeIn?.setTextColor(Color.parseColor("#FF0000"))
        }
        if(logDetails.timeOut.isNullOrEmpty() || logDetails.timeOut == "null")
        {
            view.timeOut?.text = "N/A"
            view.timeOut?.setTextColor(Color.parseColor("#FF0000"))
        }
        if(logDetails.breakIn.isNullOrEmpty() || logDetails.breakIn == "null")
        {
            view.breakIn?.text = "N/A"
            view.breakIn?.setTextColor(Color.parseColor("#FF0000"))
        }
        if(logDetails.breakOut.isNullOrEmpty() || logDetails.breakOut == "null")
        {
            view.breakOut?.text = "N/A"
            view.breakOut?.setTextColor(Color.parseColor("#FF0000"))
        }
        return view
    }

    companion object {
        val TAG: String = LogDetailsFragment::class.java.simpleName
        fun newInstance(bundle: Bundle) = LogDetailsFragment().apply {
            this.arguments = bundle
        }
    }

}