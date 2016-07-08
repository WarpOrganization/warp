package pl.warp.engine.graphics.resource.texture;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 2016-07-08 at 21
 */
public class CubemapDecoder {

    public static final String[] CUBEMAP_ELEMENTS = new String[]{
            "posx",
            "negx",
            "posy",
            "negy",
            "posz",
            "negz"
    };


    public static DecodedCubemap decodeCubemap(String cubemapPath) {
        InputStream[] streams = new InputStream[6];
        ClassLoader loader = ImageDecoder.class.getClassLoader();
        for (int i = 0; i < CUBEMAP_ELEMENTS.length; i++) {
            streams[i] = loader.getResourceAsStream(cubemapPath + "/" + CUBEMAP_ELEMENTS[i] + ".png");
        }
        return decodePNGs(streams, PNGDecoder.Format.RGBA);
    }

    private static DecodedCubemap decodePNGs(InputStream[] src, PNGDecoder.Format format) {
        ByteBuffer[] buffers = new ByteBuffer[src.length];
        int w = 0, h = 0;
        for (int i = 0; i < src.length; i++) {
            try {
                PNGDecoder decoder = new PNGDecoder(src[i]);
                buffers[i] = BufferUtils.createByteBuffer(format.getNumComponents() * decoder.getWidth() * decoder.getHeight());
                decoder.decode(buffers[i], decoder.getWidth() * format.getNumComponents(), format);
                w = decoder.getWidth();
                h = decoder.getHeight();
                buffers[i].flip();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    src[i].close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new DecodedCubemap(buffers, w, h);
    }

    public static final class DecodedCubemap {
        private ByteBuffer[] data;
        private int w, h;

        public DecodedCubemap(ByteBuffer[] data, int h, int w) {
            this.data = data;
            this.w = w;
            this.h = h;
        }

        public ByteBuffer[] getData() {
            return data;
        }

        public int getWidth() {
            return w;
        }

        public int getHeight() {
            return h;
        }
    }
}
