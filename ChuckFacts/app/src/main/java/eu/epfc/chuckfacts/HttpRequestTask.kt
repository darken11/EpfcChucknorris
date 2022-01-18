package eu.epfc.chuckfacts

import android.content.Context
import android.os.Handler
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.lang.ref.WeakReference

class HttpRequestTask(private val url:String, private val applicationContext:Context,private val weakReferenceMainActivity: WeakReference<MainActivity>):Runnable {
    override fun run() {
// Create http client
        val okHttpClient = OkHttpClient()
        // build a request
        val request = Request.Builder().url(url).build()
        // send the request
        val response = okHttpClient.newCall(request).execute()
        // extract data from the response
        val responseString : String? = response.body?.string()

        if (responseString != null) {
            val mainHandler = Handler(applicationContext.getMainLooper())
            mainHandler.post {
                val mainActivity : MainActivity? = weakReferenceMainActivity.get()
                if (mainActivity != null) {
                    // run on main thread
                    val newJsonString=JSONObject(responseString)

                    mainActivity.displayHttpResponse( newJsonString.getString("value") )
                }
            }
        }

    }
}