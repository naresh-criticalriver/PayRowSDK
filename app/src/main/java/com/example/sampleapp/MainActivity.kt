package com.example.sampleapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.payrow.tappay.PayRowActivity
import com.payrow.tappay.PayRowAdapterListener
import com.payrow.tappay.PayRowConfig
import com.payrow.tappay.PayRowEMVAdapter
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    val TAG = "TapPay"
    private val merchantToken = "1234567788="
    private var txtRef = 0
    private var amount = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      //  DashboardActivity.displayToast("Welcome", this)

       // val txtRefId = DecimalFormat("00000").format(txRef)

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

     /*   //Initializing PayRowAdapter
       val payRowEMVAdapter= PayRowEMVAdapter()
        payRowEMVAdapter.initPayRowAdapter(this, payRowConfig, this)

      //  payRowEMVAdapter.initPayRowSession(  merchantToken, txtRefId, amount)

//        For cancelling current session
       // payRowEMVAdapter.cancelCurrentSession()*/

        startActivity(
            Intent(this,PayRowActivity::class.java)
                .putExtra("txRefId",txtRef)
                .putExtra("amount",amount))

    }

    fun showToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}