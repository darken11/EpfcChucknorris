package eu.epfc.myrdaapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PrayOfActivity : AppCompatActivity() {
    lateinit var prayOfView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pray_of)
        title = "الدعاء المختار"

        prayOfView = findViewById(R.id.prayOfView)

        val textFromIntent: String? = intent.getStringExtra(Intent.EXTRA_TEXT)
        prayOfView.text = textFromIntent
    }
}