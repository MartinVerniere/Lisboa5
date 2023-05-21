package ayds.lisboa.songinfo.spotify.auth

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

internal interface SpotifyAuthAPI {

    @POST("token")
    fun getToken(
      @Header("Authorization") credential: String,
      @Body tokenRequest: RequestBody
    ): Call<String>
}