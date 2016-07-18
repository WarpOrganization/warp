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
    public static ImageData decodePNG(InputStream src, PNGDecoder.Format format) {
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
        return new ImageData(buffer, w, h);
    }

    public static final String[] CUBEMAP_ELEMENTS = new String[]{
            "posx",
            "negx",
            "posy",
            "negy",
            "posz",
            "negz"
    };


    public static ImageDataArray decodeCubemap(String cubemapPath) {
        InputStream[] streams = new InputStream[6];
        ClassLoader loader = ImageDecoder.class.getClassLoader();
        for (int i = 0; i < CUBEMAP_ELEMENTS.length; i++) {
            streams[i] = loader.getResourceAsStream(cubemapPath + "/" + CUBEMAP_ELEMENTS[i] + ".png");
        }
        return decodePNGs(streams, PNGDecoder.Format.RGBA);
    }

    private static ImageDataArray decodePNGs(InputStream[] src, PNGDecoder.Format format) {
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

        return new ImageDataArray(buffers, w, h);
    }

    public static ImageDataArray decodeSpritesheet(InputStream src, PNGDecoder.Format format, int columns, int rows) {
        ImageData data = decodePNG(src, format);
        checkSize(columns, rows, data);
        int subimagesNumber = columns * rows;
        ByteBuffer[] buffers = new ByteBuffer[subimagesNumber];
        initBuffers(buffers, data.getData().capacity());
        storeData(buffers, data, format.getNumComponents(), columns, rows);
        rewindBuffers(buffers);
        return new ImageDataArray(buffers, data.getWidth() / columns, data.getHeight() / rows);
    }

    private static void checkSize(int columns, int rows, ImageData data) {
        if (data.getWidth() % columns > 0 || data.getHeight() % rows > 0)
            throw new RuntimeException("Given spritesheet is not evenly dividable to " +
                    columns + " columns and " + rows + " rows");
    }

    private static void initBuffers(ByteBuffer[] buffers, int dataSize) {
        for (int i = 0; i < buffers.length; i++)
            buffers[i] = BufferUtils.createByteBuffer(dataSize / buffers.length);
    }

    private static void storeData(ByteBuffer[] buffers, ImageData data, int texelComponentsNumber, int columns, int rows) {
        ByteBuffer dataBuffer = data.getData();
        int width = data.getWidth();
        int height = data.getHeight();
        byte texelData[] = new byte[texelComponentsNumber];
        for (int i = 0; i < dataBuffer.capacity(); i++) {
            int x = i % width;
            int y = i / width;
            int column = (int) ((x / (float) width) * columns);
            int row = (int) ((y / (float) height) * rows);
            dataBuffer.get(texelData);
            buffers[columns * row + column].put(texelData);
        }
    }

    private static void rewindBuffers(ByteBuffer[] buffers) {
        for (ByteBuffer buffer : buffers)
            buffer.rewind();
    }
}
