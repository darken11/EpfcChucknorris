import java.io.Serializable

class Planet constructor(
                         val name:String,
                         val rotationPeriod:Int,
                         val orbitalPeriod:Int,
                         val diameter:Int,
                         val climate:String,
                         val terrain:String,
                         val population:Long

                         ):Serializable{
    operator fun set(index: Int, value: String) {

    }


}