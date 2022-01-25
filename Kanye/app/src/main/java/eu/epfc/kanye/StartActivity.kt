package eu.epfc.kanye


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {
    lateinit var fillerPhrases:List<String>

    lateinit var textOnGo:EditText
    lateinit var btnOn:Button
    lateinit var generatePhrases:String
    lateinit var  titleKanye:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        textOnGo =findViewById(R.id.editedTextGo)
        titleKanye=findViewById(R.id.title)
        btnOn =findViewById(R.id.btnEdited)
        btnOn.setOnClickListener() {
             fillerPhrases=this.createFillerPhrases(textOnGo.text)
            var displayQuoteActivity=DisplayQuoteActivity()
            //for passe a message from activity to another activity, we use Intent( this: this activity,AnotherActivity::class.java)
            val intent= Intent(this, DisplayQuoteActivity::class.java)
            //and then i prepare the message or field that i would to passe to other activity
            generatePhrases=displayQuoteActivity.generatePhrase(fillerPhrases)
            // here i put the field that i would to pass in key, that i can take it from other activity
            intent.putExtra(Intent.EXTRA_TEXT,generatePhrases)
            startActivity(intent)
        }
    }

     private fun createFillerPhrases(name: Editable) : List<String> {
        var newFillerPhrases = listOf<String>(
            "Hey $name, ",
            "You know $name, ",
            "$name, let me tell you : ",
            "Seriously $name, ",
            "Well $name, ",
            "You see $name, ",
            "Basically $name, ",
            "Actually $name, ",
            "Clearly $name, ",
            "Believe me $name, "
        )
        return newFillerPhrases
    }

}