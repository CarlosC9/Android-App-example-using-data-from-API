package com.example.cuteanimals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_api__informtion.*
import org.json.JSONObject

class API_Informtion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api__informtion)

        takeApiInformation()
    }

    private fun takeApiInformation() {
        var queue = Volley.newRequestQueue(this)

        var jsonRequest = JsonObjectRequest(Request.Method.GET, URL_SERVER + "/informationApi", null,
            Response.Listener<JSONObject> { response ->
                var information = ArrayList<String>()
                information.add(response.getString("name"))
                information.add(response.getString("version"))
                information.add(response.getString("description"))
                information.add(response.getString("author"))

                apiTextView.text = "Name: " + information.get(0) +
                        "\nVersion: " + information.get(1) +
                        "\nDescription: " + information.get(2) +
                        "\nAuthor: " + information.get(3)
            },
            Response.ErrorListener {

            })

        queue.add(jsonRequest)
    }
}
