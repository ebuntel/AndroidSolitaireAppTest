package com.example.bunte.testapp2

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val TAG = "testapp2.MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apikey = "cd8b30d9bf70a8a67954c0b084872104"
        val lat = 37.8267
        val long = -122.4233
        val earl = "https://api.darksky.net/forecast/$apikey/$lat,$long"

        if(networkIsAvailable()) {

            val george = OkHttpClient()
            val request: Request = Request.Builder().url(earl).build()

            val call: Call = george.newCall(request)

            call.enqueue(object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    e?.printStackTrace()
                }

                override fun onResponse(call: Call?, response: Response?) {
                    try {
                        if (response is Response) {
                            if (response.isSuccessful()) {
                                Log.v(TAG, response.body()!!.string())
                            } else {
                                alertUserAboutError("Response Error")
                            }
                        }
                    } catch(e: IOException) {
                        Log.e(TAG, "Exception Caught: ", e)
                    }

                }
            })
        }else{
            alertUserAboutError("Connection Error")
        }

    }

    private fun  networkIsAvailable(): Boolean {
        val manager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netin = manager.activeNetworkInfo
        var avail = false
        if(netin != null && netin.isConnected){
            avail = true
        }

        return avail
    }

    private fun alertUserAboutError(errorName: String) {
        when(errorName){
            "Response Error" -> {
                val dia = AlertDialogfragment()
                dia.show(fragmentManager, "Error_Dialog")
            }
            "Connection Error" -> {
                val dianii = ConnectionErrorDialogFrag()
                dianii.show(fragmentManager, "Con_Error_Dialog")
            }
            else -> print("jei")
        }
    }
}
