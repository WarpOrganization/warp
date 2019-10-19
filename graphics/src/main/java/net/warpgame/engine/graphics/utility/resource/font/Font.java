package net.warpgame.engine.graphics.utility.resource.font;

import net.warpgame.engine.graphics.utility.resource.texture.ImageData;
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

    public Font(FontFile file, ImageData fontImage){
        this.metaData = file.getMetaData();
        this.textureData = new HashMap<>();
        this.metaData.forEach(
                (i, c) -> calcTextureCords(file, i, c)
        );
        this.name = file.getFace();

    }

    private void calcTextureCords(FontFile file, Integer i, Character c) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
        buffer.put((float)c.getxTextureCoord()/file.getSize());
        buffer.put((float)c.getyTextureCoord()/file.getSize());
        buffer.put((float)(c.getxTextureCoord()+c.getxTexSize())/file.getSize());
        buffer.put((float)(c.getyTextureCoord()+c.getyTexSize())/file.getSize());
        buffer.flip();
        textureData.put(i, buffer);
    }

    protected void destroy(){

    }

    public String getName() {
        return name;
    }

    public Map<Integer, Character> getMetaData() {
        return metaData;
    }

    public Map<Integer, FloatBuffer> getTextureData() {
        return textureData;
    }

}
