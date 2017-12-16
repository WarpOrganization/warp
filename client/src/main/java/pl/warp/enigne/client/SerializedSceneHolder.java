package pl.warp.enigne.client;

import io.netty.buffer.ByteBuf;
import pl.warp.engine.core.context.service.Service;

/**
 * @author Hubertus
 * Created 14.12.2017
 */
@Service
public class SerializedSceneHolder {

    private long latestTimestamp;
    private boolean used = true;
    private ByteBuf serializedScene;


    public void offerScene(long timestamp, ByteBuf scene){
        if(timestamp> latestTimestamp){
            latestTimestamp = timestamp;
            serializedScene = scene;
            used = false;
        }
    }

    public boolean isSceneAvailable(){
        return !used;
    }

    public ByteBuf getScene(){
        used = true;
        return serializedScene;
    }

}
