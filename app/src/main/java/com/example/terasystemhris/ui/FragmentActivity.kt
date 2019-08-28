package com.example.terasystemhris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.bottomnavigation.extension.active
import com.example.bottomnavigation.extension.attach
import com.example.bottomnavigation.extension.detach
import com.example.bottomnavigation.helper.BottomNavigationPosition
import com.example.bottomnavigation.helper.createFragment
import com.example.bottomnavigation.helper.findNavigationPositionById
import com.example.bottomnavigation.helper.getTag
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentActivity : AppCompatActivity() {

    private val KEY_POSITION = "keyPosition"

    private var navPosition: BottomNavigationPosition = BottomNavigationPosition.LOGS

//    private lateinit var myDetails: AccountDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreSaveInstanceState(savedInstanceState)
        setContentView(R.layout.fragment_main)
//        val data = intent.extras
//        val accountDetails = data?.getParcelable<AccountDetails>("keyAccountDetails")!!
//        val profile_name = if(accountDetails.middleName != "")
//        {
//            ("${accountDetails.firstName} ${accountDetails.middleName} ${accountDetails.lastName}").toUpperCase()
//        }
//        else
//        {
//            ("${accountDetails.firstName} ${accountDetails.lastName}").toUpperCase()
//        }
//        name_text?.text = profile_name
//        val firstNameInitial = accountDetails.firstName[0].toString()
//        val lastNameInitial = accountDetails.lastName[0].toString()
//        profile_id?.text = accountDetails.empID
//        profile_email?.text = maskEmail(accountDetails.emailAddress)
//        profile_number?.text = maskMobileNumber(accountDetails.mobileNumber)
//        initials?.text = "$firstNameInitial$lastNameInitial"

        findViewById<Toolbar>(R.id.toolbar).apply {
            setSupportActionBar(this)
        }

        findViewById<BottomNavigationView>(R.id.bottom_navigation).apply {
            // This is required in Support Library 27 or lower:
            // bottomNavigation.disableShiftMode()
            active(navPosition.position) // Extension function
            setOnNavigationItemSelectedListener { item ->
                navPosition = findNavigationPositionById(item.itemId)
                switchFragment(navPosition)
            }
        }

        initFragment(savedInstanceState)
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
        else if(email_id!!.count() > 3)
        {
            masked_email = "$email_initials$hide_email"
        }
        return  masked_email
    }

    private fun maskMobileNumber(mobile: String?): String{
        val country_code: String
        val mobile_initials: String
        val hide_mobile: String
        var masked_mobile: String = ""
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

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(KEY_POSITION, navPosition.id)
        super.onSaveInstanceState(outState)
    }

    private fun restoreSaveInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.getInt(KEY_POSITION, BottomNavigationPosition.LOGS.id)?.also {
            navPosition = findNavigationPositionById(it)
        }
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        savedInstanceState ?: switchFragment(BottomNavigationPosition.LOGS)
    }

    private fun switchFragment(navPosition: BottomNavigationPosition): Boolean {
        return findFragment(navPosition).let {
            if (it.isAdded) return false
            supportFragmentManager.detach() // Extension function
            supportFragmentManager.attach(it, navPosition.getTag()) // Extension function
            supportFragmentManager.executePendingTransactions()
        }
    }

    private fun findFragment(position: BottomNavigationPosition): Fragment {
        return supportFragmentManager.findFragmentByTag(position.getTag()) ?: position.createFragment()
    }

    override
    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            super.onBackPressed()
            val intent = Intent(this@FragmentActivity, MainActivity::class.java).apply {
                this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            true
        } else super.onKeyDown(keyCode, event)
    }
}
