package com.example.bunte.testapp2

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by bunte on 7/25/2017.
 */
data class CurrWeather(var mIcon: String = "", var mTime: Long = 0,
    var mTemp: Double = 0.0, var mHumidity: Double = 0.0,
    var mPrecipChance: Double = 0.0, var mSummary: String = "",
    var mTimezone: String = ""){

}