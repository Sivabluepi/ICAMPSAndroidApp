package com.bluepi.icampsandroid.ui.devicednainfopocsample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluepi.icampsandroid.R
import com.bluepi.icampsandroid.ui.devicednainfopocsample.DetailsInfoActivity
import com.bluepi.icampsandroid.ui.devicednainfopocsample.DetailsListActivity
import com.bluepi.icampsandroid.ui.devicednainfopocsample.WifiListActivity
import com.bluepi.icampsandroid.ui.devicednainfopocsample.adapters.MainPageAdapter
import com.bluepi.icampsandroid.ui.devicednainfopocsample.listner.CellClickListener
import kotlin.Any
import kotlin.Array
import kotlin.String
import kotlin.apply
import kotlin.arrayOf

class MainActivity : AppCompatActivity(), CellClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MainPageAdapter(this, fetchList(), this)
    }

    private fun fetchList(): Array<String> {
        return arrayOf(
            "AllAboutDeviceDetails (OEM,Model,OS)",
            "OS Update-Patch Details",
            "Security Update & Expiry Date",
            "List Of Apps Installed Package Name & Version",
            "System Apps List",
            "All Side Loaded Apps List",
            "Publisher Store Data for Side Loaded Apps List",
            "User Installed Apps List",
            "Google PlayStore Apps List",
            "Device Rooted Status",
            "Usb Debugging(adb) Status",
            "Developer Options(adb) Status",
            "NFC Status",
            "Bluetooth Status",
            "Wifi Status",
            "SOHO Router Details (Mac Address)",
            "SIM Lock Status",
            "Screen Lock Status",
            "Password Status",
            "Google Play Store App info",
            "Google Play Version info",
            "Default Browser Info",
            "Android WebView Component Version",
            "Device Uptime",
            "Find Out Network Details",
            "Saved & Available Wifi Networks"
        )
    }

    override fun onCellClickListener(data: Any) {
        val title = data.toString()
        Log.v("Selected option::>> ", title)
        when (title) {
            "List Of Apps Installed Package Name & Version",
            "System Apps List",
            "All Side Loaded Apps List",
            "User Installed Apps List",
            "Google PlayStore Apps List",
            "Publisher Store Data for Side Loaded Apps List"-> {
                // start your activity by passing the intent
                startActivity(Intent(this, DetailsListActivity::class.java).apply {
                    // you can add values(if any) to pass to the next class or avoid using `.apply`
                    putExtra("TITLE_KEY", title)
                })
            }
            "Saved & Available Wifi Networks" -> {
                // start your activity by passing the intent
                //AppUtils.getSavedWifiNetworks(this)
                startActivity(Intent(this, WifiListActivity::class.java).apply {
                    // you can add values(if any) to pass to the next class or avoid using `.apply`
                    putExtra("TITLE_KEY", title)
                })
            }
            else -> {
                // start your activity by passing the intent
                startActivity(Intent(this, DetailsInfoActivity::class.java).apply {
                    // you can add values(if any) to pass to the next class or avoid using `.apply`
                    putExtra("TITLE_KEY", title)
                })
            }
        }
    }
}