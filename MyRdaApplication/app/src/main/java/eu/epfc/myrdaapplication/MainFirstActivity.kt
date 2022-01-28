package eu.epfc.myrdaapplication

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.concat
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class MainFirstActivity:AppCompatActivity() {
    lateinit var fillerPhrases:List<String>
    lateinit var generateDhikr:String
    lateinit var  dhikrBody:TextView
    lateinit var labelDate:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_main)
        title="ٱلسَّلَامُ عَلَيْكُمْ"
        fillerPhrases = this.createFillerPhrases()
        generateDhikr = generatePhrase(fillerPhrases)
        dhikrBody = findViewById(R.id.dhikrBody)
        dhikrBody.text = generateDhikr
        labelDate = findViewById(R.id.label_date)

        val url = "https://api.pray.zone/v2/times/today.json?city=brussels"

        val requestTask = HttpRequestTask(url, this.applicationContext, WeakReference(this))
        val requestThread = Thread(requestTask)
        requestThread.start()

        //normal date
        val currentDate:String=java.text.SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault()).format(Date())

        labelDate.text=currentDate


    }

    fun goNextPage(view:View){
        val intent= Intent(this, MainActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT,generateDhikr)
        startActivity(intent)
    }


    private fun createFillerPhrases(): List<String> {
        return listOf(
            "اَعـوذُبِكَلِمـاتِ اللّهِ التّـامّـاتِ مِنْ شَـرِّ ما خَلَـق ",
            "اللَّهُمَّ بِكَ أَصْبَحْنَا وَبِكَ أَمْسَيْنَا وَبِكَ نَحْيَا وَبِكَ نَمُوتُ وَإِلَيْكَ الْمَصِيرُ ",
            "اللَّهُمَّ بِكَ أَمْسَيْنَا وَبِكَ أَصْبَحْنَا وَبِكَ نَحْيَا وَبِكَ نَمُوتُ وَإِلَيْكَ النُّشُورُ ",
            "لَا إِلٰهَ إِلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ ",
            "يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغِيثُ أَصْلِحْ لِي شَأْنِي كُلَّهُ وَلَا تَكِلْنِي إِلَى نَفْسِي طَرْفَةَ عَيْنٍ",
            "رَضيـتُ بِاللهِ رَبَّـاً وَبِالإسْلامِ ديـناً وَبِمُحَـمَّدٍ نَبِيّـاً ",
            "سُبْحَانَ اللهِ وَبِحَمْدِهِ: عَدَدَ خَلْقِهِ، وَرِضَا نَفْسِهِ، وَزِنَةَ عَرْشِهِ وَمِدَادَ كَلِمَاتِهِ ",
            "سُبْحَانَ اللهِ وَبِحَمْدِهِ"

        )
    }
    private fun generatePhrase(phrases:List<String>):String{
        val randomValue = Random.nextInt(phrases.size)
        return  phrases[randomValue]
    }



     fun  displayHttpResponse(response :String?,completed :Boolean){
        if (response != null) {

            // parse raw response to JSON object
            val jsonObject =  JSONObject(response.toString())
            val jsonObj = jsonObject.optJSONObject("results")
            val dataArray =jsonObj.optJSONArray("datetime")
            val dateHij=dataArray.getJSONObject(0)
            val hijri=dateHij.getJSONObject("date")["hijri"];
           // val currentDate:String=java.text.SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault()).format(Date())
           //Hijry date

            //var concat2st=concat("Hijry date $hijriDate  current Date  $currentDate")




        }else{
            val failedMsg :String = getString(R.string.failed_msg)
            labelDate.text = failedMsg

        }

    }

}