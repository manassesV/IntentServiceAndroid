package com.example.logonrmlocal.intentservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe





class MainActivity : AppCompatActivity() {

    val receiver = ResponseReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val intent = Intent(this, MinhaIntentService::class.java)
        intent.putExtra(MinhaIntentService.PARAM_ENTRADA, "Agora É:")
        startService(intent)

        registrarReceiver()



        var filter = IntentFilter()
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED)

        val receiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent) {
          if (intent?.action.equals(Intent.ACTION_POWER_CONNECTED)){
              tvResultado.text = "Conected"
          }else{
              tvResultado.text = "Disconected"

          }

            }
        }

        registerReceiver(receiver, filter)

    }

    private fun registrarReceiver() {
        val filter = IntentFilter(MinhaIntentService.ACTION)
        filter.addCategory(Intent.CATEGORY_DEFAULT)
        registerReceiver(receiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }


    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Status) {/* Do something */

        when(event){
            Status.SUCCESS->{
               contanerLoading.visibility = View.GONE
            }
            Status.ERROR->{
              contanerLoading.visibility = View.GONE
            }
            Status.LOADING->{
              contanerLoading.visibility = View.VISIBLE
            }
        }
    };

    inner class  ResponseReceiver: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            tvResultado.text = intent?.getStringExtra(MinhaIntentService.PARAM_SAIDA)
        }

    }
}
