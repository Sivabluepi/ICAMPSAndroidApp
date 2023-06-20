package com.bluepi.icampsandroid.ui.devicednainfopocsample

import android.graphics.drawable.Drawable
import android.util.Log


class PInfo {

    var appname = ""
    var pname = ""
    var publisherStoreName = ""
    var versionName = ""
    var versionCode = 0
    var icon: Drawable? = null
    fun prettyPrint() {
        Log.v("", appname + "\t" + pname + "\t" + versionName + "\t" + versionCode  + "\t" + publisherStoreName)
    }
}