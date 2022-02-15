package eu.epfc.myrdaapplication


import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference
import android.content.pm.PackageManager



class ThirdFragment : Fragment(R.layout.fragment_third), LocationListener {
    var lon: Double = 0.0
    var lat: Double = 0.0


    private lateinit var locationManager: LocationManager
    private lateinit var tvGpsLocation: String
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onLocationChanged(location: Location) {
        lon = location.longitude
        lat = location.latitude

        //localisationText = findViewById(R.id.localisationText)
        // we prepare our url for localisation
        val urlGps = "https://nominatim.openstreetmap.org/reverse?format=json&lat=$lat&lon=$lon"




        this.tvGpsLocation = "Latitude: $lat , Longitude: $lon"
    }


    fun showMap() {
        val searchLocal: String = this.tvGpsLocation

        val addressUri = Uri.parse("geo:0,0?q=$searchLocal")
        onShowMap(addressUri)
    }

    fun onShowMap(geoLocation: Uri) {
        val packageManager: PackageManager? =null
        val intent = Intent(Intent.ACTION_VIEW).apply {

            data = geoLocation
        }
        if (packageManager?.let { intent.resolveActivity(it) } != null) {
            startActivity(intent)
        }
    }

}