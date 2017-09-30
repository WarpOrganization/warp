package pl.warp.engine.graphics.rendering.scene;

import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2017-09-26 at 15
 */
public class TessellationModeProperty extends Property {
    public static final String NAME = "tessellationMode";
    private SceneTessellationMode tessellationMode;

    public TessellationModeProperty(SceneTessellationMode tessellationMode) {
        super(NAME);
        this.tessellationMode = tessellationMode;
    }

    public SceneTessellationMode getTessellationMode() {
        return tessellationMode;
    }

    public void setTessellationMode(SceneTessellationMode tessellationMode) {
        this.tessellationMode = tessellationMode;
    }
}
