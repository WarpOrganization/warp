package net.warpgame.engine.net;

/**
 * @author Hubertus
 * Created 10.08.2018
 */
public abstract class PublicIdPoolProvider {
    public abstract IdPool requestIdPool();
    public abstract void freeIdPool(IdPool idPool);
}
