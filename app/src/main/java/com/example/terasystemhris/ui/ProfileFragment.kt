package com.example.bottomnavigation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.terasystemhris.AccountDetails
import com.example.terasystemhris.MainActivity
import com.example.terasystemhris.R
import com.example.terasystemhris.Update
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private var myDetails: AccountDetails = AccountDetails("","","","","","","","")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bundle = this.arguments
        if (bundle != null)
        {
            myDetails = bundle.getParcelable("keyAccountDetails")!!
        }
        activity?.title = ""
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        activity?.toolbar_title?.text = getString(R.string.profile_title)
        activity?.toolbar_button?.setBackgroundResource(0)
        activity?.toolbar_button?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
        activity?.toolbar_button?.text = getString(R.string.title_activity_logout)
        val profile_name = if(myDetails.middleName != "" && myDetails.middleName != "null")
        {
            ("${myDetails.firstName} ${myDetails.middleName} ${myDetails.lastName}").toUpperCase()
        }
        else
        {
            ("${myDetails.firstName} ${myDetails.lastName}").toUpperCase()
        }
        view.name_text?.text = profile_name
        val firstNameInitial = myDetails.firstName[0].toString()
        val lastNameInitial = myDetails.lastName[0].toString()
        view.profile_id?.text = myDetails.empID
        view.profile_email?.text = maskEmail(myDetails.emailAddress)
        view.profile_number?.text = maskMobileNumber(myDetails.mobileNumber)
        view.initials?.text = "$firstNameInitial$lastNameInitial"

        view.update_button.setOnClickListener {
            val intent = Intent(activity, Update::class.java).apply {
                this.putExtra("keyAccountDetails", myDetails)
            }
            startActivity(intent)
            (activity as Activity).overridePendingTransition(0, 0)
        }
        activity?.toolbar_button?.setOnClickListener{
            val intent = Intent(activity, MainActivity::class.java).apply {
                this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            (activity as Activity).overridePendingTransition(0, 0)
        }

        return view
    }

    companion object {
        val TAG: String = ProfileFragment::class.java.simpleName
        fun newInstance(bundle: Bundle) = ProfileFragment().apply {
            this.arguments = bundle
        }
    }

    private fun maskEmail(email: String?): String{
        val email_id: String? = email?.substringBeforeLast("@")
        var masked_email: String = ""
        val email_initials = email?.substring(0..3)
        val hide_email = email?.replaceBefore('@', "*****")
        if(email_id!!.count() <= 3)
        {
            masked_email = "$email_id$hide_email"
        }
        else if(email_id.count() > 3)
        {
            masked_email = "$email_initials$hide_email"
        }
        return  masked_email
    }

    private fun maskMobileNumber(mobile: String?): String{
        val country_code: String
        val mobile_initials: String
        val hide_mobile: String
        var masked_mobile = ""
        if(mobile?.count() == 13)
        {
            country_code = mobile.substring(0..2)
            mobile_initials = mobile.substring(3..5)
            hide_mobile = mobile.substring(9..12)
            masked_mobile = "$country_code $mobile_initials *** $hide_mobile"
        }
        else if(mobile?.count() == 11)
        {
            mobile_initials = mobile.substring(0..3)
            hide_mobile = mobile.substring(7..10)
            masked_mobile = "$mobile_initials *** $hide_mobile"
        }
        return  masked_mobile
    }

}