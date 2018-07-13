package net.warpgame.engine.audio.decoder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Marcin on 11.05.2017.
 */
public class SoundDecoderManager {

    public SoundDecoderManager() {
        throw new UnsupportedOperationException();
    }

    public static SoundData decode(String pathToFile) throws IOException {
        String extension = FilenameUtils.getExtension(pathToFile);
        switch (extension) {
            case "wav":
                return WavFileDecoder.decode(pathToFile);
            case "ogg":
                return OggFileDecoder.decode(pathToFile);
            default:
                throw new RuntimeException(String.format("No support for %s file system", FilenameUtils.getExtension(pathToFile)));
        }
    }
}
