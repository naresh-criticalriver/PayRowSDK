package com.payrow.cardreader.model

data class ApplicationFileLocator (
        val sfi: Int,
        val firstRecord: Int,
        val lastRecord: Int,
        val isOfflineAuthentication: Boolean
)