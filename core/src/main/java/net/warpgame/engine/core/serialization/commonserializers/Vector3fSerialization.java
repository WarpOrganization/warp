package net.warpgame.engine.core.serialization.commonserializers;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.serialization.Deserializer;
import net.warpgame.engine.core.serialization.Serialization;
import net.warpgame.engine.core.serialization.Serializer;
import org.joml.Vector3f;

/**
 * @author Hubertus
 * Created 11.07.2018
 */
//TODO xd
@Service
public class Vector3fSerialization extends Serialization<Vector3f> {
    public Vector3fSerialization() {
        super(Vector3f.class);
    }

    @Override
    public void serialize(Vector3f object, Serializer serializer) {
        serializer
                .write(object.x)
                .write(object.y)
                .write(object.z);
    }

    @Override
    public Vector3f deserialize(Deserializer deserializer) {
        return new Vector3f(
                deserializer.getFloat(),
                deserializer.getFloat(),
                deserializer.getFloat()
        );
    }

    @Override
    public int getType() {
        return 6;
    }
}
