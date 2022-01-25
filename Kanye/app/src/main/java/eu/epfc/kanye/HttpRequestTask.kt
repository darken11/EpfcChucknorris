package eu.epfc.kanye

import android.content.Context
import android.os.Handler
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.lang.ref.WeakReference

class HttpRequestTask(
    private val url:String,
    private val applicationContext:Context,
    private val activityWeakRef:WeakReference<DisplayQuoteActivity>):Runnable {
    override fun run() {
        val okHttpClient = OkHttpClient()
        // build a request
        val request = Request.Builder().url(url).build()
        try {
            // send the request
            val response = okHttpClient.newCall(request).execute()
            // extract data from the response
            val responseString: String? = response.body?.string()
            if (responseString != null) {
                val mainHandler = Handler(applicationContext.mainLooper)
                mainHandler.post {
                    activityWeakRef.get()?.displayHttpResponse(responseString, true)
                }
            }
        }catch(exception :IOException){
            val mainHandler=Handler(applicationContext.mainLooper)
            mainHandler.post{
                val displayQuoteActivity: DisplayQuoteActivity?=activityWeakRef.get()
                displayQuoteActivity?.displayHttpResponse(null, false)
            }
        }
    }
}