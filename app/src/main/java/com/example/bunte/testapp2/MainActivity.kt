package com.example.bunte.testapp2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val TAG = "testapp2.MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var apikey = "cd8b30d9bf70a8a67954c0b084872104"
        var lat = 37.8267
        var long = -122.4233
        val earl = "https://api.darksky.net/forecast/$apikey/$lat,$long"

        val george = OkHttpClient()
        var request: Request = Request.Builder().url(earl).build()

        var call: Call = george.newCall(request)
        var resp: Response? =
                try{
                    call.execute()
                }catch(e: IOException){
                    Log.e(TAG, "Exception Caught: ", e)
                    null
                }

        if(resp is Response){
           if(resp.isSuccessful()){
               Log.v(TAG, resp.body()!!.string())
           }
        }
    }
}
