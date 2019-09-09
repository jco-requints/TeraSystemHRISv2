package com.example.bottomnavigation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.terasystemhris.AccountDetails
import com.example.terasystemhris.LogsFragment
import com.example.terasystemhris.R
import kotlinx.android.synthetic.main.fragment_addtimelogsuccess.view.*
import kotlinx.android.synthetic.main.fragment_main.*

class AddTimeLogSuccessFragment : Fragment() {

    private var myDetails: AccountDetails = AccountDetails("","","","","","","","")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bundle = this.arguments
        var logType = 0
        var currentTime = ""
        if (bundle != null)
        {
            logType = bundle.getInt("logType")
            currentTime = bundle.getString("currentTime")!!
            myDetails = bundle.getParcelable("keyAccountDetails")!!
        }
        activity?.title = ""
        val view = inflater.inflate(R.layout.fragment_addtimelogsuccess, container, false)
        activity?.toolbar_title?.text = getString(R.string.addtimelogsuccess_title)
        activity?.toolbar_button?.text = null
        activity?.backBtn?.visibility = View.GONE
        activity?.toolbar_button?.visibility = View.GONE
        if(logType == 1)
        {
            view.leaveType.text = getString(R.string.time_in_text)
        }
        else if (logType == 2)
        {
            view.leaveType.text = getString(R.string.break_out_text)
        }
        else if(logType == 3)
        {
            view.leaveType.text = getString(R.string.break_in_text)
        }
        else
        {
            view.leaveType.text = getString(R.string.time_out_text)
        }
        view.time.text = currentTime
        view.okBtn?.setOnClickListener {
            val mBundle = Bundle()
            val fragmentManager = activity?.supportFragmentManager
            val fragment = LogsFragment()
            mBundle.putParcelable("keyAccountDetails", myDetails)
            fragment.arguments = mBundle
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.container, fragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        return view
    }
    companion object {
        val TAG: String = AddTimeLogSuccessFragment::class.java.simpleName
        fun newInstance(bundle: Bundle) = AddTimeLogSuccessFragment().apply {
            this.arguments = bundle
        }
    }
}