package com.example.bottomnavigation.ui

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.terasystemhris.*
import kotlinx.android.synthetic.main.fragment_addtimelog.view.network_status
import kotlinx.android.synthetic.main.fragment_addtimelog.view.popupHolder
import kotlinx.android.synthetic.main.fragment_addtimelog.view.progressBar
import kotlinx.android.synthetic.main.fragment_addtimelog.view.spinner
import kotlinx.android.synthetic.main.fragment_addtimelog.view.txtclose
import kotlinx.android.synthetic.main.fragment_fileleave.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class FileLeaveFragment : Fragment(), NetworkRequestInterface {

    private var myDetails: AccountDetails = AccountDetails("","","","","","","","")
    private lateinit var selectedTypeofLeave: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bundle = this.arguments
        if (bundle != null)
        {
            myDetails = bundle.getParcelable("keyAccountDetails")!!
        }
        activity?.title = ""
        val view = inflater.inflate(R.layout.fragment_fileleave, container, false)
        activity?.toolbar_title?.text = getString(R.string.fileleave_title)
        activity?.toolbar_button?.text = getString(R.string.done_title)
        activity?.toolbar_button?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
        activity?.backBtn?.visibility = View.VISIBLE
        activity?.backBtn?.text = getString(R.string.cancel_title)
        activity?.backBtn?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14F)

        //code to get current date
        val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("MMMM d yyyy")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val formatted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            current.format(formatter)
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        //code for Leave toggle
        selectedTypeofLeave = ""

        view.vlToggle.setOnClickListener {
            view.vlToggle.setBackgroundColor(Color.parseColor("#1D8ECE"))
            view.vlToggle.setTextColor(Color.parseColor("#FFFFFF"))

            view.slToggle.setBackgroundResource(R.drawable.toggleborder)
            view.slToggle.setTextColor(Color.parseColor("#FF858484"))
            selectedTypeofLeave = "1"
        }

        view.slToggle.setOnClickListener {
            view.slToggle.setBackgroundColor(Color.parseColor("#1D8ECE"))
            view.slToggle.setTextColor(Color.parseColor("#FFFFFF"))

            view.vlToggle.setBackgroundResource(R.drawable.toggleborder)
            view.vlToggle.setTextColor(Color.parseColor("#FF858484"))
            selectedTypeofLeave = "2"
        }


        //Code for spinner
        val adapter = ArrayAdapter.createFromResource(view.context,
            R.array.time_type, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.spinner?.adapter = adapter

        //code for default value of start and end date
        view.startDate.text = formatted
        view.endDate.text = formatted

        //code to hide or show end date
        view.spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                if (selectedItem == "Whole Day")
                {
                    getView()?.endDateHolder?.visibility = View.VISIBLE
                    getView()?.endDate?.text = formatted
                    getView()?.startDateSuccessTitle?.text = getString(R.string.start_date_title)
                }
                else
                {
                    getView()?.endDateHolder?.visibility = View.GONE
                    getView()?.endDate?.text = null
                    getView()?.startDateSuccessTitle?.text = getString(R.string.file_leave_date)
                }
            }

        }

        //code for Date Picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val wordMonths= arrayOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")

        view.startDate?.setOnClickListener {
            val dpd = DatePickerDialog(container!!.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                val wordMonth = wordMonths[monthOfYear]
                val date = "$wordMonth $dayOfMonth $year"
                this.view?.startDate?.text = date
            }, year, month, day)
            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }

        view.endDate?.setOnClickListener {
            val dpd = DatePickerDialog(container!!.context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                val wordMonth = wordMonths[monthOfYear]
                val date = "$wordMonth $dayOfMonth $year"
                this.view?.endDate?.text = date
            }, year, month, day)
            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }

        activity?.toolbar_button?.setOnClickListener {
            if (isConnected(container!!.context)) {
                var isDateValid = true
                val mURL = URL("http://222.222.222.71:9080/MobileAppTraining/AppTrainingAddLeave.htm").toString()
                if(!view.endDate.text.isNullOrEmpty())
                {
                    isDateValid = isDateValid(view.startDate.text.toString(), view.endDate.text.toString())
                }
                val itemSelected = view.spinner.selectedItemPosition + 1
                if(view.endDate.text != null && isDateValid && selectedTypeofLeave != "")
                {
                    FetchCredentials(this).execute(mURL, myDetails.username, selectedTypeofLeave, convertDateToStandardForm(view.startDate.text.toString()), convertDateToStandardForm(view.endDate.text.toString()), itemSelected.toString())
                }
                else if(view.endDate.text.isNullOrEmpty() || view.endDate.text == "" && selectedTypeofLeave != "")
                {
                    FetchCredentials(this).execute(mURL, myDetails.username, selectedTypeofLeave, view.startDate.text.toString(), null, itemSelected.toString())
                }
                else if(selectedTypeofLeave == "")
                {
                    view.popupHolder.visibility = View.VISIBLE
                    view.network_status.text = getString(R.string.no_type_selected_error)
                }
                else if(view.endDate.text != null && !isDateValid)
                {
                    view.popupHolder.visibility = View.VISIBLE
                    view.network_status.text = getString(R.string.date_error)
                }
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
                val fragmentManager = activity?.supportFragmentManager
                val fragment = FileLeaveSuccessFragment()
                mBundle.putString("typeOfLeave", selectedTypeofLeave)
                mBundle.putInt("time", itemSelected!!)
                mBundle.putString("startDate", view?.startDate?.text.toString())
                mBundle.putString("endDate", view?.endDate?.text.toString())
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
                view?.network_status?.text = getString(R.string.leave_update_error_message)
            }
        }
    }

    private fun convertDateToStandardForm(date: String): String {

        val currentFormatOfDateToConvert = SimpleDateFormat("MMMM d yyyy")
        val formatToBeConvertedInto = SimpleDateFormat("yyyy-MM-dd")
        try {
            val stringParsedToDate = currentFormatOfDateToConvert.parse(date)
            return formatToBeConvertedInto.format(stringParsedToDate)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    private fun isDateValid(startDate: String, endDate: String): Boolean {
        val currentFormatOfDateToConvert = SimpleDateFormat("MMMM d yyyy")
        val startDateParsedToDate = currentFormatOfDateToConvert.parse(startDate)
        val endDateParsedToDate = currentFormatOfDateToConvert.parse(endDate)
        if(startDateParsedToDate.before(endDateParsedToDate) || (startDateParsedToDate.compareTo(endDateParsedToDate) == 0))
        {
            return true
        }
        return false
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