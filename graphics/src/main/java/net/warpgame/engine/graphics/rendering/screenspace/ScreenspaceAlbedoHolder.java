package net.warpgame.engine.graphics.rendering.screenspace;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.texture.Texture2D;

/**
 * @author Jaca777
 * Created 2018-01-13 at 22
 */
@Service
public class ScreenspaceAlbedoHolder {
    private Texture2D albedoTex;

    public Texture2D getAlbedoTex() {
        return albedoTex;
    }

    public void setAlbedoTex(Texture2D albedoTex) {
        this.albedoTex = albedoTex;
    }
}
