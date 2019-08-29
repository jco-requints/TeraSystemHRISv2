package com.example.terasystemhris

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activty_main.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity(), NetworkRequestInterface {

    lateinit var myDetails: AccountDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_main)
        val mURL = URL("http://222.222.222.71:9080/MobileAppTraining/AppTrainingLogin.htm").toString()
        login_button.setOnClickListener {
            if (userId_edit.text.trim().toString() == "")
            {
                userId_edit.error = "User ID is required"
            }
            if(password_edit.text.trim().toString() == "")
            {
                password_edit.error = "Password is required"
            }
            if(userId_edit.text.trim().toString() != "" && password_edit.text.trim().toString() != "")
            {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
                if (isConnected(this)) {
                    userId_edit.clearFocus()
                    password_edit.clearFocus()
                    FetchCredentials(this).execute(mURL, userId_edit.text.toString(), password_edit.text.toString())
                }
                else
                {
                    popupHolder.visibility = View.VISIBLE
                    network_status.text = getString(R.string.no_internet_message)
                }
            }
        }
        signup_button.setOnClickListener {
            val intent = Intent(this@MainActivity, WebView::class.java)
            startActivity(intent)
        }

        txtclose.setOnClickListener {
            popupHolder.visibility = View.GONE
        }

    }

    override fun beforeNetworkCall() {
        progressBarHolder.visibility = View.VISIBLE
    }

    override fun afterNetworkCall(result: String?) {
        progressBarHolder.visibility = View.GONE
        if (result == "Connection Timeout") {
            popupHolder.visibility = View.VISIBLE
            network_status.text = getString(R.string.connection_timeout_message)
        }
        else if(result.isNullOrEmpty())
        {
            popupHolder.visibility = View.VISIBLE
            network_status.text = getString(R.string.server_error)
        }
        else
        {
            var jsonObject = JSONObject(result)
            val status = jsonObject?.get("status").toString()
            if(status == "0")
            {
                jsonObject = jsonObject.getJSONObject("user")
                myDetails = AccountDetails("","","","","","","","")
                myDetails.username = jsonObject?.get("userID").toString()
                myDetails.empID = jsonObject?.get("idNumber").toString()
                myDetails.firstName = jsonObject?.get("firstName").toString()
                myDetails.middleName = jsonObject?.get("middleName").toString()
                myDetails.lastName = jsonObject?.get("lastName").toString()
                myDetails.emailAddress = jsonObject?.get("emailAddress").toString()
                myDetails.mobileNumber = jsonObject?.get("mobileNumber").toString()
                myDetails.landlineNumber = jsonObject?.get("landline").toString()
                val intent = Intent(this@MainActivity,
                    FragmentActivity::class.java).apply {
                    this.putExtra("keyAccountDetails", myDetails)
                }
                startActivity(intent)

            }
            else
            {
                val toast = Toast.makeText(
                    applicationContext,
                    "Invalid user name or password",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
    }

    override
    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KEYCODE_BACK) {
            super.onBackPressed()
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            true
        } else super.onKeyDown(keyCode, event)
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