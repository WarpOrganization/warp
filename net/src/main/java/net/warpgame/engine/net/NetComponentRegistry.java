package net.warpgame.engine.net;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.component.IdExistsException;
import net.warpgame.engine.core.component.SceneComponent;

import java.util.Collection;

/**
 * @author Hubertus
 * Created 10.08.2018
 */
public class NetComponentRegistry extends ComponentRegistry {

    public static final int PRIVATE_ID_POOL_BEGINNING = Integer.MIN_VALUE;
    public static final int PUBLIC_ID_POOL_BEGINNING = Integer.MIN_VALUE + IdPool.ID_POOL_SIZE * 20;

    private IdPool publicIdPool;
    private PublicIdPoolProvider publicIdPoolProvider;

    public NetComponentRegistry(PublicIdPoolProvider publicIdPoolProvider) {
        this.publicIdPoolProvider = publicIdPoolProvider;
        publicIdPool = publicIdPoolProvider.requestIdPool();
    }

    public synchronized SceneComponent createPublicComponent(Component parent) {
        SceneComponent component = new SceneComponent(parent, publicIdPool.getNextId());
        //TODO check pool availability
        return component;
    }

    @Override
    public synchronized void addComponent(Component component, int id) throws IdExistsException {
        super.addComponent(component, id);
    }

    @Override
    public synchronized int addComponent(Component component) {
        return super.addComponent(component);
    }

    @Override
    public synchronized Component getComponent(int id) {
        return super.getComponent(id);
    }

    @Override
    public synchronized Component getRootComponent() {
        return super.getRootComponent();
    }

    @Override
    public synchronized void removeComponent(int id) {
        super.removeComponent(id);
    }

    @Override
    public synchronized void getComponents(Collection<Component> target) {
        super.getComponents(target);
    }
}
