package com.example.driverapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.driverapp.R
import com.example.driverapp.viewModel.HistoryViewModel


class MyHistoryAdapter(var context: Context): RecyclerView.Adapter<MyHistoryAdapter.ViewHolder>(){
    var onItemClick:((Int) -> Unit)? = null
    inner class ViewHolder(listItemView: View): RecyclerView.ViewHolder(listItemView){
        var date_book_car: TextView = listItemView.findViewById(R.id.date_book_car)
        var time_book_car: TextView = listItemView.findViewById(R.id.time_book_car)
        var pick_me_address: TextView = listItemView.findViewById(R.id.pick_me_address)
        var destinaton_address: TextView = listItemView.findViewById(R.id.destinaton_address)
        init {
            listItemView.setOnClickListener { onItemClick?.invoke(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val serviceView = inflater.inflate(R.layout.history_item, parent, false)
        return ViewHolder(serviceView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date_book_car.text = "20/11/2022"
//        holder.time_book_car.text = serviceList.get(position).servicePhoneNumber
//        holder.pick_me_address.text = serviceList.get(position).servicePhoneNumber
//        holder.destinaton_address.text = serviceList.get(position).servicePhoneNumber
    }

    override fun getItemCount(): Int {
        return 5
    }



}


class HistoryFragment : Fragment() {


    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val historyViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)

        val rootView = inflater.inflate(R.layout.fragment_history, container, false)

        val recyclerView_history = rootView.findViewById<RecyclerView>(R.id.history_recycler_view)
//        emptyServiceTextView = rootView.findViewById(R.id.service_empty_text_view)
//        serviceViewModel.setServiceList()
        val adapter = MyHistoryAdapter(requireContext())
        recyclerView_history.adapter = adapter
        adapter.onItemClick = {position ->
            val bundle = Bundle()
//            bundle.putString("serviceID", serviceViewModel.selectedServiceList.value!!.get(position).serviceID)
            findNavController().navigate(R.id.action_nav_history_to_nav_detail_transport, bundle)
        }
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}