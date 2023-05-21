package ayds.lisboa.songinfo.spotify.tracks

import ayds.lisboa.songinfo.spotify.Song
import ayds.lisboa.songinfo.spotify.SpotifyTrackService
import ayds.lisboa.songinfo.spotify.auth.SpotifyAccountService
import retrofit2.Response

internal class SpotifyTrackServiceImpl(
    private val spotifyTrackAPI: SpotifyTrackAPI,
    private val spotifyAccountService: SpotifyAccountService,
    private val spotifyToSongResolver: SpotifyToSongResolver,
) : SpotifyTrackService {

    override fun getSong(title: String): Song.SpotifySong? {
        val callResponse = getSongFromService(title)
        return spotifyToSongResolver.getSongFromExternalData(callResponse.body())
    }

    private fun getSongFromService(query: String): Response<String> {
        return spotifyTrackAPI.getTrackInfo("Bearer " + spotifyAccountService.token, query)
            .execute()
    }
}