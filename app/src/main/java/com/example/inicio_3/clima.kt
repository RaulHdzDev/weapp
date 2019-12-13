package com.example.inicio_3

import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.kotlinweather.Common.Common
import com.example.inicio_3.Common.Helper
import com.example.kotlinweather.Model.OpenWeatherMap
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_clima.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_comments.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.imageView
import kotlinx.android.synthetic.main.fragment_home.txtDescription
import kotlinx.android.synthetic.main.fragment_home.txtCelsius as txtCelsius1

class clima : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener  {


    val PERMISSON_REQUEST_CODE = 1001
    val PLAY_SERVICE_RESOLUTION_REQUEST = 1000

    lateinit var cityy: String
    lateinit var desc: String
    lateinit var grad: String

    var mGoogleApiClient: GoogleApiClient?=null
    var mLocationRequest: LocationRequest?=null
    internal var openWeatherMap = OpenWeatherMap()




    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clima)


        this.requestPermission()

        if(checkPlayService()) {
            buildGoogleApiClient()
        }

        Handler().postDelayed({
            //start main activity
            startActivity(Intent(this@clima, MainActivity::class.java))
            //finish this activity
            finish()
        },4000)
    }




    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermission() {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)  != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            this.requestPermissions(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSON_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode){
            PERMISSON_REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(checkPlayService()){
                        buildGoogleApiClient()
                    }
                }
            }
        }
    }

    private fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build()
    }

    private fun checkPlayService(): Boolean {
        var resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)

        if(resultCode != ConnectionResult.SUCCESS){
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICE_RESOLUTION_REQUEST).show()
            } else {
                Toast.makeText(applicationContext, "The device is not supported", Toast.LENGTH_SHORT).show()
                finish()
            }
            return false
        }
        return true
    }

    override fun onConnected(p0: Bundle?) {
        createLocationRequest()
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 10000
        mLocationRequest!!.fastestInterval = 5000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)  != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
    }

    override fun onConnectionSuspended(p0: Int) {
        mGoogleApiClient!!.connect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i("ERROR", "Connection failed: "+p0.errorCode)
    }

    override fun onLocationChanged(location: Location?) {
        GetWeather().execute(Common.apiRequest(location!!.latitude.toString(), location!!.longitude.toString()))
    }

    override fun onStart() {
        super.onStart()
        if(mGoogleApiClient != null)
            mGoogleApiClient!!.connect()
    }

    override fun onDestroy() {
        mGoogleApiClient!!.disconnect()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        checkPlayService()
    }

    private inner class GetWeather: AsyncTask<String, Void, String>() {

        //internal var pd = ProgressDialog(this@MainActivity)

        /*override fun onPreExecute() {
            super.onPreExecute()
            pd.setTitle("Please wait...")
            pd.show()
        }*/

        override fun doInBackground(vararg params: String?): String {
            var stream:String?=null
            var urlString = params[0]
            val http = Helper()
            stream = http.getHTTPData(urlString)
            return stream
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(result!!.contains("Error: Not found city ")) {
                //pd.dismiss()
                return
            }
            val gson= Gson()
            val mType=object: TypeToken<OpenWeatherMap>(){}.type

            openWeatherMap = gson.fromJson<OpenWeatherMap>(result,mType)
            //pd.dismiss()

            //Set information into UI




            cityy = "${openWeatherMap.name}, ${openWeatherMap.sys!!.country}"
            txtCity.text= "${openWeatherMap.name}, ${openWeatherMap.sys!!.country}"

            txtDescription.text = "${openWeatherMap.weather!![0].description}"
            desc= "${openWeatherMap.weather!![0].description}"
            txtCelsius.text="${openWeatherMap.main!!.temp} °C"
            grad= "${openWeatherMap.main!!.temp} °C"
            Picasso.with(this@clima)
                    .load(Common.getImage(openWeatherMap.weather!![0].icon!!))
                    .into(imageView)

            val obj = datosdeClima( grad,desc,cityy)
            obj.save(applicationContext)
        }

    }

}
