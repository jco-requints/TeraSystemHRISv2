package com.example.terasystemhris
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_logs.*
import kotlinx.android.synthetic.main.fragment_logs.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.json.JSONObject
import java.net.URL
import android.R.attr.data
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class LogsFragment : Fragment(), NetworkRequestInterface {

    lateinit var logsList: Logs
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private var myDetails: AccountDetails = AccountDetails("","","","","","","","")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val roboto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            resources.getFont(R.font.roboto)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val bundle = this.arguments
        if (bundle != null)
        {
            myDetails = bundle.getParcelable("keyAccountDetails")!!
        }
        activity?.title = ""
        val view = inflater.inflate(R.layout.fragment_logs, container, false)
        val mURL = URL("http://222.222.222.71:9080/MobileAppTraining/AppTrainingGetTimeLogs.htm").toString()
        activity?.toolbar_title?.text = getString(R.string.logs_title)
        activity?.toolbar_button?.text = "+"
        activity?.toolbar_button?.typeface = roboto
        activity?.toolbar_button?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 42F)
        if (isConnected(container!!.context)) {
            FetchCredentials(this).execute(mURL, myDetails.username)
        }
        else
        {
            view.popupHolder.visibility = View.VISIBLE
            view.network_status.text = getString(R.string.no_internet_message)
        }
        return view
    }

    override fun beforeNetworkCall() {
        view?.progressBarHolder?.visibility = View.VISIBLE
    }

    override fun afterNetworkCall(result: String?) {
        view?.progressBarHolder?.visibility = View.GONE
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
                val logs = ArrayList<Logs>()
                val jsonArray = jsonObject.getJSONArray("timeLogs")
                for (i in 0 until jsonArray.length()) {
                    logsList = Logs("","","","","","")
                    val obj = jsonArray.getJSONObject(i)
                    logsList.userID = obj.getString("userID")
                    logsList.date = convertDateToHumanDate(obj.getString("date"))
                    logsList.timeIn = obj.getString("timeIn")
                    logsList.breakOut = obj.getString("breakOut")
                    logsList.breakIn = obj.getString("breakIn")
                    logsList.timeOut = obj.getString("timeOut")
                    logs.add(logsList)
                }
                linearLayoutManager = LinearLayoutManager(this.context)
                view?.recyclerView?.layoutManager = linearLayoutManager
                adapter = RecyclerAdapter(logs)
                view?.recyclerView?.adapter = adapter
            }
            else
            {
                view?.popupHolder?.visibility = View.VISIBLE
                view?.network_status?.text = getString(R.string.logs_error_message)
            }
        }
    }

    private fun convertDateToHumanDate(logDate: String): String {

        val humanDateFormat = SimpleDateFormat("MMMM dd")
        try {
            val parsedDateFormat = Date(logDate.toLong())
            val cal = Calendar.getInstance()
            cal.time = parsedDateFormat
            return humanDateFormat.format(cal.time)
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }
    }

    companion object {
        val TAG: String = LogsFragment::class.java.simpleName
        fun newInstance(bundle: Bundle) = LogsFragment().apply {
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