package com.example.terasystemhris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.bottomnavigation.extension.active
import com.example.bottomnavigation.extension.attach
import com.example.bottomnavigation.extension.detach
import com.example.bottomnavigation.helper.BottomNavigationPosition
import com.example.bottomnavigation.helper.createFragment
import com.example.bottomnavigation.helper.findNavigationPositionById
import com.example.bottomnavigation.helper.getTag
import com.example.bottomnavigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_main.*

class FragmentActivity : AppCompatActivity(), AppBarController {

    private val KEY_POSITION = "keyPosition"

    private var navPosition: BottomNavigationPosition = BottomNavigationPosition.LOGS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreSaveInstanceState(savedInstanceState)
        setContentView(R.layout.fragment_main)
        this.title = null
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
        val ft = supportFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.replace(id, fragment, fragment.toString())
        ft.addToBackStack(null)
        ft.commit()
    }

    private fun findFragment(position: BottomNavigationPosition, bundle: Bundle): Fragment {
        return supportFragmentManager.findFragmentByTag(position.getTag()) ?: position.createFragment(bundle)
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        val data = intent.extras
        val accountDetails = data?.getParcelable<AccountDetails>("keyAccountDetails")!!
        val bundle = Bundle()
        val fragment = supportFragmentManager.findFragmentById(container.id)

        when (fragment) {
            is LogsFragment -> finish()

            is AddTimeLogFragment,
            is AddTimeLogSuccessFragment,
            is FileLeaveFragment,
            is FileLeaveSuccessFragment -> super.onBackPressed()

            else -> findViewById<BottomNavigationView>(R.id.bottom_navigation).apply {
                active(0) // Extension function
                val fragmentManager = supportFragmentManager
                val fragment = LogsFragment()
                bundle.putParcelable("keyAccountDetails", accountDetails)
                fragment.arguments = bundle
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.container, fragment)
                fragmentTransaction?.commit()
            }
        }
    }

    override fun setTitle(title: String) {
        title_toolbar.text = title
    }

    override fun setCancelButtonTitle(cancelTitle: String?) {
        backBtn.text = cancelTitle
    }

    override fun setAddButtonTitle(addTitle: String?) {
        toolbar_button.text = addTitle
    }

    override fun getToolBar(): Toolbar {
        return toolbar
    }

    override fun getCancelButton(): Button {
        return backBtn
    }

    override fun getAddButton(): Button {
        return toolbar_button
    }

    override fun getSupportFragmentManager(): FragmentManager {
        return super.getSupportFragmentManager()
    }

}