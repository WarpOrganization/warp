package net.warpgame.engine.core.serialization.commonserializers;

import org.joml.Vector3f;
import org.junit.Test;

/**
 * @author Hubertus
 * Created 11.07.2018
 */
public class Vector3FSerializationeloTests {

    @Test
    public void deserializedShouldEqualOriginal() {
        Vector3f originalVector = new Vector3f(2, 3, 4);
//        Vector3fSerializationelo serialization = new Vector3fSerializationelo();
//        Serializers serializers = new Serializers(new Serialization[]{serialization});
//        SerializationBuffer buffer = new SerializationBuffer(1000);
//        serializers.serialize(buffer, originalVector);
//
//        Vector3f deserializedVector =  (Vector3f) serializers.deserialize(buffer);
//
//        Assert.assertEquals(originalVector, deserializedVector);
    }
}
