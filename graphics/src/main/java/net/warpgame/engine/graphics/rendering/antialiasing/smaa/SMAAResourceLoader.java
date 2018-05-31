package net.warpgame.engine.graphics.rendering.antialiasing.smaa;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.texture.Texture2D;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * @author Jaca777
 * Created 2018-01-13 at 16
 */
@Service
public class SMAAResourceLoader {

    private static final int TEX_CHANNELS = 2;

    private static final String AREA_TEX_RESOURCE = "smaa_area.raw";
    private static final int AREA_TEX_WIDTH = 160;
    private static final int AREA_TEX_HEIGHT = 560;

    private static final String SEARCH_TEX_RESOURCE = "smaa_search.raw";
    private static final int SEARCH_TEX_WIDTH = 66;
    private static final int SEARCH_TEX_HEIGHT = 33;


    public Texture2D loadAreaTexture() {
        int size = AREA_TEX_WIDTH * AREA_TEX_HEIGHT * TEX_CHANNELS;
        InputStream areaTex = SMAAResourceLoader.class.getResourceAsStream(AREA_TEX_RESOURCE);
        ByteBuffer data = readData(size, areaTex);
        return new Texture2D(AREA_TEX_WIDTH, AREA_TEX_HEIGHT, GL30.GL_RG8, GL30.GL_RG, false, data);
    }

    public Texture2D loadSearchTexture() {
        InputStream searchTex = SMAAResourceLoader.class.getResourceAsStream(SEARCH_TEX_RESOURCE);
        int size = SEARCH_TEX_HEIGHT * SEARCH_TEX_WIDTH * TEX_CHANNELS;
        ByteBuffer data = readData(size, searchTex);
        return new Texture2D(SEARCH_TEX_WIDTH, SEARCH_TEX_HEIGHT, GL30.GL_RG8, GL30.GL_RG, false, data);
    }

    private ByteBuffer readData(int size, InputStream tex) {
        ByteBuffer texData = BufferUtils.createByteBuffer(1024 * 1024);
        ReadableByteChannel readableByteChannel = Channels.newChannel(tex);
        try {
            readableByteChannel.read(texData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        texData.rewind();
        return texData;
    }


}
