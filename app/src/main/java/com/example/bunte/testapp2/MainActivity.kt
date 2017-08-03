package com.example.bunte.testapp2

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val TAG = "testapp2.MainActivity"
    var mCurrWeatherData: CurrWeather = CurrWeather()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apikey = "cd8b30d9bf70a8a67954c0b084872104"
        val lat = 42.3878
        val long = -71.1143
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
                                val resp = response.body()!!.string()
                                mCurrWeatherData = getCurrentDetails(resp)
                                Log.v(TAG, resp)
                            } else {
                                alertUserAboutError("Response Error")
                            }
                        }
                    } catch(e: IOException) {
                        Log.e(TAG, "Exception Caught: ", e)
                    } catch (j: JSONException){
                        Log.e(TAG, "JSON Error", j)
                    }

                }
            })
        }else{
            alertUserAboutError("Connection Error")
        }

    }

    @Throws(JSONException::class)
    private fun  getCurrentDetails(resp: String): CurrWeather {
        val forecast: JSONObject = JSONObject(resp)
        val timezone = forecast.getString("timezone")
        Log.i(TAG, "From JSON: $timezone")

        val curr = forecast.getJSONObject("currently")
        var weather = CurrWeather(mHumidity = curr.getDouble("humidity"),
                mTime = curr.getLong("time"), mIcon = curr.getString("icon"),
                mPrecipChance = curr.getDouble("precipProbability"),
                mSummary = curr.getString("summary"), mTemp = curr.getDouble("temperature"),
                mTimezone = timezone)

        Log.d(TAG, getFormattedTime(weather))

        return  weather
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

    private fun getFormattedTime(weather: CurrWeather): String{
        val formatter = SimpleDateFormat("h:mm a")
        formatter.timeZone = TimeZone.getTimeZone(weather.mTimezone)
        val dateTime = Date(weather.mTime * 1000)
        return formatter.format(dateTime)
    }

    private fun getIconID(name: String): Int{
        var id = 0
        when(name){
            "clear-day" -> id = R.drawable.clear_day
            "clear-night" -> R.drawable.clear_night
            "rain" -> R.drawable.rain
            "snow" -> R.drawable.snow
            "sleet" -> R.drawable.sleet
            "wind" -> R.drawable.wind
            "fog" -> R.drawable.fog
            "cloudy" -> R.drawable.cloudy
            "partly-cloudy-day" -> R.drawable.partly_cloudy
            "partly-cloudy-night" -> R.drawable.cloudy_night
            else -> print("NOPE")
        }
        return id
    }
}
