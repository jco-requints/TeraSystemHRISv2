package com.example.bottomnavigation.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentTransaction
import com.example.terasystemhris.*
import kotlinx.android.synthetic.main.fragment_addtimelog.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.json.JSONObject
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddTimeLogFragment : Fragment(), NetworkRequestInterface {

    private var myDetails: AccountDetails = AccountDetails("","","","","","","","")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bundle = this.arguments
        if (bundle != null)
        {
            myDetails = bundle.getParcelable("keyAccountDetails")!!
        }
        activity?.title = ""
        val view = inflater.inflate(R.layout.fragment_addtimelog, container, false)
        activity?.toolbar_title?.text = getString(R.string.addtimelog_title)
        activity?.toolbar_button?.text = getString(R.string.done_title)
        activity?.toolbar_button?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
        activity?.backBtn?.visibility = View.VISIBLE
        activity?.backBtn?.text = getString(R.string.cancel_title)
        activity?.backBtn?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14F)
        val adapter = ArrayAdapter.createFromResource(view.context,
            R.array.log_type, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.spinner?.adapter = adapter
        activity?.toolbar_button?.setOnClickListener {
            if (isConnected(container!!.context)) {
                val mURL = URL("http://222.222.222.71:9080/MobileAppTraining/AppTrainingAddTimeLog.htm").toString()
                val itemSelected = view.spinner.selectedItemPosition + 1
                FetchCredentials(this).execute(mURL, myDetails.username, itemSelected.toString())
            }
            else
            {
                view.popupHolder.visibility = View.VISIBLE
                view.network_status.text = getString(R.string.no_internet_message)
            }
        }

        view.txtclose?.setOnClickListener {
            view.popupHolder.visibility = View.GONE
        }

        activity?.backBtn?.setOnClickListener {
            val fragmentManager = activity?.supportFragmentManager
            fragmentManager?.popBackStackImmediate()
        }


        return view
    }

    override fun beforeNetworkCall() {
        view?.progressBar?.visibility = View.VISIBLE
    }

    override fun afterNetworkCall(result: String?) {
        view?.progressBar?.visibility = View.GONE
        if(result == "Connection Timeout")
        {
            view?.popupHolder?.visibility = View.VISIBLE
            view?.network_status?.text = getString(R.string.connection_timeout_message)
        }
        else if(result.isNullOrEmpty())
        {
            view?.popupHolder?.visibility = View.VISIBLE
            view?.network_status?.text = getString(R.string.server_error)
        }
        else
        {
            val jsonObject = JSONObject(result)
            val status = jsonObject?.get("status").toString()
            if(status == "0")
            {
                val itemSelected = view?.spinner?.selectedItemPosition?.plus(1)
                val mBundle = Bundle()
                val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDateTime.now()
                } else {
                    TODO("VERSION.SDK_INT < O")
                }
                val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    DateTimeFormatter.ofPattern("h:mm a")
                } else {
                    TODO("VERSION.SDK_INT < O")
                }
                val formatted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    current.format(formatter)
                } else {
                    TODO("VERSION.SDK_INT < O")
                }
                val fragmentManager = activity?.supportFragmentManager
                val fragment = AddTimeLogSuccessFragment()
                mBundle.putInt("logType", itemSelected!!)
                mBundle.putString("currentTime", formatted)
                mBundle.putParcelable("keyAccountDetails", myDetails)
                fragment.arguments = mBundle
                val fragmentTransaction = fragmentManager?.beginTransaction()?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                fragmentTransaction?.replace(R.id.container, fragment)
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
            }
            else
            {
                view?.popupHolder?.visibility = View.VISIBLE
                view?.network_status?.text = getString(R.string.logs_update_error_message)
            }
        }
    }

    companion object {
        val TAG: String = AddTimeLogFragment::class.java.simpleName
        fun newInstance(bundle: Bundle) = AddTimeLogFragment().apply {
            this.arguments = bundle
        }
    }

    fun isConnected(context: Context): Boolean {
        val connectivity = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null)
                for (i in info)
                    if (i.state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
        }
        return false
    }

}