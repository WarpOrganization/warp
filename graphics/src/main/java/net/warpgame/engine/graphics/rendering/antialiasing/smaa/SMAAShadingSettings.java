package net.warpgame.engine.graphics.rendering.antialiasing.smaa;

import net.warpgame.engine.core.context.config.Config;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.program.extendedglsl.preprocessor.ConstantField;
import net.warpgame.engine.graphics.window.Display;

@Service
@Profile("graphics")
public class SMAAShadingSettings {

    private Config config;
    private ConstantField constants;

    public SMAAShadingSettings(Config config) {
        this.config = config;
        Display display = config.getValue("graphics.display");
        SMAAPreset preset = config.getValue("graphics.rendering.smaa.preset");
        this.constants = new ConstantField()
                .set(preset.getDefine(), 1)
                .set("SMAA_RT_METRICS", "float4(1.0 / 1280.0, 1.0 / 720.0, 1280.0, 720.0)");
    /*            .set("SMAA_RT_METRICS", "float4(" +
                        "1.0 / " + display.getWidth() + ".0" +
                        ", 1.0 / " + display.getHeight() + ".0" +
                        ", " + display.getWidth() + ".0"
                        + ", " + display.getHeight() + ".0)");*/
    }

    public ConstantField getConstants() {
        return constants;
    }
}
