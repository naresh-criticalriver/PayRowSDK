package com.payrow.tappay

interface PayRowAdapterListener {


   // public abstract fun registerEMVAdapterListener(listener: EMVAdapterListener)

    public abstract fun onAdapterInitComplete(isInitialized: kotlin.Boolean, reason: kotlin.String): kotlin.Unit

    public abstract fun onAdapterInitializing(): kotlin.Unit

    public abstract fun onCardProcessing(): kotlin.Unit

    public abstract fun onCardProcessingComplete(): kotlin.Unit

    public abstract fun onCardProcessingNotify(message: kotlin.String): kotlin.Unit

    public abstract fun onCardRemoved(): kotlin.Unit

    public abstract fun onCheckDeviceRegistrationComplete(isRegistered: kotlin.Boolean, reason: kotlin.String, merchantId: kotlin.String, merchantCode: kotlin.String, merchantName: kotlin.String, terminalId: kotlin.String): kotlin.Unit

    public abstract fun onDeviceRegistrationComplete(isRegistered: kotlin.Boolean, reason: kotlin.String): kotlin.Unit

    public abstract fun onSessionComplete(isSuccessful: kotlin.Boolean, statusCode: kotlin.String, reason: kotlin.String, sessionData: kotlin.collections.Map<kotlin.String, kotlin.String>): kotlin.Unit

    public abstract fun onSessionCountdown(remainingSeconds: kotlin.Int): kotlin.Unit

    public abstract fun onSessionInitComplete(isInitialized: kotlin.Boolean, reason: kotlin.String): kotlin.Unit

    public abstract fun onSessionTimeout(): kotlin.Unit
}