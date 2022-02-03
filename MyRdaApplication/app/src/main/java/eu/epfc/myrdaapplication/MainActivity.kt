package eu.epfc.myrdaapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {


    lateinit var dhikrTxtView: TextView
    lateinit var restTxt: TextView
    lateinit var incTxt: TextView
    var increment = 0

    /**
     * the main function
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dhikrTxtView = findViewById(R.id.dhikrView)
        restTxt = findViewById(R.id.resetTxt)
        incTxt = findViewById(R.id.incTxt)
        val textFromIntent: String? = intent.getStringExtra(Intent.EXTRA_TEXT)
        dhikrTxtView.text = textFromIntent
        dhikrTxtView.setOnClickListener {
            increment++
            incTxt.text = increment.toString()
        }


        incTxt.setOnClickListener {
            increment++

            incTxt.text = increment.toString()
        }
        restTxt.setOnClickListener {
            increment = 0
            incTxt.text = increment.toString()
        }


    }

    /**
     * get Information
     */
    fun getInformation(view: View) {

        /*nameTxtView.text = nameTxt.text
        nameTxtView.visibility=View.VISIBLE
        secondNameTxtView.text = secondNameTxt.text
        secondNameTxtView.visibility=View.VISIBLE

        emailTxtView.text = emailTxt.text
        emailTxtView.visibility=View.VISIBLE*/


    }

}