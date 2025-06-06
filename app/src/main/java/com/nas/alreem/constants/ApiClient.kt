package com.nas.alreem.constants

import android.content.Context
import com.google.gson.GsonBuilder
import com.nas.alreem.R
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


class ApiClient (private val context: Context){

//var BASE_URL = "https://delta.mobatia.in:8083/nas-abudhabiv2/public/api/v1/"
  var BASE_URL = "https://cms.nasabudhabi.ae/api/v1/"

    val getClient: ApiInterface
        get() {
            try {
            val gson = GsonBuilder().setLenient().create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val certificatePinner = CertificatePinner.Builder()
//                .add("nas2025.mobatia.in", "")
                .add("delta.mobatia.in", "sha256/ohmltvh1K5StBefzEp0tYM2hSfbnru5lSGaCRVTHjmU=")
                .add("cms.nasabudhabi.ae", "sha256/6pm37LJ/fwfXrzobo2Ajdjxv+hIbGHwOr+RN39qcEPM=")
                .build()


            val client = OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(ApiInterface::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                throw RuntimeException("Failed to set up Retrofit with SSL pinning", e)
            }
        }


}