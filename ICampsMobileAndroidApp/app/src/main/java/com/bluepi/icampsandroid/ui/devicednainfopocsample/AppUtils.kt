package com.bluepi.icampsandroid.ui.devicednainfopocsample

import android.app.KeyguardManager
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.net.wifi.WifiManager
import android.nfc.NfcManager
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.SystemClock
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.text.format.Formatter
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.pm.PackageInfoCompat
import com.google.android.gms.common.GoogleApiAvailability
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.TimeUnit


object AppUtils {
    const val INSTALLER_ADB = "adb"
    const val INSTALLER_PACKAGE_INSTALLER_NOUGAT = "com.google.android.packageinstaller"
    const val INSTALLER_PACKAGE_INSTALLER_NOUGAT2 = "com.android.packageinstaller"
    fun getAllDeviceDetails(): String {
        val details = """
             MANUFACTURER : ${Build.MANUFACTURER}
            MODEL : ${Build.MODEL}
            VERSION.RELEASE : ${Build.VERSION.RELEASE}
            VERSION.INCREMENTAL : ${Build.VERSION.INCREMENTAL}
            VERSION.SDK.NUMBER : ${Build.VERSION.SDK_INT}
            BOARD : ${Build.BOARD}
            BRAND : ${Build.BRAND}
            CPU_ABI : ${Build.CPU_ABI}
            DISPLAY : ${Build.DISPLAY}
            FINGERPRINT : ${Build.FINGERPRINT}
            HARDWARE : ${Build.HARDWARE}
            HOST : ${Build.HOST}
            ID : ${Build.ID}
            PRODUCT : ${Build.PRODUCT}
            TAGS : ${Build.TAGS}
            TIME : ${Build.TIME}
            TYPE : ${Build.TYPE}
            USER : ${Build.USER}
            """.trimIndent()

        Log.d("Device Details", details)

        return details
    }

    fun getSecurityPatchLevelInfo(): String {
        //Date
        val securityInfo = SystemProperties.read("ro.build.version.security_patch")
        val details = """
            Sec Update expiry date(Last date till which we get updates) : $securityInfo
            """.trimIndent()

        Log.d("Security Details", details)

        return details
    }

    fun getDeviceRootedOrNot(): String {
        return when {
            DeviceRootUtil.isDeviceRooted() -> {
                "Device Rooted."
            }

            else -> {
                "Device Not Rooted."
            }
        }
    }

    fun isDevelopmentSettingsEnabled(context: Context): String {
        val resStatus = when {
            Build.VERSION.SDK_INT > VERSION_CODES.JELLY_BEAN -> {
                Settings.Secure.getInt(
                    context.contentResolver,
                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
                ) != 0
            }

            Build.VERSION.SDK_INT == VERSION_CODES.JELLY_BEAN -> {
                @Suppress("DEPRECATION")
                Settings.Secure.getInt(
                    context.contentResolver,
                    Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED, 0
                ) != 0
            }

            else -> false
        }

        val statusInfo = when {
            resStatus -> {
                "Enabled"
            }

            else -> {
                "Does not Enabled"
            }
        }

        val details = """
            Developer Options(adb) : $statusInfo
            """.trimIndent()
        return details
    }

    @Suppress("DEPRECATION")
    fun isUSBDebugAdbEnabledStatus(context: Context): String {
        var isDebuggable = false
        val reString = when {
            Settings.Secure.getInt(
                context.contentResolver,
                Settings.Secure.ADB_ENABLED,
                0
            ) == 1 -> {
                isDebuggable = true
                // debugging enabled
                "Enabled"
            }

            else -> {
                isDebuggable = false
                //;debugging does not enabled
                "Does not enabled"
            }
        }

        val details = """
            USB Debug(adb) : $reString
            USB Debug(adb) Enabled Status  : $isDebuggable
            """.trimIndent()

        Log.d("Device Details", details)

        return details
    }

    fun isNFCEnabledStatus(context: Context): String {
        val manager = context.getSystemService(Context.NFC_SERVICE) as NfcManager
        val nfcAdapter = manager.defaultAdapter
        //val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
        val resValue = when {
            nfcAdapter == null -> {
                "NFC is not available for device"

            }

            !nfcAdapter.isEnabled -> {
                "NFC is available for device but not enabled"
            }

            else -> {
                "NFC is enabled"
            }
        }

        return resValue
    }

    fun isBluetoothEnabledStatus(context: Context): String {

        /*val manager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val mBluetoothAdapter = manager.adapter*/
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        return when {
            mBluetoothAdapter == null -> {
                "Device does not support Bluetooth"
            }

            !mBluetoothAdapter.isEnabled -> {
                "Bluetooth is not enabled"
            }

            else -> {
                "Bluetooth is enabled"
            }
        }
    }

    @Suppress("DEPRECATION")
    fun checkWifiOnAndConnected(context: Context): String {
        val connManager: ConnectivityManager? =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val mWifi: NetworkInfo? = connManager?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return when (mWifi?.isConnected) {
            true -> {
                // Do whatever
                "Connected to Wifi"
            }

            else -> {
                "Not connected to Wifi"
            }
        }
    }

    @Suppress("DEPRECATION")
    fun getMacId(context: Context): String {
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?
        val wifiInfo = wifiManager!!.connectionInfo
        return wifiInfo.bssid
        /*val wifiManager =context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?
        val wifiInfo = wifiManager!!.connectionInfo
        // BSSID Known as a "Basic Service Set Identifier," the BSSID is basically the MAC physical address of the wireless router
        // or access point the user is using to connect via WiFi.
        // You can see the BSSID on Windows systems by running the command: netsh wlan show interfaces | find "BSSID"
        return wifiInfo.bssid*/
    }

    fun isPasswordLockSecured(context: Context): String {
        return when (LockTest.doesDeviceHaveSecuritySetup(context)) {
            true -> {
                "Password/Face/Pattern Protected."
            }

            else -> {
                "No Password/Face/Pattern"
            }
        }
    }

    fun isSIMLocked(context: Context): String {
        val telMgr =
            context.getSystemService(AppCompatActivity.TELEPHONY_SERVICE) as TelephonyManager
        val simState = telMgr.getSimState()
        val resValue = when (simState) {
            TelephonyManager.SIM_STATE_ABSENT -> {
                "SIM Absent"
            }

            TelephonyManager.SIM_STATE_NETWORK_LOCKED -> {
                "SIM Network locked"
            }

            TelephonyManager.SIM_STATE_PIN_REQUIRED -> {
                "SIM PIN required"
            }

            TelephonyManager.SIM_STATE_PUK_REQUIRED -> {
                "SIM PUK required"
            }

            TelephonyManager.SIM_STATE_READY -> {
                "SIM Ready"
            }

            TelephonyManager.SIM_STATE_UNKNOWN -> {
                "SIM Unknown"
            }

            else -> {
                "SIM State Unknown"
            }
        }

        return resValue
    }

    fun getGooglePlayStoreAppInfo(context: Context): String {
        val pm: PackageManager = context.packageManager
        return try {
            val pi = pm.getPackageInfo("com.android.vending", 0)
            Log.d("TAG", "version name: " + pi?.versionName)
            Log.d("TAG", "version code: " + pi?.versionCode)
            """
                Version Name : ${pi?.versionName}
                Version Code : ${pi?.versionCode}
                """.trimIndent()
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("TAG", "No Android Market App Installed")
            "No Android Market App Installed"
        }
    }

    fun getGooglePlayServicesVersionInfo(context: Context): String {
        var vaersionData = ""
        try {
            val versionDetails = PackageInfoCompat.getLongVersionCode(
                context.packageManager.getPackageInfo(
                    GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE,
                    0
                )
            )

            if(versionDetails > 0){
               vaersionData =  """
                Version Info : $versionDetails
                """.trimIndent()
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("TAG", "No Android Market App Installed")
            vaersionData = "No Android Market App Installed"
        }

        return vaersionData
    }

    fun getScreenLockStatus(context: Context): String {

        var status = ""
        val theFilter = IntentFilter()
        /** System Defined Broadcast */
        theFilter.addAction(Intent.ACTION_SCREEN_ON)
        theFilter.addAction(Intent.ACTION_SCREEN_OFF)
        theFilter.addAction(Intent.ACTION_USER_PRESENT)

        val screenOnOffReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val strAction = intent.action
                val myKM = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
                if (strAction == Intent.ACTION_USER_PRESENT || strAction == Intent.ACTION_SCREEN_OFF || strAction == Intent.ACTION_SCREEN_ON) if (myKM.inKeyguardRestrictedInputMode()) {
                    println("Screen off " + "LOCKED")
                    status = "Screen off " + "LOCKED"
                } else {
                    println("Screen off " + "UNLOCKED")
                    status = "Screen off " + "LOCKED"
                }
            }
        }
        context.applicationContext.registerReceiver(screenOnOffReceiver, theFilter)

        return status
    }

    fun getDefaultBrowserDetails(context: Context): String {
        val browserIntent = Intent("android.intent.action.VIEW", Uri.parse("http://"))
        val resolveInfo: ResolveInfo? =
            context.packageManager.resolveActivity(browserIntent, PackageManager.MATCH_DEFAULT_ONLY)
        // This is the default browser's packageName
        val packageName = resolveInfo?.activityInfo?.packageName
        val pm: PackageManager = context.packageManager
        try {
            val pi = packageName?.let { pm.getPackageInfo(it, 0) }
            Log.d("TAG", "version name: " + pi?.versionName)
            Log.d("TAG", "version code: " + pi?.versionCode)

            return """
            Package Name : ${resolveInfo!!.activityInfo.packageName}
            App Name : ${resolveInfo.activityInfo.name}
            Version Name : ${pi?.versionName}
            Version Code : ${pi?.versionCode}
            """.trimIndent()

        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("TAG", "Android Default Browser is not found")
            return "Android Default Browser is not set"
        }
    }

    @Suppress("DEPRECATION")
    fun getWebViewComponentVersionDetails(context: Context): String {
        val pm: PackageManager = context.packageManager
        return try {
            val pi = pm.getPackageInfo("com.google.android.webview", 0)
            Log.d("TAG", "version name: " + pi.versionName)
            Log.d("TAG", "version code: " + pi.versionCode)
            """
                Version Name : ${pi?.versionName}
                Version Code : ${pi?.versionCode}
                """.trimIndent()

        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("TAG", "Android System WebView is not found")
            "Android System WebView is not found"
        }
    }

    fun getDeviceUpTime(): String {
        return formatInterval(SystemClock.uptimeMillis())
    }

    private fun formatInterval(millis: Long): String {
        val hr: Long = TimeUnit.MILLISECONDS.toHours(millis)
        val min: Long = TimeUnit.MILLISECONDS.toMinutes(millis - TimeUnit.HOURS.toMillis(hr))
        val sec: Long = TimeUnit.MILLISECONDS.toSeconds(
            millis - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min)
        )
        val ms: Long = TimeUnit.MILLISECONDS.toMillis(
            millis - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(
                sec
            )
        )
        return String.format("%02d:%02d:%02d.%03d", hr, min, sec, ms)
    }

    fun getUserInstalledApps(context: Context): ArrayList<PInfo> {
        val res = ArrayList<PInfo>()
        // Flags
        val flags = PackageManager.GET_META_DATA or
                PackageManager.GET_SHARED_LIBRARY_FILES or
                PackageManager.GET_UNINSTALLED_PACKAGES

        val pm: PackageManager = context.packageManager
        val applications = pm.getInstalledApplications(flags)
        for (appInfo in applications) {
            if (appInfo.flags and ApplicationInfo.FLAG_SYSTEM == 1) {
                // System application
            } else {
                val newInfo = PInfo()
                newInfo.appname = appInfo.loadLabel(context.packageManager).toString()
                newInfo.pname = appInfo.packageName
                Log.v("Apps::>> ", " Package name:" + appInfo.packageName)
                newInfo.icon = appInfo.loadIcon(context.packageManager)
                if (getInstallerPackageName(context, appInfo.packageName).isNotEmpty()) {
                    newInfo.publisherStoreName = getInstallerPackageName(context, appInfo.packageName)
                }
                res.add(newInfo)
            }
        }

        return res
    }

    fun getSystemApps(context: Context): ArrayList<PInfo> {
        val res = ArrayList<PInfo>()
        // Flags
        val flags = PackageManager.GET_META_DATA or
                PackageManager.GET_SHARED_LIBRARY_FILES or
                PackageManager.GET_UNINSTALLED_PACKAGES

        val pm: PackageManager = context.packageManager
        val applications = pm.getInstalledApplications(flags)
        for (appInfo in applications) {
            if (appInfo.flags and (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP or ApplicationInfo.FLAG_SYSTEM) > 0) {
                // It is a system app
                // System application
                val newInfo = PInfo()
                newInfo.appname = appInfo.loadLabel(context.packageManager).toString()
                newInfo.pname = appInfo.packageName
                newInfo.icon = appInfo.loadIcon(context.packageManager)
                res.add(newInfo)
            }
        }

        return res
    }

    fun getInstalledApps(getSysPackages: Boolean, context: Context): ArrayList<PInfo> {
        val res = ArrayList<PInfo>()
        val packs = context.packageManager.getInstalledPackages(0)

        for (i in packs.indices) {
            val p = packs[i]
            if (!getSysPackages && p.versionName == null) {
                continue
            }
            val newInfo = PInfo()
            newInfo.appname = p.applicationInfo.loadLabel(context.packageManager).toString()
            newInfo.pname = p.packageName
            Log.v("Apps::>> ", " Package name:" + p.packageName)
            newInfo.versionName = p.versionName
            newInfo.versionCode = p.versionCode
            newInfo.icon = p.applicationInfo.loadIcon(context.packageManager)
            res.add(newInfo)
        }
        return res
    }

    @Suppress("DEPRECATION")
    fun isStoreVersion(context: Context, packageName: String): Boolean? {
        return try {
            context.packageManager.getInstallerPackageName(packageName)?.isNotEmpty()
        } catch (e: Throwable) {
            false
        }
    }

    fun getSideLoadedPackages(context: Context): ArrayList<PInfo> {
        val apps: ArrayList<PInfo> = getInstalledApps(false, context) /* false = no system packages */
        val sideLoadedApps: ArrayList<PInfo> = ArrayList<PInfo>()
        val max: Int = apps.size
        for (i in 0 until max) {
            apps[i].prettyPrint()
            if (isStoreVersion(context, apps[i].pname) == true) {
                sideLoadedApps.add(apps[i])
            }
        }
        return sideLoadedApps
    }

    fun getDeviceOSUpdateDetails(): String {
        val currentVersion = Build.VERSION.SDK_INT
        val latestVersion = VERSION_CODES.TIRAMISU
        return if (currentVersion == latestVersion) {
            """ 
            Device OS Already up to date.
            Latest Version : ${Build.VERSION.RELEASE}
            """.trimIndent()
        } else {
            """ 
            Device OS Update Available.
            Current Version : ${Build.VERSION.RELEASE}
            Latest Version : ${getVersionName(VERSION_CODES.TIRAMISU)}
            """.trimIndent()
        }

    }

    fun isLockScreenDisabled(context: Context): String {
        val keyguardManager: KeyguardManager =
            context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        var statusValue = "Screen " + "LOCK Disabled"
        val status = if (Build.VERSION.SDK_INT >= VERSION_CODES.M) {
            keyguardManager.isDeviceSecure
        } else {
            keyguardManager.isKeyguardSecure
        }

        if (status) {
            statusValue = "Screen " + "LOCK Enabled"
        }
        return statusValue
    }

    fun getFindOutConnectedNetworkMacAddress(context: Context): String {
        // Invoking the Wifi Manager
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        // Method to get the current connection info
        val wInfo = wifiManager.connectionInfo
        // Extracting the information from the received connection info
        val ipAddress = Formatter.formatIpAddress(wInfo.ipAddress)
        val linkSpeed = wInfo.linkSpeed
        val networkID = wInfo.networkId
        val ssid = wInfo.ssid
        val hssid = wInfo.hiddenSSID
        val bssid = wInfo.bssid
        // Setting the text inside the textView with
        // various entities of the connection
        return "IP Address:\t$ipAddress\n" +
                "Link Speed:\t$linkSpeed\n" +
                "Network ID:\t$networkID\n" +
                "SSID:\t$ssid\n" +
                "Hidden SSID:\t$hssid\n" +
                "BSSID:\t$bssid\n"
    }

    private fun getVersionName(apiLevel: Int): Int {
        return when (apiLevel) {
            33 -> {
                13
            }

            32, 31 -> {
                12
            }

            30 -> {
                11
            }

            29 -> {
                10
            }

            28 -> {
                9
            }

            26, 27 -> {
                8
            }

            25, 24 -> {
                7
            }

            23 -> {
                6
            }

            22, 21 -> {
                5
            }

            14, 15, 16, 17, 18, 19, 20 -> {
                4
            }

            else -> {
                1
            }
        }
    }

    fun installedFromMarket(
        weakContext: WeakReference<out Context?>,
        packageName: String
    ): Boolean {
        var result = false
        val context = weakContext.get()
        if (context != null) {
            try {
                var installer: String?
                kotlin.runCatching {
                    if (Build.VERSION.SDK_INT >= VERSION_CODES.R) {
                        installer =
                            context.packageManager.getInstallSourceInfo(packageName).installingPackageName
                    } else {
                        @Suppress("DEPRECATION")
                        installer = context.packageManager.getInstallerPackageName(packageName)
                    }

                    // if installer string is not null it might be installed by market
                    if (!TextUtils.isEmpty(installer)) {
                        result = true

                        // on Android Nougat and up when installing an app through the package installer (which HockeyApp uses itself), the installer will be
                        // "com.google.android.packageinstaller" or "com.android.packageinstaller" which is also not to be considered as a market installation
                        if (Build.VERSION.SDK_INT >= VERSION_CODES.N && (TextUtils.equals(
                                installer,
                                INSTALLER_PACKAGE_INSTALLER_NOUGAT
                            ) || TextUtils.equals(installer, INSTALLER_PACKAGE_INSTALLER_NOUGAT2))
                        ) {
                            result = false
                        }

                        // on some devices (Xiaomi) the installer identifier will be "adb", which is not to be considered as a market installation
                        if (TextUtils.equals(installer, INSTALLER_ADB)) {
                            result = false
                        }
                    }
                }
            } catch (ignored: Throwable) {
                result = false
            }
        }
        return result
    }

    fun getGooglePlayStoreInstalledApps(context: Context): ArrayList<PInfo> {
        val apps: ArrayList<PInfo> = getInstalledApps(false, context) /* false = no system packages */
        val sideLoadedApps: ArrayList<PInfo> = ArrayList<PInfo>()
        val max: Int = apps.size
        for (i in 0 until max) {
            if (verifyInstallerId(context, apps[i].pname)) {
                sideLoadedApps.add(apps[i])
            }
        }
        return sideLoadedApps

    }

    private fun verifyInstallerId(context: Context, packageName: String): Boolean {
        // A list with valid installers package name
        val validInstallers: List<String> = ArrayList(listOf("com.android.vending", "com.google.android.feedback"))
        // The package name of the app that has installed your app
        val installer = context.packageManager.getInstallerPackageName(packageName)
        // true if your app has been downloaded from Play Store
        return installer != null && validInstallers.contains(installer)
    }

    /**
     * ctx: this
     * packName example: 'nps.nps'
     * return will installer information
     */
    private fun getInstallerPackageName(ctx: Context,packName: String): String {
        var installerInfo: String?
        val packageManager: PackageManager = ctx.packageManager

        try {
            installerInfo =
                if (Build.VERSION.SDK_INT >= VERSION_CODES.R)
                    packageManager.getInstallSourceInfo(packName).installingPackageName
                else
                    packageManager.getInstallerPackageName(packName)
        } catch (e: Exception) {
            installerInfo = "--"
            e.printStackTrace()
        }

        return installerInfo ?: ""
    }

    fun getSideLoadsAppsWithPublisherStoreNames(context: Context): ArrayList<PInfo> {
        val apps: ArrayList<PInfo> = getInstalledApps(false, context) /* false = no system packages */
        val sideLoadedApps: ArrayList<PInfo> = ArrayList<PInfo>()
        val max: Int = apps.size
        for (i in 0 until max) {
            if (getInstallerPackageName(context, apps[i].pname).isNotEmpty()) {
                apps[i].publisherStoreName = getInstallerPackageName(context, apps[i].pname)
                sideLoadedApps.add(apps[i])
            }
        }
        return sideLoadedApps
    }
}