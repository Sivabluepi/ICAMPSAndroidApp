package com.bluepi.icampsandroid.ui.devicednainfopocsample

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluepi.icampsandroid.R
import com.bluepi.icampsandroid.ui.devicednainfopocsample.adapters.WifiNWDetailsPageAdapter
import com.bluepi.icampsandroid.ui.devicednainfopocsample.listner.CellClickListener

class WifiListActivity : AppCompatActivity(), CellClickListener {
    private var statusProgressBar: LinearLayoutCompat?= null
    private var recyclerView: RecyclerView ?= null
    private var wifiManager: WifiManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val title:String = intent.getStringExtra("TITLE_KEY").toString()
        setTitle(title)
        recyclerView = findViewById(R.id.recyclerView)
        statusProgressBar = findViewById(R.id.statusProgressBar)
        recyclerView?.visibility = View.GONE
        statusProgressBar?.visibility = View.VISIBLE
        wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        if (wifiManager?.isWifiEnabled == false) {
            Toast.makeText(this, "Turning WiFi ON...", Toast.LENGTH_LONG).show()
            wifiManager?.isWifiEnabled = true
        }
    }

    private fun fetchList(title:String): ArrayList<PInfo> {
        return if(title == "Side Loaded Apps List"){
            AppUtils.getSideLoadedPackages(this)
        } else {
            AppUtils.getInstalledApps(false, this)
        }

    }

    override fun onResume() {
        super.onResume()

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        registerReceiver(receiverWifi, intentFilter)
        getWifi()
    }

    override fun onCellClickListener(data: Any) {

    }

    private fun getWifi(){
        // Network scanning requires access to device location, but we first need to check
        // whether the user has given its permission to use it.
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            }
        } else {
            wifiManager?.startScan()
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiverWifi)
    }

    private val receiverWifi = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val wifiMan = context!!.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val scanResults = wifiMan.scanResults
            update(scanResults)
        }
    }

    private fun update(scanResults: List<ScanResult>) {
        recyclerView?.visibility = View.VISIBLE
        statusProgressBar?.visibility = View.GONE
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = WifiNWDetailsPageAdapter(this, scanResults, this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        ) ==
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                        wifiManager?.startScan()
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}