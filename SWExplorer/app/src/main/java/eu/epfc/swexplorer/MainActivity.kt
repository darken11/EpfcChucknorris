package eu.epfc.swexplorer

import Planet
import SWPlanetsAdapter
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var resourceFromJson :String
     lateinit var planetList:MutableList<Planet>
     lateinit var btnVertical:Button
     lateinit var btnGrid:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recycleView:RecyclerView=findViewById(R.id.recycleView)
        val planetAdapter =SWPlanetsAdapter()


        resourceFromJson= this.loadJSONFromAsset().toString()
        planetList= mutableListOf()

        val rootJsonObject=JSONObject(resourceFromJson)
        // get the "results" element array from the root JSON element
        val jsonPlanets = rootJsonObject.getJSONArray("results")
        // for all the planets listed in the “results” element
        for (i in 0 until jsonPlanets.length()){
            // get the JSON element corresponding to the planet
            val jsonPlanet = jsonPlanets.getJSONObject(i)
            val name = jsonPlanet.getString("name")
            val rotationPeriod = jsonPlanet.getInt("rotation_period")
            val orbitalPeriod = jsonPlanet.getInt("orbital_period")
            val diameter = jsonPlanet.getInt("diameter")
            val climate=jsonPlanet.getString("climate")
            val terrain=jsonPlanet.getString("terrain")
            val population=jsonPlanet.getLong("population")




            // create a Planet object and copy all the information
            //from the JSONObject to the Planet Object
            val newPlanet = Planet(name,rotationPeriod,orbitalPeriod,diameter,climate,terrain,
                population
                )

            planetList.add(newPlanet)
            planetAdapter.planetData=planetList

        }



        /**
         * i store the value of the current activity into the context
         */
        val context: Context =this
        val layoutManager =LinearLayoutManager(context)


        /**
         * i set the value of the adapter in the recycleView first
         */

        recycleView.adapter=planetAdapter
        /**
         * i store the value of layoutManager to the recycleView
         */
        recycleView.layoutManager = layoutManager


        /**
         * the onclick methode listener event to update the view one is vertical view other one is grid system
         */
        btnVertical=findViewById(R.id.btn_1)
        btnVertical.setOnClickListener{
            val layoutManagerVert=LinearLayoutManager(context)
            recycleView.layoutManager=layoutManagerVert
        }
        btnGrid=findViewById(R.id.btn_2)
        btnGrid.setOnClickListener{
            val layoutManagerGrid=GridLayoutManager(context,2)
            recycleView.layoutManager=layoutManagerGrid
        }




    }


    /**
     * This function read from assets/planets.json file and return string
     */
    private fun loadJSONFromAsset(): String? {
        val jsonContent :String
        try{
            val inputStream=applicationContext.assets.open("planets.json")
            val reader = BufferedReader(inputStream.reader())
            jsonContent=reader.readText()

            reader.close()
        }catch (ex: IOException){
            ex.printStackTrace()
            return null
        }

        return jsonContent

    }

}