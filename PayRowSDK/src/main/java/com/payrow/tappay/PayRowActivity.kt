package com.payrow.tappay

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.wizzitdigital.emv.sdk.EMVAdapter
import com.wizzitdigital.emv.sdk.EMVAdapterListener
import com.wizzitdigital.emv.sdk.EMVCfgVars
import java.text.DecimalFormat

class PayRowActivity : AppCompatActivity(), EMVAdapterListener {

    private var emvAdapter: EMVAdapter? = null
    private var progressBar: ProgressBar? = null
    private val merchantToken = "1234567788="
    private var txRef = 0
    private var amount = 0
    private val TAG=PayRowActivity::class.simpleName
    private var countdownInterfaceInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_row)

        txRef = intent.getIntExtra("txRefId",0)

        amount=intent.getIntExtra("amount",0)

        progressBar = findViewById(R.id.progressBar_Initalizing)
        showProgressBar("Initializing")
        initalizeEMVAdapter(this)

    }

    private fun showProgressBar(msg: String) {
        progressBar?.visibility = View.VISIBLE
    }

    private fun initalizeEMVAdapter(activity: PayRowActivity) {
        emvAdapter = EMVAdapter(activity, this)
        emvAdapter!!.setConfig(EMVCfgVars.COUNTRY_CODE, "0784")
        emvAdapter!!.setConfig(EMVCfgVars.CURRENCY_CODE, "0784")
        emvAdapter!!.setConfig(EMVCfgVars.PIN_REQUIREMENT, 1)
        emvAdapter!!.setConfig(EMVCfgVars.READER_LIMIT, 100.00)
        emvAdapter!!.setConfig(EMVCfgVars.SIGNATURE_FLAG, 0)
        emvAdapter!!.setConfig(EMVCfgVars.TX_TYPE, "00")
        emvAdapter!!.setConfig(EMVCfgVars.CURRENCY_EXPONENT, 2)
        emvAdapter!!.setConfig(EMVCfgVars.OVERRIDE_MASTERCARD_PROFILE_CONFIG, true)
        emvAdapter!!.setConfig(EMVCfgVars.MOCK_AUTH_CODE, "00")
        emvAdapter!!.setConfig(EMVCfgVars.ENFORCE_PIN_CVM, false)
        emvAdapter!!.setConfig(EMVCfgVars.MOCK_STATUS_CODE, "01")
        emvAdapter?.initAdapter()
    }

    override fun onAdapterInitComplete(isInitialized: Boolean, reason: String) {
        progressBar?.visibility = View.GONE
        showToast("EMV Adapter Intitalized")
        if(isInitialized){
            emvAdapter?.setAdapterActivity(this)
            initPayRowSession()
        }
    }

    private fun initPayRowSession() {
        Log.d(TAG,"EMV Session Started")
       // showToast("EMV Session Started")
        runOnUiThread {
         try {
             amount *= 100
             emvAdapter?.initSession(
                 merchantToken,
                 DecimalFormat("00000").format(txRef),
                 amount
             )
         }catch (e:Exception){
             Log.d(TAG,"Exception "+e.message)
         }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onAdapterInitializing() {
        TODO("Not yet implemented")
    }

    override fun onCardProcessing() {
        Log.d(TAG,"CardProcessing")
       // showToast("CardProcessing")
        runOnUiThread {
            try {
                println("SDK PaymentActivity On Card processing")
                (findViewById(R.id.textViewTapToPay) as TextView).text = "Please Wait..."
                (findViewById(R.id.textViewSecondsLeft) as TextView).text = ""
                (findViewById(R.id.textViewTimeoutSeconds) as TextView).text = ""
                (findViewById(R.id.buttonCancel) as Button).visibility = View.INVISIBLE
                countdownInterfaceInitialized = false
            } catch (ex: Exception) {
            }
        }
    }

    override fun onCardProcessingComplete() {
        Log.d(TAG,"CardProcessingComplete")
    }

    override fun onCardProcessingNotify(message: String) {
        Log.d(TAG,"CardProcessingNotify  $message")
        runOnUiThread {
            (findViewById(R.id.textViewCardProcessingNotify) as TextView).text = message
        }
    }

    override fun onCardRemoved() {
        Log.d(TAG,"Card Removed")
        runOnUiThread {
            try {
                (findViewById(R.id.textViewTapToPay) as TextView).text = "Please wait..."
                (findViewById(R.id.textViewSecondsLeft) as TextView).text = ""
                (findViewById(R.id.textViewTimeoutSeconds) as TextView).text = ""
                setContentView(R.layout.activity_please_wait)
            } catch (ex: Exception) {
            }
        }
    }

    override fun onCheckDeviceRegistrationComplete(
        isRegistered: Boolean,
        reason: String,
        merchantId: String,
        merchantCode: String,
        merchantName: String,
        terminalId: String
    ) {
        Log.d(TAG,"CheckDeviceRegistrationComplete  $isRegistered  // $reason")
    }

    override fun onDeviceRegistrationComplete(isRegistered: Boolean, reason: String) {
        Log.d(TAG,"DeviceRegistrationComplete  $isRegistered  // $reason")
    }

    override fun onSessionComplete(
        isSuccessful: Boolean,
        statusCode: String,
        reason: String,
        sessionData: Map<String, String>
    ) {
        Log.d(TAG,"SessionInitComplete  $isSuccessful  // $reason")
        showToast(reason)
        finish()
    }

    override fun onSessionCountdown(remainingSeconds: Int) {
        Log.d(TAG,"Session count  $remainingSeconds")
        try {
            runOnUiThread {
                if (!countdownInterfaceInitialized) {
                    (findViewById(R.id.textViewTapToPay) as TextView).text = "Tap Card To Pay"
                    (findViewById(R.id.textViewSecondsLeft) as TextView).text = "Seconds Left"
                    (findViewById(R.id.buttonCancel) as Button).visibility = View.VISIBLE
                    countdownInterfaceInitialized = true
                }
                (findViewById(R.id.textViewTimeoutSeconds) as TextView).text =
                    remainingSeconds.toString()
            }
        } catch (ex: Exception) {
        }
    }

    override fun onSessionInitComplete(isInitialized: Boolean, reason: String) {
        Log.d(TAG,"SessionInitComplete  $isInitialized  // $reason")
        try {
            txRef += 1
            if (isInitialized) {
                runOnUiThread {
                    setContentView(R.layout.tap_to_pay)
                    (findViewById<TextView>(R.id.textViewTransactionRef)).text =
                        DecimalFormat("00000").format(txRef)
                    (findViewById<TextView>(R.id.textViewAmount)).text = formatAmount()
                    val btnCancel = findViewById<Button>(R.id.buttonCancel)
                    btnCancel.setOnClickListener {
                        cancelSessionClicked()
                    }
                }
            }
        } catch (ex: Exception) {

        }
    }

    override fun onSessionTimeout() {
        Log.d(TAG,"Session Timeout")
    }

    private fun formatAmount(): String {
        val decim = DecimalFormat("0.00")
        return decim.format(amount.toDouble())
    }

    private fun cancelSessionClicked() {
        try {
            emvAdapter?.cancelSession()
        } catch (ex: Exception) {
        }
    }
}