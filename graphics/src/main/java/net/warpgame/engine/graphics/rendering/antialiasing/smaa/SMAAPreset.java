package net.warpgame.engine.graphics.rendering.antialiasing.smaa;

/**
 * @author Jaca777
 * Created 2018-01-13 at 23
 */
public enum SMAAPreset {
    LOW("SMAA_PRESET_LOW"), MEDIUM("SMAA_PRESET_MEDIUM"), HIGH("SMAA_PRESET_HIGH"), ULTRA("SMAA_PRESET_ULTRA");

    private String define;

    SMAAPreset(String define) {
        this.define = define;
    }

    public String getDefine() {
        return define;
    }
}
