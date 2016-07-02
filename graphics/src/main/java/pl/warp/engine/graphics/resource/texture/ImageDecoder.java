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


    /**
     * All encoded images must be the same size.
     */
    public static DecodedImagesArray decodePNGs(InputStream[] src, PNGDecoder.Format format) {
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

        return new DecodedImagesArray(buffers, w, h);
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

    public static final class DecodedImagesArray {
        private ByteBuffer[] buffers;
        private int w, h;

        public DecodedImagesArray(ByteBuffer[] buffers, int h, int w) {
            this.buffers = buffers;
            this.w = w;
            this.h = h;
        }

        public ByteBuffer[] getBuffers() {
            return buffers;
        }

        public int getW() {
            return w;
        }

        public int getH() {
            return h;
        }
    }

}
