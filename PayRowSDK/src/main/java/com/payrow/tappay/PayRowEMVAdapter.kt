package com.payrow.tappay

import android.app.Activity
import com.wizzitdigital.emv.sdk.EMVAdapter
import com.wizzitdigital.emv.sdk.EMVAdapterListener
import com.wizzitdigital.emv.sdk.EMVCfgVars

class PayRowEMVAdapter(
    val activity: Activity,
    val payRowConfig: PayRowConfig,
    val  listener: PayRowAdapterListener
) : EMVAdapterListener {


    private var emvAdapterListener: EMVAdapterListener? = null
    var payRowAdapterListener: PayRowAdapterListener? = null
    var initialised = false
    var amount = 0
    var refId = "0"

    companion object{
        private var emvAdapter: EMVAdapter? = null
        fun cancelCurrentSession(){
            emvAdapter?.let {
                it.cancelSession()
            }
        }
    }

    /**
     * Default values for config
     */
    fun initPayRowAdapter(activity: Activity) {
        emvAdapter = EMVAdapter(activity, this)
        registerListener(this)
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

    /**
     * Custom config params and init EMV Adapter
     */
    init {
        payRowAdapterListener = listener

        registerListener(this)

        emvAdapter = EMVAdapter(activity, this)
        emvAdapter!!.setAdapterActivity(activity)
        emvAdapter!!.setConfig(EMVCfgVars.COUNTRY_CODE, payRowConfig.currencyCode) //0784 -DEFAULT
        emvAdapter!!.setConfig(EMVCfgVars.CURRENCY_CODE, payRowConfig.currencyCode) //0784 -DEFAULT
        emvAdapter!!.setConfig(EMVCfgVars.PIN_REQUIREMENT, payRowConfig.pinRequirement) //1 -DEFAULT
        emvAdapter!!.setConfig(EMVCfgVars.READER_LIMIT, payRowConfig.readerLimit) //100.00 -DEFAULT
        emvAdapter!!.setConfig(EMVCfgVars.SIGNATURE_FLAG, payRowConfig.signatureFlag) //0 -DEFAULT
        emvAdapter!!.setConfig(EMVCfgVars.TX_TYPE, payRowConfig.transactionType) //00 -DEFAULT
        emvAdapter!!.setConfig(EMVCfgVars.CURRENCY_EXPONENT, payRowConfig.currencyExponent) //2 -DEFAULT
        emvAdapter!!.setConfig(EMVCfgVars.OVERRIDE_MASTERCARD_PROFILE_CONFIG, true)
        emvAdapter!!.setConfig(EMVCfgVars.MOCK_AUTH_CODE, payRowConfig.mockAuthCode)//00 -DEFAULT
        emvAdapter!!.setConfig(EMVCfgVars.ENFORCE_PIN_CVM, payRowConfig.enforcePinCVM) //false -DEFAULT
        emvAdapter!!.setConfig(EMVCfgVars.MOCK_STATUS_CODE, payRowConfig.mockStatusCode) //01 -DEFAULT
        emvAdapter?.initAdapter()

        //Initializing EMV Adapter Session
        emvAdapter?.initSession(payRowConfig.merchantToken,payRowConfig.txtRefId,payRowConfig.amount)
    }

  /*  fun cancelEmvAdapterSession(){
        emvAdapter?.cancelSession()
    }*/

    fun cancelCurrentSession(){
        emvAdapter?.cancelSession()
    }

    private fun registerListener(listener: EMVAdapterListener) {
        emvAdapterListener = listener
    }

    override fun onAdapterInitComplete(isInitialized: Boolean, reason: String) {
        payRowAdapterListener?.onAdapterInitComplete(isInitialized, reason)
    }

    override fun onAdapterInitializing() {
        payRowAdapterListener?.onAdapterInitializing()
    }

    override fun onCardProcessing() {
        payRowAdapterListener?.onCardProcessing()
    }

    override fun onCardProcessingComplete() {
        payRowAdapterListener?.onCardProcessingComplete()
    }

    override fun onCardProcessingNotify(message: String) {
        payRowAdapterListener?.onCardProcessingNotify(message)
    }

    override fun onCardRemoved() {
        payRowAdapterListener?.onCardRemoved()
    }

    override fun onCheckDeviceRegistrationComplete(
        isRegistered: Boolean,
        reason: String,
        merchantId: String,
        merchantCode: String,
        merchantName: String,
        terminalId: String
    ) {
        payRowAdapterListener?.onCheckDeviceRegistrationComplete(
            isRegistered,
            reason,
            merchantId,
            merchantCode,
            merchantName,
            terminalId
        )
    }

    override fun onDeviceRegistrationComplete(isRegistered: Boolean, reason: String) {
        payRowAdapterListener?.onDeviceRegistrationComplete(isRegistered, reason)
    }

    override fun onSessionComplete(
        isSuccessful: Boolean,
        statusCode: String,
        reason: String,
        sessionData: Map<String, String>
    ) {
        payRowAdapterListener?.onSessionComplete(isSuccessful, statusCode, reason, sessionData)
    }

    override fun onSessionCountdown(remainingSeconds: Int) {
        payRowAdapterListener?.onSessionCountdown(remainingSeconds)
    }

    override fun onSessionInitComplete(isInitialized: Boolean, reason: String) {
        payRowAdapterListener?.onSessionInitComplete(isInitialized, reason)
    }

    override fun onSessionTimeout() {
        payRowAdapterListener?.onSessionTimeout()
    }

    /*  override fun onAdapterInitComplete(isInitialized: Boolean, reason: String) {
          println("SDK EMVAdapter onAdapterInitComplete: $isInitialized")
          this.initialised = isInitialized
          emvAdapterListener?.onAdapterInitComplete(isInitialized, reason)

          payRowAdapterListener?.onAdapterInitComplete(isInitialized,reason)
      }

      override fun onAdapterInitializing() {
          println("SDK EMVAdapter onAdapterInitializing")
              emvAdapterListener?.onAdapterInitializing()
      }

      override fun onCardProcessing() {
          println("SDK EMVAdapter onCardProcessing")
              emvAdapterListener?.onCardProcessing()
      }

      override fun onCardProcessingComplete() {
          println("SDK EMVAdapter onCardProcessingComplete")
              emvAdapterListener?.onCardProcessingComplete()
      }

      override fun onCardProcessingNotify(message: String) {
          println("SDK EMVAdapter onCardProcessingNotify")
              emvAdapterListener?.onCardProcessingNotify(message)
      }

      override fun onCardRemoved() {
          println("SDK EMVAdapter onCardRemoved")
              emvAdapterListener?.onCardRemoved()
      }

      override fun onCheckDeviceRegistrationComplete(
          isRegistered: Boolean,
          reason: String,
          merchantId: String,
          merchantCode: String,
          merchantName: String,
          terminalId: String
      ) {
              emvAdapterListener?.onCheckDeviceRegistrationComplete(
                  isRegistered,
                  reason,
                  merchantId,
                  merchantCode,
                  merchantName,
                  terminalId
              )
      }

      override fun onDeviceRegistrationComplete(isRegistered: Boolean, reason: String) {
              emvAdapterListener?.onDeviceRegistrationComplete(isRegistered, reason)
      }

      override fun onSessionComplete(
          isSuccessful: Boolean,
          statusCode: String,
          reason: String,
          sessionData: Map<String, String>
      ) {
          println("SDK EMVAdapter onSessionComplete")
              emvAdapterListener?.onSessionComplete(isSuccessful, statusCode, reason, sessionData)
      }

      override fun onSessionCountdown(remainingSeconds: Int) {
          println("SDK EMVAdapter onSessionCountdown")
              emvAdapterListener?.onSessionCountdown(remainingSeconds)
      }

      override fun onSessionInitComplete(isInitialized: Boolean, reason: String) {
          println("SDK EMVAdapter onSessionInitComplete")
              emvAdapterListener?.onSessionInitComplete(isInitialized, reason)
      }

      override fun onSessionTimeout() {
          println("SDK EMVAdapter onSessionTimeout")
              emvAdapterListener?.onSessionTimeout()
      }*/
}