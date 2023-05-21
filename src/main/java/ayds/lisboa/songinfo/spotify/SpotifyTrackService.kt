package ayds.lisboa.songinfo.spotify


interface SpotifyTrackService {

    fun getSong(title: String): Song.SpotifySong?
}