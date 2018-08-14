package net.warpgame.engine.graphics.rendering.scene.gbuffer;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.config.ConfigValue;
import net.warpgame.engine.core.context.config.EnableConfig;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.window.Display;

/**
 * @author Jaca777
 * Created 2017-11-11 at 15
 */
@Service
@Profile("graphics")
@EnableConfig
public class GBufferManager {

    private GBuffer gBuffer = new GBuffer();
    private Display display;

    public GBufferManager(Config config) {
        this.display = config.getValue("graphics.display");
    }

    public void initialize() {
        this.gBuffer.generate();
        this.gBuffer.initWithSize(display.getWidth(), display.getHeight());
    }

    @ConfigValue(value = "graphics.display", dispatcher = "graphics", onlyOnChanges = true)
    public void resize(Display newDisplay) {
        this.display = newDisplay;
        this.gBuffer.initWithSize(newDisplay.getWidth(), newDisplay.getHeight());
    }

    public GBuffer getGBuffer() {
        return gBuffer;
    }
}
