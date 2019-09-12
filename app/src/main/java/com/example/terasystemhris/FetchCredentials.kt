package com.example.terasystemhris

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

@SuppressLint("StaticFieldLeak")
class FetchCredentials( private var networkRequestInterface: NetworkRequestInterface) : AsyncTask<String, Int, String?>() {

    //val requestParam: HashMap<String, String>
    override fun onPreExecute() {
        super.onPreExecute()
        networkRequestInterface.beforeNetworkCall()
    }

    override fun doInBackground(vararg parts: String): String? {

        var result = ""
        var isTimedOut = false
        val stringURL: String = parts[0]
        val mURL = URL(stringURL)
        val userID: String? = parts[1]

        //try catch for MainActivity
        try {
            val password: String? = parts[2]
            var reqParam = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8")
            reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")

            with(mURL.openConnection() as HttpURLConnection) {
                requestMethod = "POST"
                var inputLine: String?
                handleTimeout { timedOut ->
                    if (timedOut) {
                        isTimedOut = true
                        disconnect()
                        result = "Connection Timeout"
                    }
                }

                if(!isTimedOut)
                {
                    val wr = OutputStreamWriter(outputStream)
                    wr.write(reqParam)
                    wr.flush()

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()

                        }
                        result = response.toString()
                        it.close()
                    }
                }
            }
        } catch (ex: Exception) {
            Log.d("", "Error in doInBackground ")
        }

        //try catch for Update
        try {
            val firstName: String? = parts[2]
            val middleName: String? = parts[3]
            val lastName: String? = parts[4]
            val emailAddress: String? = parts[5]
            val mobileNumber: String? = parts[6]
            val landline: String? = parts[7]
            var reqParam = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8")
            reqParam += "&" + URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(firstName, "UTF-8")
            reqParam += "&" + URLEncoder.encode("middleName", "UTF-8") + "=" + URLEncoder.encode(middleName, "UTF-8")
            reqParam += "&" + URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(lastName, "UTF-8")
            reqParam += "&" + URLEncoder.encode("emailAddress", "UTF-8") + "=" + URLEncoder.encode(emailAddress, "UTF-8")
            reqParam += "&" + URLEncoder.encode("mobileNumber", "UTF-8") + "=" + URLEncoder.encode(mobileNumber, "UTF-8")
            reqParam += "&" + URLEncoder.encode("landline", "UTF-8") + "=" + URLEncoder.encode(landline, "UTF-8")

            with(mURL.openConnection() as HttpURLConnection) {
                requestMethod = "POST"
                var inputLine: String?
                handleTimeout { timedOut ->
                    if (timedOut) {
                        isTimedOut = true
                        disconnect()
                        result = "Connection Timeout"
                    }
                }

                if(!isTimedOut)
                {
                    val wr = OutputStreamWriter(outputStream)
                    wr.write(reqParam)
                    wr.flush()

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()

                        }
                        result = response.toString()
                        it.close()
                    }
                }

            }
        } catch (ex: Exception) {
            Log.d("", "Error in doInBackground ")
        }

        //try catch for Get Time Logs and Get Leaves
        try {
            val reqParam = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8")

            with(mURL.openConnection() as HttpURLConnection) {
                requestMethod = "POST"
                var inputLine: String?
                handleTimeout { timedOut ->
                    if (timedOut) {
                        isTimedOut = true
                        disconnect()
                        result = "Connection Timeout"
                    }
                }

                if(!isTimedOut)
                {
                    val wr = OutputStreamWriter(outputStream)
                    wr.write(reqParam)
                    wr.flush()

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()

                        }
                        result = response.toString()
                        it.close()
                    }
                }

            }
        } catch (ex: Exception) {
            Log.d("", "Error in doInBackground ")
        }

        //try catch for Add Time Logs
        try {
            val logType: String? = parts[2]
            var reqParam = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8")
            reqParam += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(logType, "UTF-8")

            with(mURL.openConnection() as HttpURLConnection) {
                requestMethod = "POST"
                var inputLine: String?
                handleTimeout { timedOut ->
                    if (timedOut) {
                        isTimedOut = true
                        disconnect()
                        result = "Connection Timeout"
                    }
                }

                if(!isTimedOut)
                {
                    val wr = OutputStreamWriter(outputStream)
                    wr.write(reqParam)
                    wr.flush()

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()

                        }
                        result = response.toString()
                        it.close()
                    }
                }

            }
        } catch (ex: Exception) {
            Log.d("", "Error in doInBackground ")
        }

        //try catch for File Leave
        try {
            val type: String? = parts[2]
            val dateFrom: String? = parts[3]
            val dateTo: String? = parts[4]
            val time: String? = parts[5]
            var reqParam: String
            if(dateTo.isNullOrEmpty())
            {
                reqParam = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8")
                reqParam += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                reqParam += "&" + URLEncoder.encode("dateFrom", "UTF-8") + "=" + URLEncoder.encode(dateFrom, "UTF-8")
                reqParam += "&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")
            }
            else
            {
               reqParam = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8")
                reqParam += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                reqParam += "&" + URLEncoder.encode("dateFrom", "UTF-8") + "=" + URLEncoder.encode(dateFrom, "UTF-8")
                reqParam += "&" + URLEncoder.encode("dateTo", "UTF-8") + "=" + URLEncoder.encode(dateTo, "UTF-8")
                reqParam += "&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")
            }

            with(mURL.openConnection() as HttpURLConnection) {
                requestMethod = "POST"
                var inputLine: String?
                handleTimeout { timedOut ->
                    if (timedOut) {
                        isTimedOut = true
                        disconnect()
                        result = "Connection Timeout"
                    }
                }

                if(!isTimedOut)
                {
                    val wr = OutputStreamWriter(outputStream)
                    wr.write(reqParam)
                    wr.flush()

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()

                        }
                        result = response.toString()
                        it.close()
                    }
                }

            }
        } catch (ex: Exception) {
            Log.d("", "Error in doInBackground ")
        }
        return result
    }

    private fun handleTimeout(delay: Long = 3000, timeout: (Boolean) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            timeout(true)
        }, delay)
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        networkRequestInterface.afterNetworkCall(result)
    }

}