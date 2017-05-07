package pl.warp.engine.audio.decoder;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by Marcin on 30.04.2017.
 */
public interface SoundFileDecoder {
    SoundDataDecoded decode(File soundFile) throws IOException;
}
