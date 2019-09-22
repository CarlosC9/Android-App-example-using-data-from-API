package com.example.cuteanimals

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.AdapterView
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONArray

const val IP = "192.168.1.34"
const val URL_SERVER = "http://" + IP + ":40000"

class MainActivity : AppCompatActivity() {

    var myAdapter: MyAdapter = MyAdapter(ArrayList<Bitmap>())


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        recyclerView.adapter = this.myAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        requestAnimal("cat")
                    }
                    1 -> {
                        requestAnimal("dog")
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                var intent = Intent(this, API_Informtion::class.java)
                startActivity(intent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun requestAnimal(animal: String) {
        var queue = Volley.newRequestQueue(this)

        var jsonArrayRequest =
            JsonArrayRequest(Request.Method.GET, URL_SERVER + "/animal?type=" + animal, null,
                Response.Listener<JSONArray> { response ->

                    var urlsImageAnimalJSON = response
                    var urlsImageAnimal = ArrayList<String>()

                    for (i in 0..(urlsImageAnimalJSON.length() - 1)) {
                        urlsImageAnimal.add(urlsImageAnimalJSON.getJSONObject(i).getString("url"))
                    }

                    createImages(urlsImageAnimal)


                }, Response.ErrorListener {

                }
            )
        queue.add(jsonArrayRequest)
    }

    private fun createImages(urlsImageAnimal: ArrayList<String>) {
        var queue = Volley.newRequestQueue(this)
        var imagesBitmap = ArrayList<Bitmap>()

        urlsImageAnimal.forEach { url ->


            var imgRequest = ImageRequest(URL_SERVER + url,
                Response.Listener<Bitmap> { response ->
                    imagesBitmap.add(response)
                    if (imagesBitmap.size == urlsImageAnimal.size) {
                        this.myAdapter.update(imagesBitmap)
                    }
                },
                0, 0, Bitmap.Config.ARGB_8888,
                Response.ErrorListener {

                }
            )

            queue.add(imgRequest)

        }


    }

}

