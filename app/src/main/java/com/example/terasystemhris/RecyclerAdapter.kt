package com.example.terasystemhris

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigation.ui.LogDetailsFragment
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*


class RecyclerAdapter(private val logs: ArrayList<Logs>) : RecyclerView.Adapter<RecyclerAdapter.LogsHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item_row, false)
        return LogsHolder(inflatedView)
    }

    override fun getItemCount() = logs.size

    override fun onBindViewHolder(holder: LogsHolder, position: Int) {
        val itemLogs = logs[position]
        holder.bindLogs(itemLogs)
    }

    //1
    class LogsHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2
        private var view: View = v
        private var logs: Logs? = null

        //3
        init {
            v.setOnClickListener(this)
        }

        //4
        override fun onClick(v: View) {
            val item = logs!!
            fragmentJump(item)

//            val context = itemView.context
//            val showLogIntent = Intent(context, LogDetailsFragment::class.java)
//            showPhotoIntent.putExtra(LOGS_KEY, logs)
//            context.startActivity(showLogIntent)
        }

        companion object {
            //5
            private val LOGS_KEY = "LOGS"
        }

        fun bindLogs(logs: Logs) {
            this.logs = logs
            view.itemDate.text = logs.date
            view.itemTimeIn.text = logs.timeIn
            view.itemTimeOut.text = logs.timeOut
        }

        fun fragmentJump(logs: Logs){
            val mBundle = Bundle()
            mBundle.putParcelable("item_selected_key", logs)
            val fragment = LogDetailsFragment()
            fragment.arguments = mBundle
            switchContent(R.id.container, fragment)
//            val fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager()
//            val fragmentTransaction = fragmentManager?.beginTransaction()?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            fragmentTransaction?.replace(R.id.content, fragment)
//            fragmentTransaction?.addToBackStack(null)
//            fragmentTransaction?.commit()
        }

        private fun switchContent(id: Int, fragment: Fragment) {
            val context = itemView.context ?: return
            if (context is FragmentActivity) {
                context.switchContent(id, fragment)
            }

        }

    }

}