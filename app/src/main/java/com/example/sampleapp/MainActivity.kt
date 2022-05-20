package com.example.sampleapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.a.b.EMVAdapterListener
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

      /*  startActivity(
            )*/
       /* val intent = Intent(this, PayRowActivity::class.java)
            .putExtra(PayRowActivity.TXT_REF_ID, txtRef)
            .putExtra(PayRowActivity.AMOUNT, amount)
      //  startActivityForResult(intent,PayRowActivity.PAYROW_RESULT_CODE)
        resultLauncher.launch(intent)*/


        findViewById<AppCompatButton>(R.id.paynow).setOnClickListener {
            val intent = Intent(this, PayRowActivity::class.java)
                .putExtra(PayRowActivity.TXT_REF_ID, txtRef)
                .putExtra(PayRowActivity.AMOUNT, amount)
            //  startActivityForResult(intent,PayRowActivity.PAYROW_RESULT_CODE)
            resultLauncher.launch(intent)
        }

    }

    fun showToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                // doSomeOperations()
                result.data?.let {
                    it.getStringExtra(PayRowActivity.RESULT_MSG)?.let {reason->
                        showToast(reason)
                    }
                }
            }
        }
    }