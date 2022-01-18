package eu.epfc.chuckfacts

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
lateinit var textUrl: TextView
lateinit var cats:Spinner
lateinit var progression: ProgressBar
lateinit var textFactChuck:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textUrl = findViewById(R.id.text_url)
         cats=findViewById(R.id.cat_select)
        progression=findViewById(R.id.progress)
        textFactChuck=findViewById(R.id.text_chuck_fact)
        progression.visibility=View.INVISIBLE


    }

    fun onGetButtonClicked(view: View){
        val category=cats.selectedItem.toString()

        val chuckUrl = "https://api.chucknorris.io/jokes/random?category=$category"


        textUrl.text=chuckUrl
        // we instance the class with the 3 params
        val requestTask = HttpRequestTask(chuckUrl,this.applicationContext,WeakReference(this))
        //we create thread with the main function that we just create
        val requestThread = Thread(requestTask)
        // with methode start() we execute thread
        requestThread.start()
        progression.visibility=View.VISIBLE
        textFactChuck.visibility=View.INVISIBLE


    }

    @SuppressLint("SetTextI18n")
    fun displayHttpResponse(response : String?){
        if (response != null) {

            textFactChuck.text=response
        }else{
            textFactChuck.text = "There is No  Such content"
        }
        textFactChuck.visibility=View.VISIBLE
        progression.visibility=View.INVISIBLE
    }


}