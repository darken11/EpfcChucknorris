package eu.epfc.chuckfacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

     var indexCategory:String="movie"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onGetButtonClicked(view: View){

        val textUrl: TextView = findViewById(R.id.text_url)
       val cats:Spinner=findViewById(R.id.cat_select)
        val chuckUrl = "https://api.chucknorris.io/jokes/random"

        val adapter=ArrayAdapter.createFromResource(this,R.array.cats,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        cats.adapter=adapter
        cats.onItemSelectedListener=this

        val indexCat:String= cats.adapter.getItem(adapter.getPosition(indexCategory)).toString()
        //we create the url with the params of category
        val chuckUrlSelected="$chuckUrl?category=${indexCat}"
        textUrl.text=chuckUrlSelected
        // we instance the class with the 3 params
        val requestTask = HttpRequestTask(chuckUrlSelected,this.applicationContext,WeakReference(this))
       indexCategory=indexCat
        //we create thread with the main function that we just create
        val requestThread = Thread(requestTask)
        // with methode start() we execute thread
        requestThread.start()


    }
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val index: String = parent?.getItemAtPosition(position).toString()
        indexCategory = index

    }
    fun displayHttpResponse(response : String?){
        val textChuckFact: TextView = findViewById(R.id.text_chuck_fact)
        if (response != null) {
            Log.d("MainActivity",response)
            textChuckFact.text=response
        }
    }


}