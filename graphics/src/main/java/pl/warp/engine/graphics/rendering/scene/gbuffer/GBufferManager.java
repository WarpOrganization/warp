package pl.warp.engine.graphics.rendering.scene.gbuffer;

import pl.warp.engine.core.context.config.ConfigValue;
import pl.warp.engine.core.context.config.EnableConfig;
import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.graphics.window.Display;

/**
 * @author Jaca777
 * Created 2017-11-11 at 15
 */
@Service
@EnableConfig
public class GBufferManager {

    private GBuffer gBuffer = new GBuffer();

    @ConfigValue(value = "graphics.display", dispatcher = "graphics")
    public void onDisplayChanged(Display newDisplay) {
        this.gBuffer.generate();
        this.gBuffer.initWithSize(newDisplay.getWidth(), newDisplay.getHeight());
    }

    public GBuffer getGBuffer() {
        return gBuffer;
    }
}
