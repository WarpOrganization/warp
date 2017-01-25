package pl.warp.engine.audio;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import pl.warp.engine.core.EngineContext;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
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

    public int getSound(String soundName) {
        return sounds.get(soundName);
    }

    public void loadDir(String path) throws URISyntaxException, IOException, UnsupportedAudioFileException {
        Path myPath = Paths.get(EngineContext.GAME_DIR_PATH + path);
        System.out.println(EngineContext.GAME_DIR_PATH + path);
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
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(EngineContext.GAME_DIR_PATH + path + File.separator + FilenameUtils.getName(files.get(i))));

            AudioFormat format = stream.getFormat();

            int openALFormat = getOpenALFormat(format);

            byte[] b = IOUtils.toByteArray(stream);
            ByteBuffer data = BufferUtils.createByteBuffer(b.length).put(b);
            data.flip();

            AL10.alBufferData(buffer.get(i), openALFormat, data, (int) format.getSampleRate());
            sounds.put(FilenameUtils.removeExtension(new File(files.get(i)).getName()), buffer.get(i));
        }
    }

    public static int getOpenALFormat(AudioFormat format) {
        final int MONO = 1;
        final int STEREO = 2;
        int openALFormat = -1;
        switch (format.getChannels()) {
            case MONO:
                switch (format.getSampleSizeInBits()) {
                    case 8:
                        openALFormat = AL10.AL_FORMAT_MONO8;
                        break;
                    case 16:
                        openALFormat = AL10.AL_FORMAT_MONO16;
                        break;
                }
                break;
            case STEREO:
                switch (format.getSampleSizeInBits()) {
                    case 8:
                        openALFormat = AL10.AL_FORMAT_STEREO8;
                        break;
                    case 16:
                        openALFormat = AL10.AL_FORMAT_STEREO16;
                        break;
                }
                break;
        }
        return openALFormat;
    }
}
