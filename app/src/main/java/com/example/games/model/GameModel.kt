package com.example.games.model

//import androidx.room.Entity
//import androidx.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import com.google.gson.annotations.SerializedName
//import kotlinx.android.parcel.Parcelize



data class GameList (

    @SerializedName("count")
    val  count:Int,
    @SerializedName("next")
    val  next:String,
    @SerializedName("previous")
    val  previous:String,
    @SerializedName("results")
    val  elements:List<GameModel>,

        ){


}

@Entity
data class GameModel (



    @SerializedName("id")
    val  id:Int,
    @SerializedName("name")
    val  name:String?,
    @SerializedName("background_image")
    val  background_image:String?,
    @SerializedName("released")
    val  released:String?,
    @SerializedName("rating")
    val  rating:String?,
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0

    ):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(background_image)
        parcel.writeString(released)
        parcel.writeString(rating)
        parcel.writeInt(uuid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameModel> {
        override fun createFromParcel(parcel: Parcel): GameModel {
            return GameModel(parcel)
        }

        override fun newArray(size: Int): Array<GameModel?> {
            return arrayOfNulls(size)
        }
    }


}
data class GameDetail (



    @SerializedName("id")
    val  id:Int,
    @SerializedName("name")
    val  name:String,
    @SerializedName("metacritic")
    val  metacritic:Int,
    @SerializedName("released")
    val  released:String,
    @SerializedName("description")
    val  description:String,
    @SerializedName("background_image")
    val  background_image:String,


    ){


}