package eu.epfc.myrdaapplication

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.location.*
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import org.json.JSONObject
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random

class MainFirstActivity : AppCompatActivity(), LocationListener {
    lateinit var fillerPhrases: List<String>
    lateinit var generateDhikr: String
    lateinit var dhikrBody: TextView
    lateinit var prayForPhrases: List<String>
    lateinit var generatePrayFor: String
    lateinit var prayOfBody: TextView
    lateinit var btnToday: TextView
    lateinit var btnQibla: TextView
    lateinit var btnPoints: TextView
    lateinit var btnMosque: TextView
    lateinit var texteVImage: TextView
    lateinit var currentDate: String
    lateinit var labelDate: TextView
    var lon: Double = 0.0
    var lat: Double = 0.0
    private lateinit var locationManager: LocationManager
    private lateinit var tvGpsLocation: String
    private val locationPermissionCode = 2
    lateinit var localisationText: TextView

    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-uuuu")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set by default the Orientation to Portrait screen and don't lets user change it with rotate
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setContentView(R.layout.activity_first_main)
        val context: Context = this

        val imageMore: ImageView = findViewById(R.id.imageMore)
        btnPoints = findViewById(R.id.btn_points)
        btnPoints.setBackgroundColor(ContextCompat.getColor(context, R.color.gris))
        val imageQibla: ImageView = findViewById(R.id.imageQibla)
        btnQibla = findViewById(R.id.btn_qibla)
        btnQibla.setBackgroundColor(ContextCompat.getColor(context, R.color.gris))
        val imageMosque: ImageView = findViewById(R.id.imageMosque)
        btnMosque = findViewById(R.id.btn_mosque)
        btnMosque.setBackgroundColor(ContextCompat.getColor(context, R.color.gris))

        val imageCalendar: ImageView = findViewById(R.id.imageCalendar)
        btnToday = findViewById(R.id.btn_today)
        btnToday.setBackgroundColor(ContextCompat.getColor(context, R.color.gris))


        btnToday.setOnClickListener {
            btnToday.setTextColor(ContextCompat.getColor(context, R.color.gold))
            imageCalendar.setImageResource(R.mipmap.cal_gold)
            btnToday.setTextSize(2, 18F)
            btnQibla.setTextColor(ContextCompat.getColor(context, R.color.white))
            imageQibla.setImageResource(R.mipmap.qib)
            btnPoints.setTextColor(ContextCompat.getColor(context, R.color.white))
            imageMore.setImageResource(R.mipmap.bar)
            btnMosque.setTextColor(ContextCompat.getColor(context, R.color.white))
            imageMosque.setImageResource(R.mipmap.mos)


        }
        btnQibla.setOnClickListener {
            btnQibla.setTextColor(ContextCompat.getColor(context, R.color.gold))
            imageQibla.setImageResource(R.mipmap.qib_gold)
            btnQibla.setTextSize(2, 18F)

            btnPoints.setTextColor(ContextCompat.getColor(context, R.color.white))
            imageMore.setImageResource(R.mipmap.bar)

            btnToday.setTextColor(ContextCompat.getColor(context, R.color.white))
            imageCalendar.setImageResource(R.mipmap.cal)

            btnMosque.setTextColor(ContextCompat.getColor(context, R.color.white))
            imageMosque.setImageResource(R.mipmap.mos)


        }
        btnMosque.setOnClickListener {
            btnMosque.setTextColor(ContextCompat.getColor(context, R.color.gold))
            imageMosque.setImageResource(R.mipmap.mos_gold)
            btnMosque.setTextSize(2, 18F)

            btnPoints.setTextColor(ContextCompat.getColor(context, R.color.white))
            imageMore.setImageResource(R.mipmap.bar)

            btnToday.setTextColor(ContextCompat.getColor(context, R.color.white))
            imageCalendar.setImageResource(R.mipmap.cal)

            btnQibla.setTextColor(ContextCompat.getColor(context, R.color.white))
            imageQibla.setImageResource(R.mipmap.qib)


        }
        btnPoints.setOnClickListener {
            btnPoints.setTextColor(ContextCompat.getColor(context, R.color.gold))
            imageMore.setImageResource(R.mipmap.bar_gold)
            btnPoints.setTextSize(2, 18F)

            btnToday.setTextColor(ContextCompat.getColor(context, R.color.white))
            imageCalendar.setImageResource(R.mipmap.cal)

            btnQibla.setTextColor(ContextCompat.getColor(context, R.color.white))
            imageQibla.setImageResource(R.mipmap.qib)

            btnMosque.setTextColor(ContextCompat.getColor(context, R.color.white))
            imageMosque.setImageResource(R.mipmap.mos)


        }




        fillerPhrases = this.createFillerPhrases()
        generateDhikr = generatePhrase(fillerPhrases)
        dhikrBody = findViewById(R.id.dhikrBody)
        dhikrBody.text = generateDhikr

        prayForPhrases = this.createPrayForPhrases()
        generatePrayFor = generatePhrase(prayForPhrases)
        prayOfBody = findViewById(R.id.prayOfBody)
        prayOfBody.text = generatePrayFor

        labelDate = findViewById(R.id.label_date)

//        val url = "https://api.pray.zone/v2/times/today.json?city=brussels"
//
//        val requestTask = HttpRequestTask(url, this.applicationContext, WeakReference(this))
//        val requestThread = Thread(requestTask)
//        requestThread.start()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }


        //normal date
        currentDate =
            java.text.SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault()).format(Date())
        val dayOfWeek: String = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())

        labelDate.text = currentDate

        texteVImage = findViewById(R.id.textVImage)
        texteVImage.text = dayOfWeek

        getLocation()
        btnMosque.setOnClickListener {
            showMap()
        }

    }


    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }


    override fun onLocationChanged(location: Location) {
        lon = location.longitude
        lat = location.latitude

        localisationText = findViewById(R.id.localisationText)
        // we prepare our url for localisation
        val urlGps = "https://nominatim.openstreetmap.org/reverse?format=json&lat=$lat&lon=$lon"

        val requestTaskGps = HttpRequestTask(urlGps, this.applicationContext, WeakReference(this))
        val requestThreadGps = Thread(requestTaskGps)
        requestThreadGps.start()


        tvGpsLocation = "Latitude: " + location.latitude + " , Longitude: " + location.longitude
    }


    fun showMap() {
        val searchLocal: String = tvGpsLocation
        val addressUri = Uri.parse("geo:0,0?q=$searchLocal")
        onShowMap(addressUri)

    }

    fun onShowMap(geoLocation: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {

            data = geoLocation
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    fun goNextPage(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, generateDhikr)
        startActivity(intent)
    }

    fun onShowDoua(view: View) {


        val intent = Intent(this, PrayOfActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, generatePrayFor)
        startActivity(intent)
    }


    fun getDate() {


        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        val c = Calendar.getInstance()
        val _date: String = sdf.format(c.time)
        val gregorianDate: LocalDate = LocalDate.parse(_date, dateFormatter)
        val islamicDates: HijrahDate = HijrahDate.from(gregorianDate)


    }


    /**
     * List of douaa
     */
    private fun createPrayForPhrases(): List<String> {
        return listOf(
            "اللَّهمَّ إنِّي أسألُكَ منَ الخيرِ كلِّهِ عاجلِهِ وآجلِهِ ما علمتُ منهُ وما لم أعلمْ وأعوذُ بكَ منَ الشَّرِّ كلِّهِ عاجلِهِ وآجلِهِ ما علمتُ منهُ وما لم أعلمْ اللَّهمَّ إنِّي أسألُكَ من خيرِ ما سألكَ عبدُكَ ونبيُّكَ وأعوذُ بكَ من شرِّ ما عاذَ منه عبدُكَ ونبيُّكَ اللَّهمَّ إنِّي أسألُكَ الجنَّةَ وما قرَّبَ إليها من قولٍ وعملٍ وأعوذُ بكَ منَ النَّارِ وما قرَّبَ إليها من قولٍ أو عملٍ وأسألُكَ أن تجعلَ كلَّ قضاءٍ قضيتَهُ لي خيرًا",

            "اللَّهُمَّ إنِّي أَعُوذُ بكَ مِنَ العَجْزِ، وَالْكَسَلِ، وَالْجُبْنِ، وَالْبُخْلِ، وَالْهَرَمِ، وَعَذَابِ، القَبْرِ اللَّهُمَّ آتِ نَفْسِي تَقْوَاهَا، وَزَكِّهَا أَنْتَ خَيْرُ مَن زَكَّاهَا، أَنْتَ وَلِيُّهَا وَمَوْلَاهَا، اللَّهُمَّ إنِّي أَعُوذُ بكَ مِن عِلْمٍ لا يَنْفَعُ، وَمِنْ قَلْبٍ لا يَخْشَعُ، وَمِنْ نَفْسٍ لا تَشْبَعُ، وَمِنْ دَعْوَةٍ لا يُسْتَجَابُ لَهَا",

            "اللهمَّ مالكَ الملكِ تُؤتي الملكَ مَن تشاءُ، وتنزعُ الملكَ ممن تشاءُ، وتُعِزُّ مَن تشاءُ، وتذِلُّ مَن تشاءُ، بيدِك الخيرُ إنك على كلِّ شيءٍ قديرٌ، رحمنُ الدنيا والآخرةِ ورحيمُهما، تعطيهما من تشاءُ، وتمنعُ منهما من تشاءُ، ارحمْني رحمةً تُغنيني بها عن رحمةِ مَن سواك",

            "اللَّهُمَّ لكَ الحَمْدُ، أنْتَ رَبُّ السَّمَوَاتِ والأرْضِ، لكَ الحَمْدُ أنْتَ قَيِّمُ السَّمَوَاتِ والأرْضِ ومَن فِيهِنَّ، لكَ الحَمْدُ أنْتَ نُورُ السَّمَوَاتِ والأرْضِ، قَوْلُكَ الحَقُّ، ووَعْدُكَ الحَقُّ، ولِقَاؤُكَ حَقٌّ، والجَنَّةُ حَقٌّ، والنَّارُ حَقٌّ، والسَّاعَةُ حَقٌّ، اللَّهُمَّ لكَ أسْلَمْتُ، وبِكَ آمَنْتُ، وعَلَيْكَ تَوَكَّلْتُ، وإلَيْكَ أنَبْتُ، وبِكَ خَاصَمْتُ، وإلَيْكَ حَاكَمْتُ، فَاغْفِرْ لي ما قَدَّمْتُ وما أخَّرْتُ، وأَسْرَرْتُ وأَعْلَنْتُ، أنْتَ إلَهِي لا إلَهَ لي غَيْرُكَ",

            "اللهم اجعلْ في قلبي نورًا، وفي سمعي نورًا، وعن يميني نورًا، وعن يساري نورًا، وفوقي نورًا، وتحتي نورًا، وأمامي نورًا، وخلفي نورًا، وأعظِمْ لي نورًا اللهم اجعلْ لي نورًا في قلبي، واجعلْ لي نورًا في سمعي، واجعلْ لي نورًا في بصري، واجعلْ لي نورًا عن يميني، ونورًا عن شمالي، واجعلْ لي نورًا من بين يديَّ، ونورًا من خلفي، وزِدْني نورًا، وزِدْني نورًا، وزِدْني نورًا",

            "اللهمَّ إني أسألُك العفوَ والعافيةَ، في الدنيا والآخرةِ، اللهمَّ إني أسألُك العفوَ والعافيةَ، في دِيني ودنيايَ وأهلي ومالي، اللهمَّ استُرْ عوراتي، وآمِنْ روعاتي، واحفظني من بين يدي، ومن خلفي، وعن يميني، وعن شمالي، ومن فوقي، وأعوذُ بك أن أُغْتَالَ من تحتي",

            "يا حيُّ يا قيُّومُ، برَحمتِكَ أستَغيثُ، أصلِح لي شأني كُلَّهُ، ولا تَكِلني إلى نَفسي طرفةَ عينٍ",

            "اللَّهمَّ إنِّي أسألُكَ عِلمًا نافعًا ورزقًا طيِّبًا وعملًا متقبَّلًا",

            "أَذْهِبِ البَاسَ، رَبَّ النَّاسِ، وَاشْفِ أَنْتَ الشَّافِي، لا شِفَاءَ إلَّا شِفَاؤُكَ، شِفَاءً لا يُغَادِرُ سَقَمًا",

            "بسمِ اللَّهِ الَّذي لا يضرُّ معَ اسمِهِ شيءٌ في الأرضِ ولَا في السَّماءِ، وَهوَ السَّميعُ العليمُ ثلاثَ مرَّاتٍ",

            "اللهمَّ عافِني في بدني، اللهمَّ عافِني في سمعي، اللهمَّ عافِني في بصري",

            "ربَّنا اللهُ الذي في السَّماءِ تقدَّس اسمُك أمرُك في السَّماءِ والأرضِ كما رحمتُك في السَّماءِ فاجعلْ رحمتَكَ في الأرضِ اغفرْ لنا حُوبَنا وخطايانا أنت ربُّ الطَّيِّبينَ أنزِلْ رحمةً وشفاءً من شفائِك على هذا الوجعِ فيبرأُ",

            "أَعوذُ بكلِماتِ اللهِ التامَّاتِ، الَّتي لا يُجاوِزُهُنَّ بَرٌّ ولا فاجرٌ، مِن شرِّ ما خلقَ، وذرأَ، وبرأَ، ومِن شرِّ ما ينزِلُ مِن السَّماءِ، ومِن شرِّ ما يعرُجُ فيها، ومِن شرِّ ما ذرأَ في الأرضِ وبرأَ، ومِن شرِّ ما يَخرجُ مِنها، ومِن شرِّ فِتَنِ اللَّيلِ والنَّهارِ، ومِن شرِّ كلِّ طارقٍ يطرُقُ، إلَّا طارقًا يطرقُ بِخَيرٍ، يا رَحمنُ",

            "أَعُوذُ بكَلِمَاتِ اللَّهِ التَّامَّةِ، مِن كُلِّ شيطَانٍ وهَامَّةٍ، ومِنْ كُلِّ عَيْنٍ لَامَّةٍ"


        )
    }

    /**
     * list of tasbih
     */
    private fun createFillerPhrases(): List<String> {
        return listOf(
            "اَعـوذُبِكَلِمـاتِ اللّهِ التّـامّـاتِ مِنْ شَـرِّ ما خَلَـق ",
            "اللَّهُمَّ بِكَ أَصْبَحْنَا وَبِكَ أَمْسَيْنَا وَبِكَ نَحْيَا وَبِكَ نَمُوتُ وَإِلَيْكَ الْمَصِيرُ ",
            "اللَّهُمَّ بِكَ أَمْسَيْنَا وَبِكَ أَصْبَحْنَا وَبِكَ نَحْيَا وَبِكَ نَمُوتُ وَإِلَيْكَ النُّشُورُ ",
            "لَا إِلٰهَ إِلَّا اللهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ، وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ ",
            "يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغِيثُ أَصْلِحْ لِي شَأْنِي كُلَّهُ وَلَا تَكِلْنِي إِلَى نَفْسِي طَرْفَةَ عَيْنٍ",
            "رَضيـتُ بِاللهِ رَبَّـاً وَبِالإسْلامِ ديـناً وَبِمُحَـمَّدٍ نَبِيّـاً ",
            "سُبْحَانَ اللهِ وَبِحَمْدِهِ: عَدَدَ خَلْقِهِ، وَرِضَا نَفْسِهِ، وَزِنَةَ عَرْشِهِ وَمِدَادَ كَلِمَاتِهِ ",
            "سُبْحَانَ اللهِ وَبِحَمْدِهِ",
            "أَسْتَغْفِرُ ٱللّٰهَ",
            "اللّٰهُ أَكْبَر",
            "لا حولة ولا قوة إلا بالله",
            "حسبي الله لا إله إلا الله"

        )
    }

    /**
     * generate data from list of data
     */
    private fun generatePhrase(phrases: List<String>): String {
        val randomValue = Random.nextInt(phrases.size)
        return phrases[randomValue]
    }


    fun displayHttpResponse(response: String?, completed: Boolean) {
        if (response != null) {


            // parse raw response to JSON object
            val jsonObject = JSONObject(response.toString())
            val jsonObj = jsonObject.optJSONObject("address")
            val ville = jsonObj.getString("municipality")

            localisationText.text = ville


//            // parse raw response to JSON object
//            val jsonObject = JSONObject(response.toString())
//            val jsonObj = jsonObject.optJSONObject("results")
//            val dataArray = jsonObj.optJSONArray("datetime")
//            val dateHij = dataArray.getJSONObject(0)
//            val hijri = dateHij.getJSONObject("date")["hijri"];
            // val currentDate:String=java.text.SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault()).format(Date())
            //Hijry date

            //var concat2st=concat("Hijry date $hijriDate  current Date  $currentDate")


        } else {
            val failedMsg: String = getString(R.string.failed_msg)
            labelDate.text = failedMsg

        }

    }


}