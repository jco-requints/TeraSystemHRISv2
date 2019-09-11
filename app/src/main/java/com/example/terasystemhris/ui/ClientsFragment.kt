package com.example.bottomnavigation.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.terasystemhris.AppBarController
import com.example.terasystemhris.R
import kotlinx.android.synthetic.main.fragment_main.*

class ClientsFragment : Fragment() {

    private var myInterface: AppBarController? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.title = ""
        val view = inflater.inflate(R.layout.fragment_clients, container, false)
        myInterface?.setTitle(getString(R.string.clients_title))
        myInterface?.setAddButtonTitle(null)
        myInterface?.setCancelButtonTitle(null)
        myInterface?.getCancelButton()?.visibility = View.GONE
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(context is AppBarController)
        {
            myInterface = context
        }
    }

    companion object {
        val TAG: String = ClientsFragment::class.java.simpleName
        fun newInstance(bundle: Bundle) = ClientsFragment().apply {
            this.arguments = bundle
        }
    }

}