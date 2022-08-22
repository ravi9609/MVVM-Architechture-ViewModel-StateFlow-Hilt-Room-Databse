package com.example.mvvmdemo.util

import android.widget.Toast

import android.content.Intent

import android.content.BroadcastReceiver
import android.content.Context
import android.provider.Settings


class AirplaneModeChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (isAirplaneModeOn(context.applicationContext)) {
            Toast.makeText(context, "AirPlane mode is on", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "AirPlane mode is off", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private fun isAirplaneModeOn(context: Context): Boolean {
            return Settings.System.getInt(
                context.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON,
                0
            ) !== 0
        }
    }
}