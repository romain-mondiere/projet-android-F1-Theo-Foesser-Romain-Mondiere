package com.example.tp2_foesser_mondiere.Model
import kotlinx.serialization.Serializable

@Serializable
data class Beer(val name:String, val description:String, val tagline:String, val image_url:String, val abv:Float,
                internal val food_pairing:Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Beer

        if (name != other.name) return false
        if (description != other.description) return false
        if (tagline != other.tagline) return false
        if (image_url != other.image_url) return false
        if (abv != other.abv) return false
        if (!food_pairing.contentEquals(other.food_pairing)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + tagline.hashCode()
        result = 31 * result + image_url.hashCode()
        result = 31 * result + abv.hashCode()
        result = 31 * result + food_pairing.contentHashCode()
        return result
    }
}