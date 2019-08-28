package com.example.bottomnavigation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.terasystemhris.R


class ClientsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.title = getString(R.string.clients_title)
        val view = inflater.inflate(R.layout.fragment_clients, container, false)
        return view
    }

    companion object {
        val TAG: String = ClientsFragment::class.java.simpleName
        fun newInstance() = ClientsFragment()
    }

}