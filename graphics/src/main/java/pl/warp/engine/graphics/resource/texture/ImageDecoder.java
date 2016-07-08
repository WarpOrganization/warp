package pl.warp.engine.graphics.resource.texture;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * @author Jaca777
 *         Created 25.12.14 at 20:23
 */
public class ImageDecoder {

    /**
     * @param src Encoded PNG stream
     * @return Dimension of images (width, height)
     */
    public static DecodedImage decodePNG(InputStream src, PNGDecoder.Format format) {
        ByteBuffer buffer = null;
        int w = 0, h = 0;
        try {
            PNGDecoder decoder = new PNGDecoder(src);
            buffer = BufferUtils.createByteBuffer(format.getNumComponents() * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buffer, decoder.getWidth() * format.getNumComponents(), format);
            w = decoder.getWidth();
            h = decoder.getHeight();
            buffer.flip();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                src.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new DecodedImage(buffer, w, h);
    }


    public static final class DecodedImage {
        private ByteBuffer buffer;
        private int w, h;

        public DecodedImage(ByteBuffer buffer, int w, int h) {
            this.buffer = buffer;
            this.w = w;
            this.h = h;
        }

        public ByteBuffer getData() {
            return buffer;
        }

        public int getW() {
            return w;
        }

        public int getH() {
            return h;
        }

    }

}
