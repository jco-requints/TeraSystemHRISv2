package com.example.terasystemhris

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
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
//            val fragment = LogDetailsFragment()
//            val fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager()
//            val fragmentTransaction = fragmentManager?.beginTransaction()?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            fragmentTransaction?.replace(R.id.content, fragment)
//            fragmentTransaction?.addToBackStack(null)
//            fragmentTransaction?.commit()

            val toast = Toast.makeText(
                v.context,
                "Item is clicked",
                Toast.LENGTH_SHORT
            )
            toast.show()

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

    }

}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}