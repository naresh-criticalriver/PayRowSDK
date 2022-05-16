package com.payrow.tappay

data class PayRowConfig (
    val countryCode:String,
    val currencyCode:String,
    val pinRequirement:Int,
    val readerLimit: Double,
    val signatureFlag : Int,
    val transactionType: String,
    val currencyExponent: Int,
    val mockAuthCode: String,
    val enforcePinCVM: Boolean,
    val mockStatusCode: String,
    val merchantToken: String,
    val txtRefId: String,
    val amount: Int
)