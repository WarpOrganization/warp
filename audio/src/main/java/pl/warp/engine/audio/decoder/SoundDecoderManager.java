package pl.warp.engine.audio.decoder;

import org.apache.commons.io.FilenameUtils;
import pl.warp.engine.core.EngineContext;

import java.io.File;
import java.io.IOException;

/**
 * Created by Marcin on 11.05.2017.
 */
public class SoundDecoderManager {

    private static SoundFileDecoder decoders[] = {new WavFileDecoder(), new OggFileDecoder()};

    public static SoundData decode(String pathToFile) throws IOException {
        File soundFile = new File(EngineContext.GAME_DIR_PATH + pathToFile);
        //wiem, że łamie to SOLID, ale i tak będzie tylko używany OGG, więc jak będzie działać to WAV pójdzie sie gonić
        switch (FilenameUtils.getExtension(soundFile.getName())) {
            case "wav":
                return decoders[0].decode(soundFile);
            case "ogg":
                return decoders[1].decode(soundFile);
            default:
                throw new RuntimeException("No support for "
                        +FilenameUtils.getExtension(soundFile.getName())+" file system");
        }
    }
}
