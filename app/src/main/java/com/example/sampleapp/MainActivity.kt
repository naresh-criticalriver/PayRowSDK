package com.example.sampleapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.payrow.tappay.PayRowAdapterListener
import com.payrow.tappay.PayRowConfig
import com.payrow.tappay.PayRowEMVAdapter
import java.text.DecimalFormat


class MainActivity : AppCompatActivity(), PayRowAdapterListener {

    val TAG = "TapPay"
    private val merchantToken = "1234567788="
    private var txRef = 0
    private var amount = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      //  DashboardActivity.displayToast("Welcome", this)

        val txtRefId = DecimalFormat("00000").format(txRef)

        val payRowConfig = PayRowConfig(
            "0784",
            "0784",
            1,
            100.00,
            0,
            "00",
            2,
            "00",
            false,
            "01",
        )

        //Initializing PayRowAdapter
       val payRowEMVAdapter= PayRowEMVAdapter()
        payRowEMVAdapter.initPayRowAdapter(this, payRowConfig, this)

      //  payRowEMVAdapter.initPayRowSession(  merchantToken, txtRefId, amount)

//        For cancelling current session
       // payRowEMVAdapter.cancelCurrentSession()

    }

    override fun onAdapterInitComplete(isInitialized: Boolean, reason: String) {
        Log.d(TAG, "AdapterInit: $reason // $isInitialized")
        showToast("Adapter Initated")
    }

    override fun onAdapterInitializing() {
        Log.d(TAG, "  Adapter Initializing ")
    }

    override fun onCardProcessing() {
        Log.d(TAG, " Card Processing")
    }

    override fun onCardProcessingComplete() {
        Log.d(TAG, " Card Processing Complete")
    }

    override fun onCardProcessingNotify(message: String) {
        Log.d(TAG, " Card Processing Notify // $message")
    }

    override fun onCardRemoved() {
        Log.d(TAG, " Card Removed")
    }

    override fun onCheckDeviceRegistrationComplete(
        isRegistered: Boolean,
        reason: String,
        merchantId: String,
        merchantCode: String,
        merchantName: String,
        terminalId: String
    ) {
        Log.d(TAG, "$isRegistered // check Device Registration completed // $reason")

    }

    override fun onDeviceRegistrationComplete(isRegistered: Boolean, reason: String) {
       showToast("$isRegistered // Device Registration completed // $reason")
        Log.d(TAG, "$isRegistered // Device Registration completed // $reason")
    }

    override fun onSessionComplete(
        isSuccessful: Boolean,
        statusCode: String,
        reason: String,
        sessionData: Map<String, String>
    ) {
        showToast("$statusCode // session completed // $reason")
        Log.d(TAG, "$statusCode // session completed // $reason")
    }

    override fun onSessionCountdown(remainingSeconds: Int) {
        TODO("Not yet implemented")
    }

    override fun onSessionInitComplete(isInitialized: Boolean, reason: String) {
        Log.d(TAG, " session init complete// $reason")
    }

    override fun onSessionTimeout() {
        Log.d(TAG, " session timeout")
        showToast("session timeout")
    }

    fun showToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}