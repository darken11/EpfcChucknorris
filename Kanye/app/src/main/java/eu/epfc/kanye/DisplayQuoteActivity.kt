package eu.epfc.kanye

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.concat
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import org.json.JSONObject
import java.lang.ref.WeakReference
import kotlin.random.Random

class DisplayQuoteActivity : AppCompatActivity() {
    lateinit var textOnGo:TextView
    lateinit var getNextQuote:Button
    lateinit var progression: ProgressBar
    lateinit var imageView:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_display_quote)
            textOnGo = findViewById(R.id.txtGo)
            imageView=findViewById(R.id.imgKanye)
            getNextQuote = findViewById(R.id.quote_button)
            progression = findViewById(R.id.progress)
            progression.visibility = View.INVISIBLE


            getNextQuote.setOnClickListener() {
                val url = "https://api.kanye.rest"

                val requestTask = HttpRequestTask(url, this.applicationContext, WeakReference(this))
                val requestThread = Thread(requestTask)
                requestThread.start()

                getNextQuote.isEnabled = false
                progression.visibility = View.VISIBLE
                textOnGo.visibility = View.INVISIBLE

        }
    }



    fun  displayHttpResponse(response :String?,completed :Boolean){
        if (response != null) {
                getNextQuote.isEnabled=true

                     // parse raw response to JSON object
                val jsonObject =  JSONObject(response.toString())
                val textToDisplay = jsonObject.getString("quote")

                    // we get the text that stored in Intent.Extra_Text
                val textFromIntent:String? =intent.getStringExtra(Intent.EXTRA_TEXT)
                val result = concat(textFromIntent, textToDisplay)

                   // display the message in the textView
                textOnGo.text = result

            ObjectAnimator.ofFloat(textOnGo, "alpha", 0f,1f).apply {
                duration = 1000
                start()
            }
            //we create scaleX and scaleY first with duration
           val scaleX= ObjectAnimator.ofFloat(textOnGo, "scaleX", 0.9f,1f)
            scaleX.duration = 1000

            val scaleY=ObjectAnimator.ofFloat(textOnGo, "scaleY", 0.9f,1f)
            scaleY.duration = 1000
            // we create AnimatorSet object and use method play and with for playing 2 animation in the same time
            val animatorSet=AnimatorSet()
            animatorSet.play(scaleX).with(scaleY)
            animatorSet.start()

            val imageIndex = Random.nextInt(from = 0, until = 10)
            val imageResource : Int = R.drawable.kanye1 + imageIndex;
            imageView.setImageResource(imageResource)



        }else{
            val failedMsg :String = getString(R.string.failed_msg)
               textOnGo.text = failedMsg

        }
        textOnGo.visibility=View.VISIBLE
        progression.visibility=View.INVISIBLE
    }
    // This Function is to get random phrases with specific name
     fun generatePhrase(phrases:List<String>):String{
         val randomValue = Random.nextInt(phrases.size)
        return  phrases[randomValue]
    }

}