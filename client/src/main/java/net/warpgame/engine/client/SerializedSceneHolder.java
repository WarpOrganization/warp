package net.warpgame.engine.client;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.context.service.Service;

/**
 * @author Hubertus
 * Created 14.12.2017
 */
@Service
public class SerializedSceneHolder {

    private long latestTimestamp;
    private boolean used = true;
    private ByteBuf serializedScene;


    public void offerScene(long timestamp, ByteBuf scene) {
        if (timestamp > latestTimestamp) {
            latestTimestamp = timestamp;
            serializedScene = scene;
            used = false;
            scene.retain();
        }
    }

    public boolean isSceneAvailable() {
        return !used;
    }

    public ByteBuf getScene() {
        used = true;
        return serializedScene;
    }

}
