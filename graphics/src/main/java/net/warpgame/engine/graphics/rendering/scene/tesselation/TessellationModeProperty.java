package net.warpgame.engine.graphics.rendering.scene.tesselation;

import net.warpgame.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2017-09-26 at 15
 */
public class TessellationModeProperty extends Property {
    private SceneTessellationMode tessellationMode;

    public TessellationModeProperty(SceneTessellationMode tessellationMode) {
        this.tessellationMode = tessellationMode;
    }

    public SceneTessellationMode getTessellationMode() {
        return tessellationMode;
    }

    public void setTessellationMode(SceneTessellationMode tessellationMode) {
        this.tessellationMode = tessellationMode;
    }
}
