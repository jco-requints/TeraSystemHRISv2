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
import com.example.bottomnavigation.ui.ClientsFragment
import com.example.bottomnavigation.ui.LeavesFragment
import com.example.bottomnavigation.ui.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentActivity : AppCompatActivity() {

    private val KEY_POSITION = "keyPosition"

    private var navPosition: BottomNavigationPosition = BottomNavigationPosition.LOGS

//    private lateinit var myDetails: AccountDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreSaveInstanceState(savedInstanceState)
        setContentView(R.layout.fragment_main)
        val data = intent.extras
        val accountDetails = data?.getParcelable<AccountDetails>("keyAccountDetails")!!
        val bundle = Bundle()
        bundle.putParcelable("keyAccountDetails", accountDetails)

        findViewById<Toolbar>(R.id.toolbar).apply {
            setSupportActionBar(this)
        }

        findViewById<BottomNavigationView>(R.id.bottom_navigation).apply {
            // This is required in Support Library 27 or lower:
            // bottomNavigation.disableShiftMode()
            active(navPosition.position) // Extension function
            setOnNavigationItemSelectedListener { item ->
                navPosition = findNavigationPositionById(item.itemId)
                switchFragment(navPosition, bundle)
            }
        }

        initFragment(savedInstanceState, bundle)
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

    private fun initFragment(savedInstanceState: Bundle?, bundle: Bundle) {
        savedInstanceState ?: switchFragment(BottomNavigationPosition.LOGS, bundle)
    }

    private fun switchFragment(navPosition: BottomNavigationPosition, bundle: Bundle): Boolean {
        return findFragment(navPosition, bundle).let {
            if (it.isAdded) return false
            supportFragmentManager.detach() // Extension function
            supportFragmentManager.attach(it, navPosition.getTag()) // Extension function
            supportFragmentManager.executePendingTransactions()
        }
    }

    fun switchContent(id: Int, fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(id, fragment, fragment.toString())
        ft.addToBackStack(null)
        ft.commit()
    }

    private fun findFragment(position: BottomNavigationPosition, bundle: Bundle): Fragment {
        return supportFragmentManager.findFragmentByTag(position.getTag()) ?: position.createFragment(bundle)
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