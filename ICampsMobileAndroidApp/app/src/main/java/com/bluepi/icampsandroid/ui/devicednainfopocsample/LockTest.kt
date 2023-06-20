package com.bluepi.icampsandroid.ui.devicednainfopocsample

import android.app.KeyguardManager
import android.content.Context
import android.os.Build

object LockTest {
    /**
     *
     * Checks to see if the lock screen is set up with either a PIN / PASS / PATTERN
     *
     *
     * For Api 16+
     *
     * @return true if PIN, PASS or PATTERN set, false otherwise.
     */
    fun doesDeviceHaveSecuritySetup(context: Context): Boolean {
        return isPassOrPinSet(context)
    }

    /**
     * @param context
     * @return true if pass or pin set
     */
    private fun isPassOrPinSet(context: Context): Boolean {
        val keyguardManager: KeyguardManager =
            context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyguardManager.isDeviceSecure
        } else {
            keyguardManager.isKeyguardSecure
        }
    }
}