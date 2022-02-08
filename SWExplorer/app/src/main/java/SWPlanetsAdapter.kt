
import android.annotation.SuppressLint
import android.app.LauncherActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eu.epfc.swexplorer.MainActivity
import eu.epfc.swexplorer.R
import org.w3c.dom.Text

class SWPlanetsAdapter(itemViewPlanet:ListItemClickListener) : RecyclerView.Adapter<SWPlanetsAdapter.PlanetViewHolder>() {

        interface ListItemClickListener{
            fun onListItemClick(clickedItemIndex:Int)

        }


    val listItemClickListener:ListItemClickListener=itemViewPlanet
    var planetData : List<Planet>? = null
    @SuppressLint("NotifyDataSetChanged")
    set(planetData) {
        field=planetData
        notifyDataSetChanged()
    }


    inner class PlanetViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{



        override fun onClick(v: View?) {
            val position=this.bindingAdapterPosition
          // Log.d("Planet","clicked $position !!!")
            //on implement the interface ListItemClickListener quand vient that create, et on execute la methode onlistItem(pos)
            //this methode is for to  passe index of item clicked that we need in the activity main

            this@SWPlanetsAdapter.listItemClickListener.onListItemClick(position)
        }
        //we initialise the onClick when user click on item
        init {
            this.itemView.setOnClickListener(this)
        }

    }
    //this@MyAdapter mean that we call the reference of MyAdapter of @ the class parent
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        // get a layoutInflater from the context attached to the parent view
        val layoutInflater=LayoutInflater.from(parent.context)
        // inflate the layout item_planet in a view transformer le xml en view via la methode inflate
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
            val planetDiameterTextView:TextView=itemViewGroup.findViewById(R.id.text_diameter)
            val planetClimateTextView:TextView=itemViewGroup.findViewById(R.id.text_terrain)
            planetNameTextView.text = planet.name
            planetDiameterTextView.text=planet.diameter.toString()
            planetClimateTextView.text=planet.terrain
            planetNameTextView.setTextColor(R.color.black)

            val populationImageView1 : ImageView =
                itemViewGroup.findViewById(R.id.image_population1)
            val populationImageView2 : ImageView =
                itemViewGroup.findViewById(R.id.image_population2)
            val populationImageView3 : ImageView =
                itemViewGroup.findViewById(R.id.image_population3)


            when {
                planet.population==0L -> {
                    populationImageView1.visibility=View.INVISIBLE
                    populationImageView2.visibility=View.INVISIBLE
                    populationImageView3.visibility=View.INVISIBLE
                }
                planet.population in 1..99999999 -> {
                    populationImageView1.visibility=View.VISIBLE
                    populationImageView2.visibility=View.INVISIBLE
                    populationImageView3.visibility=View.INVISIBLE

                }
                planet.population in 1000000000..1000000000 -> {
                    populationImageView1.visibility=View.VISIBLE
                    populationImageView2.visibility=View.VISIBLE
                    populationImageView3.visibility=View.INVISIBLE

                }
                planet.population>1000000000 -> {
                    populationImageView1.visibility=View.VISIBLE
                    populationImageView2.visibility=View.VISIBLE
                    populationImageView3.visibility=View.VISIBLE

                }
            }
        }
    }
}