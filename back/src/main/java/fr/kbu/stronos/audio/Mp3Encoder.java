package fr.kbu.stronos.audio;

import java.io.ByteArrayOutputStream;
import javax.sound.sampled.AudioFormat;
import de.sciss.jump3r.lowlevel.LameEncoder;

/**
 * Mp3 encoder
 * 
 * @author Kevin Buntrock
 *
 */
public class Mp3Encoder {

  private final LameEncoder encoder;

  /**
   * Constructor
   * 
   * @param audioFormat
   */
  public Mp3Encoder(AudioFormat audioFormat) {
    encoder = new LameEncoder(audioFormat);

  }

  public byte[] encodePcmToMp3(byte[] pcm) {

    ByteArrayOutputStream mp3 = new ByteArrayOutputStream();
    byte[] buffer = new byte[encoder.getPCMBufferSize()];

    int bytesToTransfer = Math.min(buffer.length, pcm.length);
    int bytesWritten;
    int currentPcmPosition = 0;
    while (0 < (bytesWritten =
        encoder.encodeBuffer(pcm, currentPcmPosition, bytesToTransfer, buffer))) {
      currentPcmPosition += bytesToTransfer;
      bytesToTransfer = Math.min(buffer.length, pcm.length - currentPcmPosition);

      mp3.write(buffer, 0, bytesWritten);
    }

    return mp3.toByteArray();
  }

  public void close() {
    encoder.close();
  }

}
