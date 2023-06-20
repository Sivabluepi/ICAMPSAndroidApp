package com.bluepi.icampsandroid.ui.devicednainfopocsample.adapters

import android.content.Context
import android.net.wifi.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bluepi.icampsandroid.R
import com.bluepi.icampsandroid.ui.devicednainfopocsample.listner.CellClickListener

class WifiNWDetailsPageAdapter(private val context: Context,
                               private val list: List<ScanResult>,
                               private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<WifiNWDetailsPageAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titleTV: TextView = view.findViewById(R.id.appTitle)
        val packageTV: TextView = view.findViewById(R.id.appPackageTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_wifi_row_view,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.titleTV.text = data.SSID
        holder.packageTV.text = data.BSSID
    }
}