package com.bluepi.icampsandroid.ui.devicednainfopocsample.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bluepi.icampsandroid.R
import com.bluepi.icampsandroid.ui.devicednainfopocsample.listner.CellClickListener

class MainPageAdapter(private val context: Context,
                      private val list: Array<String>,
                      private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<MainPageAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titleTV: TextView = view.findViewById(R.id.deviceDNAOptionTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_main_row_view,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title = list[position]
        holder.titleTV.text = title
        holder.titleTV.setOnClickListener {
            cellClickListener.onCellClickListener(title)
        }
    }
}