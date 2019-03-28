package com.example.logonrmlocal.intentservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.logging.Logger


class  BootReceiver: BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        Logger.getLogger(BootReceiver::class.java.name).warning("O sistema está no ar")

    }

}