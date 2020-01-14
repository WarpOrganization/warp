package net.warpgame.engine.net;

import net.warpgame.engine.core.component.*;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.event.Event;

import java.util.Collection;

/**
 * @author Hubertus
 * Created 10.08.2018
 */
@Service
@Profile("net")
public class NetComponentRegistry extends ComponentRegistry {

    public static final int PRIVATE_ID_POOL_BEGINNING = 0;
    public static final int PUBLIC_ID_POOL_BEGINNING = PRIVATE_ID_POOL_BEGINNING + IdPool.ID_POOL_SIZE * 20;

    private IdPool publicIdPool;
    private PublicIdPoolProvider publicIdPoolProvider;

    public NetComponentRegistry(PublicIdPoolProvider publicIdPoolProvider) {
        this.publicIdPoolProvider = publicIdPoolProvider;
    }

    public synchronized SceneComponent createPublicComponent(Component parent) {
        if (publicIdPool == null) publicIdPool = publicIdPoolProvider.requestIdPool();
        SceneComponent component = new SceneComponent(parent, publicIdPool.getNextId());
        if (publicIdPool.getPoolState() == IdPool.IdPoolState.FREEING) {
            publicIdPool = publicIdPoolProvider.requestIdPool();
        }
        SimpleListener.createListener(component,
                Event.getTypeId(ComponentDeathEvent.class),
                (e) -> unregisterPublicComponent(component.getId()));

        return component;
    }

    private void unregisterPublicComponent(int id) {
        IdPool pool = publicIdPoolProvider.getPoolByComponentId(id);
        pool.freeId(id);
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
