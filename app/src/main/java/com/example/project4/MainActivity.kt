package com.example.project4

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import org.json.JSONObject
import java.net.URL
import kotlin.Exception


class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView

    val city = "London"
    val key: String = "25bce8a1d881a71732d233bd579371f0"
    val url: String = "https://api.openweathermap.org/data/2.5/weather?q=London&units=metric&appid=25bce8a1d881a71732d233bd579371f0"
    var lang: String = "Russian"
    var speed: Int = 7
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherTask().execute()
    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try {
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&appid=$key").readText(
                    Charsets.UTF_8
                )
            } catch (e: Exception) {
                findViewById<TextView>(R.id.city).visibility = View.VISIBLE
                findViewById<TextView>(R.id.temp).visibility = View.VISIBLE
                findViewById<TextView>(R.id.weather).visibility = View.VISIBLE
                findViewById<ImageView>(R.id.imageView).visibility = View.VISIBLE
                findViewById<TextView>(R.id.textWind).visibility = View.VISIBLE
                findViewById<TextView>(R.id.textHumidity).visibility = View.VISIBLE
                findViewById<TextView>(R.id.wind).visibility = View.VISIBLE
                findViewById<TextView>(R.id.humidity).visibility = View.VISIBLE
                findViewById<Switch>(R.id.switch1).visibility = View.VISIBLE
//                Toast.makeText(this@MainActivity, "Ошибка сети", Toast.LENGTH_SHORT).show()
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jsonObj = JSONObject(result)
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val main = jsonObj.getJSONObject("main")
                val wind = jsonObj.getJSONObject("wind")

                speed = wind.getInt("speed")
                val windSpeed = speed.toString()+"км/ч"
                val weather_main = weather.getString("main")
                val temp = main.getInt("temp").toString()+"°C"
                val humidity = main.getString("humidity")+"%"

                findViewById<TextView>(R.id.temp).text = temp
                findViewById<TextView>(R.id.weather).text = weather_main
                findViewById<TextView>(R.id.wind).text = windSpeed
                findViewById<TextView>(R.id.humidity).text = humidity

                when (weather_main) {
                    "Thunderstorm" -> findViewById<TextView>(R.id.weather).text = "Шторм"
                    "Drizzle" -> findViewById<TextView>(R.id.weather).text = "Мелкий дождь"
                    "Rain" -> findViewById<TextView>(R.id.weather).text = "Дождь"
                    "Snow" -> findViewById<TextView>(R.id.weather).text = "Снег"
                    "Clear" -> findViewById<TextView>(R.id.weather).text = "Ясно"
                    "Clouds" -> findViewById<TextView>(R.id.weather).text = "Облачно"
                    else -> findViewById<TextView>(R.id.weather).text = "Туманно"
                }

                when (weather_main) {
                    "Thunderstorm" -> findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.thunderstorm)
                    "Drizzle" -> findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.shower_rain)
                    "Rain" -> findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.rain)
                    "Snow" -> findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.snow)
                    "Clear" -> findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.clear_sky)
                    "Clouds" -> findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.scattered_clouds)
                    else -> findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.mist)
                }

                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE

                findViewById<TextView>(R.id.city).visibility = View.VISIBLE
                findViewById<TextView>(R.id.temp).visibility = View.VISIBLE
                findViewById<TextView>(R.id.weather).visibility = View.VISIBLE
                findViewById<ImageView>(R.id.imageView).visibility = View.VISIBLE
                findViewById<TextView>(R.id.textWind).visibility = View.VISIBLE
                findViewById<TextView>(R.id.textHumidity).visibility = View.VISIBLE
                findViewById<TextView>(R.id.wind).visibility = View.VISIBLE
                findViewById<TextView>(R.id.humidity).visibility = View.VISIBLE
                findViewById<Switch>(R.id.switch1).visibility = View.VISIBLE

            } catch (e: Exception) {
//                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
            }
        }

    }
    fun clickStart(view: View) {
        val weather = findViewById<TextView>(R.id.weather).text
        if (lang == "English") {
            lang = "Russian"
            findViewById<TextView>(R.id.city).text = "Лондон, Великобритания"
            when (weather) {
                "Thunderstorm" -> findViewById<TextView>(R.id.weather).text = "Шторм"
                "Drizzle" -> findViewById<TextView>(R.id.weather).text = "Мелкий дождь"
                "Rain" -> findViewById<TextView>(R.id.weather).text = "Дождь"
                "Snow" -> findViewById<TextView>(R.id.weather).text = "Снег"
                "Clear" -> findViewById<TextView>(R.id.weather).text = "Ясно"
                "Clouds" -> findViewById<TextView>(R.id.weather).text = "Облачно"
                else -> findViewById<TextView>(R.id.weather).text = "Туманно"
            }
            findViewById<TextView>(R.id.textWind).text = "Ветер"
            findViewById<TextView>(R.id.textHumidity).text = "Влажность"
            findViewById<TextView>(R.id.wind).text = speed.toString()+"км/ч"
        } else {
            lang = "English"
            findViewById<TextView>(R.id.city).text = "London, GB"
            when (weather) {
                "Шторм" -> findViewById<TextView>(R.id.weather).text = "Thunderstorm"
                "Мелкий дождь" -> findViewById<TextView>(R.id.weather).text = "Drizzle"
                "Дождь" -> findViewById<TextView>(R.id.weather).text = "Rain"
                "Снег" -> findViewById<TextView>(R.id.weather).text = "Snow"
                "Ясно" -> findViewById<TextView>(R.id.weather).text = "Clear"
                "Облачно" -> findViewById<TextView>(R.id.weather).text = "Clouds"
                else -> findViewById<TextView>(R.id.weather).text = "Mist"
            }
            findViewById<TextView>(R.id.textWind).text = "Wind"
            findViewById<TextView>(R.id.textHumidity).text = "Humidity"
            findViewById<TextView>(R.id.wind).text = speed.toString()+"km/h"
        }
    }


}