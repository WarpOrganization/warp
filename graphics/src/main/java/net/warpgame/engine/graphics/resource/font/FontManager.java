package net.warpgame.engine.graphics.resource.font;

import net.warpgame.engine.core.context.service.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MarconZet
 * Created 27.08.2018
 */

@Service
public class FontManager {
    private Map<String, Font> fonts = new HashMap<>();

    public FontManager() {
    }

    public void addFont(Font font){
        fonts.put(font.getName(), font);
    }

    public Font findFont(String font){
        return fonts.get(font);
    }

    public void destroy(){
        fonts.forEach((x,y) -> y.destroy());
        fonts.clear();
    }
}
