package pl.warp.engine.audio.decoder;

import java.io.File;
import java.io.IOException;

/**
 * Created by Marcin on 30.04.2017.
 */
public interface SoundFileDecoder {
    SoundData decode(File soundFile) throws IOException;
}
