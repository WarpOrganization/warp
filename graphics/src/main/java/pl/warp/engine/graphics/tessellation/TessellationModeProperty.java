package pl.warp.engine.graphics.tessellation;

import pl.warp.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2017-09-26 at 15
 */
public class TessellationModeProperty extends Property {
    public static final String NAME = "tessellationMode";
    private TessellationMode tessellationMode;

    public TessellationModeProperty(TessellationMode tessellationMode) {
        super(NAME);
        this.tessellationMode = tessellationMode;
    }

    public TessellationMode getTessellationMode() {
        return tessellationMode;
    }

    public void setTessellationMode(TessellationMode tessellationMode) {
        this.tessellationMode = tessellationMode;
    }
}
