package net.warpgame.engine.client;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.SerializationBuffer;

/**
 * @author Hubertus
 * Created 14.12.2017
 */
@Service
@Profile("client")
public class SerializedSceneHolder {

    private long latestTimestamp;
    private boolean used = true;
    private SerializationBuffer serializedScene;


    public void offerScene(long timestamp, ByteBuf scene) {
        if (timestamp > latestTimestamp) {
            latestTimestamp = timestamp;
            byte[] bytes = new byte[scene.readableBytes()];
            scene.readBytes(bytes);
            serializedScene = new SerializationBuffer(bytes);
//            scene.retain();
            used = false;
        }
    }

    public boolean isSceneAvailable() {
        return !used;
    }

    public SerializationBuffer getScene() {
        used = true;
        return serializedScene;
    }

}
