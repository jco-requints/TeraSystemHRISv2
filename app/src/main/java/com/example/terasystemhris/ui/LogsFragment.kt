package com.example.terasystemhris
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*

class LogsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.title = ""
        val view = inflater.inflate(R.layout.fragment_logs, container, false)
        activity?.toolbar_title?.text = getString(R.string.logs_title)
        activity?.toolbar_button?.setBackgroundResource(R.drawable.plus)
        activity?.toolbar_button?.width = 24
        activity?.toolbar_button?.height = 24
        return view
    }

    companion object {
        val TAG: String = LogsFragment::class.java.simpleName
        fun newInstance() = LogsFragment()
    }


}
