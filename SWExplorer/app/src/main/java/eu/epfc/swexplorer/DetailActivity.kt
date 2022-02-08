package eu.epfc.swexplorer

import Planet
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity(){
    lateinit var rotaion:TextView
    lateinit var orbital:TextView
    lateinit var diameter:TextView
    lateinit var climate:TextView
    lateinit var namePlanet:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        val selectedPlanet : Planet = intent.getSerializableExtra("planetObject") as
                Planet

        rotaion=findViewById(R.id.textViewRotation)
        rotaion.text=selectedPlanet.rotationPeriod.toString()
        orbital=findViewById(R.id.textViewOrbital)
        orbital.text=selectedPlanet.orbitalPeriod.toString()
        diameter=findViewById(R.id.textViewDiameter)
        diameter.text=selectedPlanet.diameter.toString()
        climate=findViewById(R.id.textViewClimate)
        climate.text=selectedPlanet.climate

        namePlanet=findViewById(R.id.planet_name)
        namePlanet.text=selectedPlanet.name



        // the file names are always lower case
        val planetImageName = selectedPlanet.name.lowercase()
        // get the resource ID of the image file from its name
        var resourceId = resources.getIdentifier(planetImageName, "drawable",
            packageName)
        // if there is no image for that planet
        if (resourceId == 0) {
            // get the resource from the generic_planet image
            resourceId = resources.getIdentifier("generic_planet", "drawable",
                packageName)
        }
         val imageViewPlanet:ImageView=findViewById(R.id.imageViewPlanet)
            imageViewPlanet.setImageResource(resourceId)
    }
}