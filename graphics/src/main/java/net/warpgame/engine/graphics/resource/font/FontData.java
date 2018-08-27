package net.warpgame.engine.graphics.resource.font;

import java.util.Map;

/**
 * @author MarconZet
 * Created 26.08.2018
 */
public class FontData {
    private Character[] characters;
    private int characterCount;

    protected FontData(Map<Integer, Character> characterMap){
        this.characterCount = characterMap.size();
        this.characters = new Character[this.characterCount];
        characterMap.forEach((x, y) -> characters[x] = y);
    }
}
