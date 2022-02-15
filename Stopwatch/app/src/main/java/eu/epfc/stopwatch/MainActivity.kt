package eu.epfc.stopwatch

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var timeTextView: TextView
    lateinit var timeTextView2: TextView

    lateinit var stopBtn: Button
    lateinit var startBtn: Button
    lateinit var resetBtn: Button
    private var seconds: Int = 0
    private var running: Boolean = false
    var layout2: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        timeTextView = findViewById(R.id.timeTextView)


        startBtn = findViewById(R.id.startBtn)
        stopBtn = findViewById(R.id.stopBtn)
        resetBtn = findViewById(R.id.resetBtn)

        if (savedInstanceState != null) {
            setContentView(R.layout.activity2_main)
            this.seconds = savedInstanceState.getInt("second")
            this.running = savedInstanceState.getBoolean("bool")
            layout2 = true
        }
        runTimer()

    }


    private fun runTimer() {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {


            override fun run() {
                val hours: Int = seconds / 3600
                val minutes: Int = (seconds % 3600) / 60
                val secs: Int = seconds % 60
                val millSecs:Int= seconds%60 *1000

                // we want 1 digit for hours, and 2 digits for minutes and secs
                // -> we use the String format to get that result
                val time = String.format(Locale.getDefault(), "%d:%02d:%02d:%03d", hours, minutes, secs,millSecs)

                // timeTextView is our TextView, assigned in onCreate
                if (layout2) {
                    timeTextView2 = findViewById(R.id.timeTextView2)
                    timeTextView2.text = time
                } else {
                    timeTextView.text = time
                }
                // if the stopwatch is running -> increment the seconds
                if (running) {
                    seconds++
                }
                // call this runnable again in 1 sec (recursion)
                handler.postDelayed(this, 1000)
            }
        }
        // call the runnable for the first time
        handler.post(runnable)


    }

    fun onClickStart(view: View) {
        running = true
    }

    fun onClickStop(view: View) {
        running = false
    }

    fun onClickReset(view: View) {
        running = false
        seconds = 0
    }

    override fun onPause() {

        super.onPause()
        running=false

    }

    override fun onResume() {
        super.onResume()
        running = true
    }
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("second", seconds)
        outState.putBoolean("bool", running)

    }
    fun onShareButtonClicked(view: View){

        val secs: Int = seconds * 3600*60*60*3600

        // we want 1 digit for hours, and 2 digits for minutes and secs
        // -> we use the String format to get that result
        val time = String.format(Locale.getDefault(), "%03d", secs)

    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, "My Time is $time seconds")
    startActivity(intent)
}

}