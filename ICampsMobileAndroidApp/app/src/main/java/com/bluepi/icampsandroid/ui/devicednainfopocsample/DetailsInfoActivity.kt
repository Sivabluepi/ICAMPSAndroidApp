package com.bluepi.icampsandroid.ui.devicednainfopocsample

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bluepi.icampsandroid.R

class DetailsInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val title: String = intent.getStringExtra("TITLE_KEY").toString()
        setTitle(title)

        if (ContextCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(ACCESS_FINE_LOCATION), 1
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(ACCESS_FINE_LOCATION), 1
                )
            }
        }

        val deviceInfoTV: TextView = findViewById(R.id.deviceInfoTV)
        var resData = ""
        when (title) {
            "AllAboutDeviceDetails (OEM,Model,OS)" -> {
                resData = AppUtils.getAllDeviceDetails()
            }
            "OS Update-Patch Details" -> {
                resData = AppUtils.getDeviceOSUpdateDetails()
            }
            "Security Update & Expiry Date" -> {
                resData = AppUtils.getSecurityPatchLevelInfo()
            }
            "Device Rooted Status" -> {
                resData = AppUtils.getDeviceRootedOrNot()
            }
            "Usb Debugging(adb) Status" -> {
                resData = AppUtils.isUSBDebugAdbEnabledStatus(this)
            }
            "Developer Options(adb) Status" -> {
                resData = AppUtils.isDevelopmentSettingsEnabled(this)
            }
            "NFC Status" -> {
                resData = AppUtils.isNFCEnabledStatus(this)
            }
            "Bluetooth Status" -> {
                resData = AppUtils.isBluetoothEnabledStatus(this)
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    resData = AppUtils.isBluetoothEnabledStatus(this)
                }*/
            }
            "Wifi Status" -> {
                resData = AppUtils.checkWifiOnAndConnected(this)
            }
            "SOHO Router Details (Mac Address)" -> {
                resData = AppUtils.getMacId(this)
            }
            "SIM Lock Status" -> {
                resData = AppUtils.isSIMLocked(this)
            }
            "Screen Lock Status" -> {
                resData = AppUtils.isLockScreenDisabled(this)
            }
            "Password Status" -> {
                resData = AppUtils.isPasswordLockSecured(this)
            }
            "Google Play Store App info" -> {
                resData = AppUtils.getGooglePlayStoreAppInfo(this)
            }
            "Google Play Version info" -> {
                resData = AppUtils.getGooglePlayServicesVersionInfo(this)
            }
            "Default Browser Info" -> {
                resData = AppUtils.getDefaultBrowserDetails(this)
            }
            "Android WebView Component Version" -> {
                resData = AppUtils.getWebViewComponentVersionDetails(this)
            }
            "Device Uptime" -> {
                resData = AppUtils.getDeviceUpTime()
            }
            "Find Out Network Details" -> {
                // Write you code here if permission already given.
                resData = AppUtils.getFindOutConnectedNetworkMacAddress(this)
            }
        }
        deviceInfoTV.text = resData

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
                            ACCESS_FINE_LOCATION
                        ) ==
                                PackageManager.PERMISSION_GRANTED)
                    ) {
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