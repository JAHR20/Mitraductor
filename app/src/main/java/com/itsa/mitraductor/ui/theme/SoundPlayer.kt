import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import java.io.IOException

class SoundPlayer {
    private val mediaPlayer = MediaPlayer()

    fun playSound(assetDescriptor: AssetFileDescriptor) {
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(assetDescriptor.fileDescriptor, assetDescriptor.startOffset, assetDescriptor.length)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
