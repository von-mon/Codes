package com.example.networktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Xml
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.lang.Exception
import java.lang.StringBuilder
import java.lang.reflect.Executable
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendRequestBtn.setOnClickListener {
            //sendRequestWithHttpURLConnection()
            sendRequestWithOkHttp()
        }

        getAppDataBtn.setOnClickListener {
            val appService = ServiceCreator.create(AppService::class.java)
            appService.getAppData().enqueue(object : Callback<List<App>> {
                override fun onFailure(call: Call<List<App>>, t: Throwable) {
                    Log.d("MainActivity","onFailure: error")
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<List<App>>, response: Response<List<App>>) {
                    val list = response.body()
                    Log.d("MainActivity", "onResponse: $list")
                    if(list != null){
                        for (app in list){
                            Log.d("MainActivity", "onResponse:id is ${app.id} ")
                            Log.d("MainActivity", "onResponse:name is ${app.name} ")
                            Log.d("MainActivity", "onResponse:version is ${app.version} ")
                        }
                    }
                }

            })
        }
    }

    private fun sendRequestWithOkHttp(){
        thread {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("http://192.168.124.64:8080/get_data.json")
                    .build()
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
                    //showResponse(responseData)
                    //parseXMLWithPull(responseData)
                    //parseJsonWithJSONObject(responseData)
                    parseJSONWithGSON(responseData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun parseJSONWithGSON(jsonData: String){
        val gson = Gson()
        val typeOf = object : TypeToken<List<App>>() {}.type
        val appList = gson.fromJson<List<App>>(jsonData,typeOf)
        for (app in appList){
            Log.d("MainActivity", "parseJSONWithGSON: id is ${app.id}")
            Log.d("MainActivity", "parseJSONWithGSON: name is ${app.name}")
            Log.d("MainActivity", "parseJSONWithGSON: version is ${app.version}")
        }
    }

    private fun parseJsonWithJSONObject(jsonData:String){
        try {
            val jsonArray = JSONArray(jsonData)
            for (i in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getString("id")
                val name = jsonObject.getString("name")
                val version = jsonObject.getString("version")
                Log.d("MainActivity", "parseJsonWithJSONObject:id is $id")
                Log.d("MainActivity", "parseJsonWithJSONObject:name is $name")
                Log.d("MainActivity", "parseJsonWithJSONObject:version is $version")
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun parseXMLWithPull(xmlData:String){
        try {
            val factory = XmlPullParserFactory.newInstance()
            val xmlPullParser = factory.newPullParser()
            xmlPullParser.setInput(StringReader(xmlData))
            var eventType = xmlPullParser.eventType
            var id = ""
            var name = ""
            var version = ""
            while (eventType != XmlPullParser.END_DOCUMENT){
                val nodeName = xmlPullParser.name
                when(eventType){
                    //开始解析某个节点
                    XmlPullParser.START_TAG->{
                        when(nodeName){
                            "id"->id=xmlPullParser.nextText()
                            "name"-> name = xmlPullParser.nextText()
                            "version"->version = xmlPullParser.nextText()
                        }
                    }
                    XmlPullParser.END_TAG->{
                        if ("app"==nodeName){
                            Log.d("MainActivity","id is $id")
                            Log.d("MainActivity","name is $name")
                            Log.d("MainActivity","version is $version")
                        }
                    }
                }
                eventType = xmlPullParser.next()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun sendRequestWithHttpURLConnection(){
        thread {
            var connection: HttpURLConnection?=null
            try {
                val response  = StringBuilder()
                val url = URL("https://www.baidu.com")
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout  = 8000
                val input = connection.inputStream
                //对输入流读取
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                showResponse(response.toString())
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                connection?.disconnect()
            }
        }
    }

    private fun showResponse(response:String){
        runOnUiThread {
            responseTest.text = response
        }
    }
}