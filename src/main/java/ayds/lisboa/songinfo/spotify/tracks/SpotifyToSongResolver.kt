package ayds.lisboa.songinfo.spotify.tracks

import com.google.gson.Gson
import ayds.lisboa.songinfo.spotify.Song
import com.google.gson.JsonObject

interface SpotifyToSongResolver {
    fun getSongFromExternalData(serviceData: String?): Song.SpotifySong?
}

private const val TRACKS = "tracks"
private const val ITEMS = "items"
private const val ID = "id"
private const val NAME = "name"
private const val ARTISTS = "artists"
private const val ALBUM = "album"
private const val IMAGES = "images"
private const val RELEASE_DATE = "release_date"
private const val RELEASE_DATE_PRECISION = "release_date_precision"
private const val URL = "url"
private const val EXTERNAL_URL = "external_urls"
private const val SPOTIFY = "spotify"

internal class JsonToSongResolver : SpotifyToSongResolver {

    override fun getSongFromExternalData(serviceData: String?): Song.SpotifySong? =
        try {
            serviceData?.getFirstItem()?.let { item ->
                Song.SpotifySong(
                  item.getId(), item.getSongName(), item.getArtistName(), item.getAlbumName(),
                  item.getReleaseDate(), item.getReleaseDatePrecision(), item.getSpotifyUrl(), item.getImageUrl()
                )
            }
        } catch (e: Exception) {
            null
        }

    private fun String?.getFirstItem(): JsonObject {
        val jobj = Gson().fromJson(this, JsonObject::class.java)
        val tracks = jobj[TRACKS].asJsonObject
        val items = tracks[ITEMS].asJsonArray
        return items[0].asJsonObject
    }

    private fun JsonObject.getId() = this[ayds.lisboa.songinfo.spotify.tracks.ID].asString

    private fun JsonObject.getSongName() = this[ayds.lisboa.songinfo.spotify.tracks.NAME].asString

    private fun JsonObject.getArtistName(): String {
        val artist = this[ayds.lisboa.songinfo.spotify.tracks.ARTISTS].asJsonArray[0].asJsonObject
        return artist[ayds.lisboa.songinfo.spotify.tracks.NAME].asString
    }

    private fun JsonObject.getAlbumName(): String {
        val album = this[ayds.lisboa.songinfo.spotify.tracks.ALBUM].asJsonObject
        return album[ayds.lisboa.songinfo.spotify.tracks.NAME].asString
    }

    private fun JsonObject.getReleaseDate(): String {
        val album = this[ayds.lisboa.songinfo.spotify.tracks.ALBUM].asJsonObject
        return album[ayds.lisboa.songinfo.spotify.tracks.RELEASE_DATE].asString
    }

    private fun JsonObject.getImageUrl(): String {
        val album = this[ayds.lisboa.songinfo.spotify.tracks.ALBUM].asJsonObject
        return album[ayds.lisboa.songinfo.spotify.tracks.IMAGES].asJsonArray[1].asJsonObject[ayds.lisboa.songinfo.spotify.tracks.URL].asString
    }

    private fun JsonObject.getSpotifyUrl(): String {
        val externalUrl = this[ayds.lisboa.songinfo.spotify.tracks.EXTERNAL_URL].asJsonObject
        return externalUrl[ayds.lisboa.songinfo.spotify.tracks.SPOTIFY].asString
    }

    private fun JsonObject.getReleaseDatePrecision(): String {
        val album = this[ayds.lisboa.songinfo.spotify.tracks.ALBUM].asJsonObject
        return album[ayds.lisboa.songinfo.spotify.tracks.RELEASE_DATE_PRECISION].asString
    }

}