package com.payrow.cardreader.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.payrow.cardreader.R
import com.payrow.cardreader.SimpleCardReader
import com.payrow.cardreader.model.EmvCard

class CardReaderActivity : AppCompatActivity(), SimpleCardReader.SimpleCardReaderCallback,
    NfcAdapter.ReaderCallback {

    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //NfcAdapter.getDefaultAdapter returns null if devise do not have NFC
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (nfcAdapter == null) {
            Toast.makeText(this, "No NFC on this device", Toast.LENGTH_LONG).show()
        } else if (nfcAdapter?.isEnabled == false) {

            // NFC is available for device but not enabled
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                try {
                    startActivityForResult(Intent(Settings.Panel.ACTION_NFC), 2265)
                } catch (ignored: ActivityNotFoundException) {
                }

            } else {
                try {
                    startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
                } catch (ignored: ActivityNotFoundException) {
                }
            }
        }
    }

    override fun cardIsReadyToRead(card: EmvCard) {
        findViewById<TextView>(R.id.tvMessage).visibility = View.GONE
        findViewById<TextView>(R.id.tvCardDetails).text =
            "${card.cardNumber.subSequence(0, 4)} ${
                card.cardNumber.subSequence(
                    4,
                    8
                )
            } ${card.cardNumber.subSequence(8, 12)} ${card.cardNumber.subSequence(12, 16)}"
        findViewById<TextView>(R.id.tvCardMonth).text = card.expireDateMonth
        findViewById<TextView>(R.id.tvDivider).text = "/"
        findViewById<TextView>(R.id.tvCardYear).text = card.expireDateYear
//        Toast.makeText(this, info, Toast.LENGTH_LONG).show()
    }

    override fun cardMovedTooFastOrLockedNfc() {
        findViewById<TextView>(R.id.tvMessage).text = "Tap again"
//        Toast.makeText(this, "Tap again", Toast.LENGTH_LONG).show()
    }

    override fun errorReadingOrUnsupportedCard() {
        findViewById<TextView>(R.id.tvMessage).text = "Error / Unsupported"
//        Toast.makeText(this, "Error / Unsupported", Toast.LENGTH_LONG).show()
    }

    override fun onTagDiscovered(tag: Tag?) {
        SimpleCardReader.readCard(tag, this)
    }

    public override fun onResume() {
        super.onResume()
        nfcAdapter?.enableReaderMode(
            this, this,
            NfcAdapter.FLAG_READER_NFC_A or
                    NfcAdapter.FLAG_READER_NFC_B or
                    NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null
        )
    }

    public override fun onPause() {
        super.onPause()
        nfcAdapter?.disableReaderMode(this)
    }
}
