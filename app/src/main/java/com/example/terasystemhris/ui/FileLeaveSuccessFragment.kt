package com.example.bottomnavigation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.terasystemhris.AccountDetails
import com.example.terasystemhris.R
import kotlinx.android.synthetic.main.fragment_addtimelogsuccess.view.leaveType
import kotlinx.android.synthetic.main.fragment_addtimelogsuccess.view.okBtn
import kotlinx.android.synthetic.main.fragment_addtimelogsuccess.view.time
import kotlinx.android.synthetic.main.fragment_fileleavesuccess.view.*
import kotlinx.android.synthetic.main.fragment_main.*

class FileLeaveSuccessFragment : Fragment() {

    private var myDetails: AccountDetails = AccountDetails("","","","","","","","")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bundle = this.arguments
        var leaveType = ""
        var time = 0
        var startDate = ""
        var endDate = ""
        if (bundle != null)
        {
            leaveType = bundle.getString("typeOfLeave")!!
            time = bundle.getInt("time")
            startDate = bundle.getString("startDate")!!
            endDate = bundle.getString("endDate")!!
            myDetails = bundle.getParcelable("keyAccountDetails")!!
        }
        activity?.title = ""
        val view = inflater.inflate(R.layout.fragment_fileleavesuccess, container, false)
        activity?.toolbar_title?.text = getString(R.string.fileleavesuccess_title)
        activity?.toolbar_button?.text = null
        activity?.backBtn?.visibility = View.GONE
        activity?.toolbar_button?.visibility = View.GONE
        if(leaveType == "1")
        {
            view.leaveType.text = getString(R.string.vacation_leave_title)
        }
        else
        {
            view.leaveType.text = getString(R.string.sick_leave_title)
        }
        when (time) {
            1 -> view.time.text = "Whole Day"
            2 -> view.time.text = "Morning"
            else -> view.time.text = "Afternoon"
        }
        if(endDate == "" || endDate.isNullOrEmpty())
        {
            view.endDateTitle.visibility = View.GONE
            view.endDate.visibility = View.GONE
            view.startDateSuccessTitle.text = getString(R.string.file_leave_date)
            view.startDate.text = startDate
        }
        else
        {
            view.endDateTitle.visibility = View.VISIBLE
            view.endDate.visibility = View.VISIBLE
            view.startDate.text = startDate
            view.endDate.text = endDate
        }
        view.okBtn?.setOnClickListener {
            val mBundle = Bundle()
            val fragmentManager = activity?.supportFragmentManager
            val fragment = LeavesFragment()
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
        val TAG: String = FileLeaveSuccessFragment::class.java.simpleName
        fun newInstance(bundle: Bundle) = FileLeaveSuccessFragment().apply {
            this.arguments = bundle
        }
    }
}