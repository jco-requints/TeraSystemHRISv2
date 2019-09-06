package com.example.terasystemhris

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.leaves_recyclerview_item_row.view.*

class LeavesRecyclerAdapter(private val leaves: ArrayList<Leaves>) : RecyclerView.Adapter<LeavesRecyclerAdapter.LeavesHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeavesHolder {
        val inflatedView = parent.inflate(R.layout.leaves_recyclerview_item_row, false)
        return LeavesHolder(inflatedView)
    }

    override fun getItemCount() = leaves.size

    override fun onBindViewHolder(holder: LeavesHolder, position: Int) {
        val itemLeaves = leaves[position]
        holder.bindLeaves(itemLeaves)
    }

    //1
    class LeavesHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        //2
        private var view: View = v
        private var leaves: Leaves? = null

        //3
        init {
            v.setOnClickListener(this)
        }

        //4
        override fun onClick(v: View) {
//            val item = leaves!!
//            fragmentJump(item)

//            val context = itemView.context
//            val showLogIntent = Intent(context, LogDetailsFragment::class.java)
//            showPhotoIntent.putExtra(LOGS_KEY, logs)
//            context.startActivity(showLogIntent)
        }

        companion object {
            //5
            private val LEAVES_KEY = "LEAVES"
        }

        fun bindLeaves(leaves: Leaves) {
            this.leaves = leaves
            val dateFrom: String
            val dateTo: String
            if(leaves.dateTo.isNullOrEmpty() || leaves.dateTo == "null")
            {
                dateFrom = leaves.dateFrom
                view.leaveDuration.text = dateFrom
                view.leaveType.text = leaves.type
                view.numberOfDays.text = leaves.time
            }
            else
            {
                dateFrom = leaves.dateFrom
                dateTo = leaves.dateTo
                view.leaveDuration.text = "$dateFrom to $dateTo"
                view.leaveType.text = leaves.type
                view.numberOfDays.text = leaves.time
            }

        }

//        fun fragmentJump(leaves: Leaves){
//            val mBundle = Bundle()
//            mBundle.putParcelable("item_selected_key", leaves)
//            val fragment = LogDetailsFragment()
//            fragment.arguments = mBundle
//            switchContent(R.id.container, fragment)
//            val fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager()
//            val fragmentTransaction = fragmentManager?.beginTransaction()?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            fragmentTransaction?.replace(R.id.content, fragment)
//            fragmentTransaction?.addToBackStack(null)
//            fragmentTransaction?.commit()
//        }

//        private fun switchContent(id: Int, fragment: Fragment) {
//            val context = itemView.context ?: return
//            if (context is FragmentActivity) {
//                context.switchContent(id, fragment)
//            }
//
//        }

    }

}