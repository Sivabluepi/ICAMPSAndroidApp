package com.bluepi.icampsandroid.ui.devicednainfopocsample.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bluepi.icampsandroid.R
import com.bluepi.icampsandroid.ui.devicednainfopocsample.PInfo
import com.bluepi.icampsandroid.ui.devicednainfopocsample.listner.CellClickListener
import java.util.ArrayList

class DetailsPageAdapter(private val context: Context,
                         private val list: ArrayList<PInfo>,
                         private val cellClickListener: CellClickListener
) : RecyclerView.Adapter<DetailsPageAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titleTV: TextView = view.findViewById(R.id.appTitle)
        val packageTV: TextView = view.findViewById(R.id.appPackageTitle)
        val versionTVLL: LinearLayout = view.findViewById(R.id.appVersionCodeLL)
        val versionNameTVLL: LinearLayout = view.findViewById(R.id.appVersionNameLL)
        val versionTV: TextView = view.findViewById(R.id.appVersionCode)
        val versionNameTV: TextView = view.findViewById(R.id.appVersionName)
        val appPublisherStoreNameLL: LinearLayout = view.findViewById(R.id.appPublisherStoreNameLL)
        val appPublisherStoreName: TextView = view.findViewById(R.id.appPublisherStoreName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_details_row_view,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.titleTV.text = data.appname
        holder.packageTV.text = data.pname
        if(data.versionCode == 0){
            holder.versionTVLL.visibility = View.GONE
        } else {
            holder.versionTVLL.visibility = View.VISIBLE
        }
        if(data.versionName.isEmpty()){
            holder.versionNameTVLL.visibility = View.GONE
        } else {
            holder.versionNameTVLL.visibility = View.VISIBLE
        }
        holder.versionTV.text = ""+data.versionCode
        holder.versionNameTV.text =  data.versionName
        if (data.publisherStoreName.isNotEmpty()){
            holder.appPublisherStoreNameLL.visibility = View.VISIBLE
            holder.appPublisherStoreName.text =  data.publisherStoreName
        } else {
            holder.appPublisherStoreNameLL.visibility = View.VISIBLE
            holder.appPublisherStoreName.text =  "-NA-"
        }
    }
}