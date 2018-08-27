package net.warpgame.engine.graphics.resource.font;

/**
 * Simple data structure class holding information about a certain glyph in the
 * font texture atlas. All sizes are for a font-size of 1.
 * 
 * @author Karl, MarconZet
 *
 */
public class Character {

	private int id;
	private int xTextureCoord;
	private int yTextureCoord;
	private int xTexSize;
	private int yTexSize;
	private int xOffset;
	private int yOffset;
	private int xAdvance;

    /**
     * @param id
     *            - the ASCII value of the character.
     * @param xTextureCoord
     *            - the x texture coordinate for the top left corner of the
     *            character in the texture atlas.
     * @param yTextureCoord
     *            - the y texture coordinate for the top left corner of the
     *            character in the texture atlas.
     * @param xTexSize
     *            - the width of the character in the texture atlas.
     * @param yTexSize
     *            - the height of the character in the texture atlas.
     * @param xOffset
     *            - the x distance from the curser to the left edge of the
     *            character's quad.
     * @param yOffset
     *            - the y distance from the curser to the top edge of the
     *            character's quad.
     *            - the height of the character's quad in screen space.
     * @param xAdvance
     *            - how far in pixels the cursor should advance after adding
     *            this character.
     */

    protected Character(int id, int xTextureCoord, int yTextureCoord, int xTexSize, int yTexSize, int xOffset, int yOffset, int xAdvance) {
        this.id = id;
        this.xTextureCoord = xTextureCoord;
        this.yTextureCoord = yTextureCoord;
        this.xTexSize = xTexSize;
        this.yTexSize = yTexSize;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xAdvance = xAdvance;
    }

    public int getId() {
        return id;
    }

    public int getxTextureCoord() {
        return xTextureCoord;
    }

    public int getyTextureCoord() {
        return yTextureCoord;
    }

    public int getxTexSize() {
        return xTexSize;
    }

    public int getyTexSize() {
        return yTexSize;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public int getxAdvance() {
        return xAdvance;
    }
}
