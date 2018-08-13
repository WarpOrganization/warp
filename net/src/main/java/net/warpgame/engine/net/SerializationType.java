package net.warpgame.engine.net;

/**
 * @author Hubertus
 * Created 24.06.2018
 */
public enum SerializationType {
    POSITION,
    POSITION_AND_VELOCITY;

    public class Size {
        /**
         * id (1), serializationType (1), translation (3), rotation (4)
         */
        public static final int POSITION_SIZE = 4 * (1 + 1 + 3 + 4);
        /**
         * id (1), serializationType (1), translation (3), rotation (4), velocity (3), angular velocity (3)
         */
        public static final int POSITION_AND_VELOCITY_SIZE =  4 * (1 + 1 + 3 + 3 + 4 + 3);
    }
}
