package eu.epfc.myrdaapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PrayOfActivity : AppCompatActivity() {
    lateinit var prayOfView: TextView
    lateinit var scroll_wrapper:ScrollView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pray_of)
        title = "الدعاء المختار"

        scroll_wrapper=findViewById(R.id.scroll_wrapper)

        scroll_wrapper.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            scroll_wrapper.getChildAt(0).height-scroll_wrapper.height
        }
        prayOfView = findViewById(R.id.prayOfView)

        val textFromIntent: String? = intent.getStringExtra(Intent.EXTRA_TEXT)
        prayOfView.text = textFromIntent
    }
}