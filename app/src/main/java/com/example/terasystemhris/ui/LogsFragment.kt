package com.example.terasystemhris
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.*

class LogsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val roboto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            resources.getFont(R.font.roboto)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        activity?.title = ""
        val view = inflater.inflate(R.layout.fragment_logs, container, false)
        activity?.toolbar_title?.text = getString(R.string.logs_title)
        activity?.toolbar_button?.text = "+"
        activity?.toolbar_button?.typeface = roboto
        activity?.toolbar_button?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 42F)
        return view
    }

    companion object {
        val TAG: String = LogsFragment::class.java.simpleName
        fun newInstance(bundle: Bundle) = LogsFragment().apply {
            this.arguments = bundle
        }
    }


}
