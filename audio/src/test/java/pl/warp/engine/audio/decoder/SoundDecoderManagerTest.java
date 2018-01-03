package pl.warp.engine.audio.decoder;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SoundDecoderManagerTest {

    public SoundDecoderManagerTest(){
    }

    @Ignore
    public void decodeOggTest() {

        SoundData data = null;
        try {
            data = SoundDecoderManager.decode("C:\\Users\\Marcin\\Music\\Stellardrone - Light Years.\\Stellardrone - Light Years - 10 Messier 45.ogg");
        } catch (Exception i) {
            fail(i.getMessage());
        }
        assertTrue(data.getResult() == 1);
    }
}
