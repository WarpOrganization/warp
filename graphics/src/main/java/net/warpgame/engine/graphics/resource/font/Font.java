package net.warpgame.engine.graphics.resource.font;

import net.warpgame.engine.graphics.resource.texture.ImageData;
import net.warpgame.engine.graphics.texture.Texture2D;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MarconZet
 * Created 26.08.2018
 */
public class Font {

    private String name;
    private Map<Integer, Character> metaData;
    private Map<Integer, FloatBuffer> textureData;
    private Texture2D fontImage;

    public Font(FontFile file, ImageData fontImage){
        this.metaData = file.getMetaData();
        this.fontImage = new Texture2D(fontImage);
        this.textureData = new HashMap<>();
        this.metaData.forEach(
                (i, c) -> {
                    FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
                    buffer.put((float)c.getxTextureCoord()/file.getSize());
                    buffer.put((float)c.getyTextureCoord()/file.getSize());
                    buffer.put((float)(c.getxTextureCoord()+c.getxTexSize())/file.getSize());
                    buffer.put((float)(c.getyTextureCoord()+c.getyTexSize())/file.getSize());
                    buffer.flip();
                    textureData.put(i, buffer);
                }
        );
        this.name = file.getFace();

    }
}
