package nacc.sergey.testweatherapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL


const val KEY = "9364486e2b48d79faa1ed2f586338992"

class MainActivity : AppCompatActivity() {

    private var userField: EditText? = null
    private var mainBtn: Button? = null
    private var resultInfo: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userField = findViewById(R.id.user_field)
        mainBtn = findViewById(R.id.main_btn)
        resultInfo = findViewById(R.id.result_info)

        mainBtn?.setOnClickListener {
            if (userField?.text.toString().trim() == "")
                Toast.makeText(this, "Введите город!", Toast.LENGTH_SHORT).show()
            else {
                val city = userField?.text.toString()
                val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$KEY&units=metric&lang=ru"

                doAsync {
                    val apiResponse = URL(url).readText()

                    val weather = JSONObject(apiResponse).getJSONArray("weather")
                    val desc = weather.getJSONObject(0).getString("description")

                    val main = JSONObject(apiResponse).getJSONObject("main")
                    val temp = main.getString("temp").toDouble()

                    resultInfo?.text = "Температура: $temp`C\n$desc"
                }
            }
        }
    }
}