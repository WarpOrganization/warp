package net.warpgame.engine.graphics.rendering.ui;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.texture.Texture2D;
import org.joml.Matrix3f;

@Service
@Profile("graphics")
public class UiTest {
    public Texture2D texture2D;
    public Matrix3f matrix3f;

    public UiTest() {
    }
}
