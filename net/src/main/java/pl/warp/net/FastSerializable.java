package pl.warp.net;

/**
 * @author Hubertus
 * Created 18.12.2017
 */
public interface FastSerializable {
    byte[] serialize();

    void deserialize(byte[] serializedData);
}
