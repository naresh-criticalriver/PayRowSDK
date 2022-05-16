package com.example.sampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.mbms.MbmsErrors
import android.util.Log
import com.example.payrowlibrary.DashboardActivity
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

        DashboardActivity.displayToast("Welcome", this)

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
            merchantToken,
            txtRefId,
            amount
        )

        //Initializing PayRowAdapter
       val payRowEMVAdapter= PayRowEMVAdapter(this, payRowConfig, this)

              // payRowEMVAdapter.cancelEmvAdapterSession()

        //If we click on cancel currentSession
        PayRowEMVAdapter.cancelCurrentSession()

    }

    override fun onAdapterInitComplete(isInitialized: Boolean, reason: String) {
        Log.d(TAG, " $reason // $isInitialized")
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
        TODO("Not yet implemented")
    }

    override fun onDeviceRegistrationComplete(isRegistered: Boolean, reason: String) {
        TODO("Not yet implemented")
    }

    override fun onSessionComplete(
        isSuccessful: Boolean,
        statusCode: String,
        reason: String,
        sessionData: Map<String, String>
    ) {
        TODO("Not yet implemented")
    }

    override fun onSessionCountdown(remainingSeconds: Int) {
        TODO("Not yet implemented")
    }

    override fun onSessionInitComplete(isInitialized: Boolean, reason: String) {
        TODO("Not yet implemented")
    }

    override fun onSessionTimeout() {
        TODO("Not yet implemented")
    }
}