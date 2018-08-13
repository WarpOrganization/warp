package net.warpgame.engine.core.serialization.commonserializers;

import net.warpgame.engine.core.serialization.Serialization;
import net.warpgame.engine.core.serialization.SerializationBuffer;
import net.warpgame.engine.core.serialization.Serializers;
import org.joml.Vector3f;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Hubertus
 * Created 11.07.2018
 */
public class Vector3FSerializationeloTests {

    @Test
    public void deserializedShouldEqualOriginal() {
        Vector3f originalVector = new Vector3f(2, 3, 4);
        Vector3fSerialization serialization = new Vector3fSerialization();
        Serializers serializers = new Serializers(new Serialization[]{serialization});
        SerializationBuffer buffer = new SerializationBuffer(1000);
        serializers.serialize(buffer, originalVector);

        Vector3f deserializedVector =  (Vector3f) serializers.deserialize(buffer);

        Assert.assertEquals(originalVector, deserializedVector);
    }
}
