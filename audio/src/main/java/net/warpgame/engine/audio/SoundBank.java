package net.warpgame.engine.audio;

import net.warpgame.engine.audio.decoder.SoundDecoderManager;
import net.warpgame.engine.audio.decoder.WavFileDecoder;
import org.apache.commons.io.FilenameUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import net.warpgame.engine.core.context.EngineContext;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * @author Hubertus
 *         Created 23.12.16
 */
public class SoundBank {
    TreeMap<String, Integer> sounds = new TreeMap<>();

    private AudioContext context;

    public SoundBank(AudioContext context){
        this.context = context;
    }

    public int getSound(String soundName) {
        return sounds.get(soundName);
    }

    public void loadDir(String path) throws IOException {
        Path myPath = Paths.get(EngineContext.CODESOURCE_DIR + path);
        Stream<Path> walk = Files.walk(myPath, 1);


        ArrayList<String> files = new ArrayList<>();
        Iterator<Path> it = walk.iterator();
        it.next();
        for (; it.hasNext(); ) {
            String p = it.next().toString();
            files.add(p);
        }

        IntBuffer buffer = BufferUtils.createIntBuffer(files.size());
        AL10.alGenBuffers(buffer);

        for (int i = 0; i < files.size(); i++) {
            SoundDecoderManager
                    .decode(EngineContext.CODESOURCE_DIR+ path + File.separator + FilenameUtils.getName(files.get(i)))
                    .fillBufferWithData(buffer.get(i));
            sounds.put(FilenameUtils.removeExtension(new File(files.get(i)).getName()), buffer.get(i));
        }
    }

}
