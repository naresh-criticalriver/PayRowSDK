package com.payrow.tappay



class SampleAdapter: PayRowAdapterListener {

    var payRowAdapterListener:PayRowAdapterListener?=null


    fun registerListener(listener: PayRowAdapterListener) {
        println("SDK EMVAdapter registerListener")
        payRowAdapterListener = listener
       // listener.registerEMVAdapterListener(this)

    }


  /*  override fun registerEMVAdapterListener(listener: EMVAdapterListener) {
        TODO("Not yet implemented")
    }*/

    override fun onAdapterInitComplete(isInitialized: Boolean, reason: String) {
        TODO("Not yet implemented")
    }

    override fun onAdapterInitializing() {
        TODO("Not yet implemented")
    }

    override fun onCardProcessing() {
        TODO("Not yet implemented")
    }

    override fun onCardProcessingComplete() {
        TODO("Not yet implemented")
    }

    override fun onCardProcessingNotify(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCardRemoved() {
        TODO("Not yet implemented")
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