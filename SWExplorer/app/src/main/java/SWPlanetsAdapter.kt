
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eu.epfc.swexplorer.R

class SWPlanetsAdapter : RecyclerView.Adapter<SWPlanetsAdapter.PlanetViewHolder>() {

    var planetData : List<Planet>? = null
    @SuppressLint("NotifyDataSetChanged")
    set(planetData) {
        field=planetData
        notifyDataSetChanged()
    }


    inner class PlanetViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        // get a layoutInflater from the context attached to the parent view
        val layoutInflater=LayoutInflater.from(parent.context)
        // inflate the layout item_planet in a view
        val planetView = layoutInflater.inflate(R.layout.item_planet, parent, false)
        // create a new ViewHolder with the view planetView
        return PlanetViewHolder(planetView)
    }



    override fun getItemCount(): Int {
        val myPlanetData= planetData
        if (myPlanetData != null){
            return myPlanetData.size
        }else{
            return 0
        }

    }
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        val myPlanetData = planetData
        if( myPlanetData !=null) {
            val planet = myPlanetData[position]


            val itemViewGroup = holder.itemView as ViewGroup
            val planetNameTextView: TextView = itemViewGroup.findViewById(R.id.text_planet_name)
            planetNameTextView.text = planet.name
            planetNameTextView.setTextColor(R.color.black)
            when {
                planet.population==0L -> {
                    planetNameTextView.setBackgroundResource(R.color.white)
                }
                planet.population in 1..99999999 -> {
                    planetNameTextView.setBackgroundResource(R.color.vert)

                }
                planet.population in 1000000000..1000000000 -> {
                    planetNameTextView.setBackgroundResource(R.color.orange)

                }
                planet.population>1000000000 -> {
                    planetNameTextView.setBackgroundResource(R.color.rood)

                }
            }
        }
    }
}