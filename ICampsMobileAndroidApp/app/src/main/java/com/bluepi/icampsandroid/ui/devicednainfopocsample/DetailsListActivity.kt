package com.bluepi.icampsandroid.ui.devicednainfopocsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluepi.icampsandroid.R
import com.bluepi.icampsandroid.ui.devicednainfopocsample.adapters.DetailsPageAdapter
import com.bluepi.icampsandroid.ui.devicednainfopocsample.listner.CellClickListener

class DetailsListActivity : AppCompatActivity(), CellClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val title: String = intent.getStringExtra("TITLE_KEY").toString()
        setTitle(title)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DetailsPageAdapter(this, fetchList(title), this)
    }

    private fun fetchList(title: String): ArrayList<PInfo> {
        return when (title) {
            "All Side Loaded Apps List" -> {
                AppUtils.getSideLoadedPackages(this)
            }
            "System Apps List" -> {
                AppUtils.getSystemApps(this)
            }
            "User Installed Apps List" -> {
                AppUtils.getUserInstalledApps(this)
            }
            "Publisher Store Data for Side Loaded Apps List" -> {
                AppUtils.getSideLoadsAppsWithPublisherStoreNames(this)
            }
            "Google PlayStore Apps List" -> {
                AppUtils.getGooglePlayStoreInstalledApps(this)
            }
            else -> {
                AppUtils.getInstalledApps(false, this)
            }
        }

    }

    override fun onCellClickListener(data: Any) {

    }
}