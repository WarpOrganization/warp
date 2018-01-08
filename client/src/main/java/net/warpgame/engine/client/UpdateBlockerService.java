package net.warpgame.engine.client;

import net.warpgame.engine.core.context.service.Service;

import java.util.ArrayList;

/**
 * @author Hubertus
 * Created 08.01.2018
 */
@Service
public class UpdateBlockerService {
    private ArrayList<Boolean> flags = new ArrayList<>();

    public void block(int componentId) {
        if (flags.size() > componentId) flags.set(componentId, true);
        else {
            flags.ensureCapacity(componentId + 20);
            flags.set(componentId, true);
        }
    }

    public void unblock(int componentId) {
        if (flags.size() > componentId) flags.set(componentId, false);
    }

    public boolean isBlocked(int componentId) {
        if (flags.size() > componentId) return flags.get(componentId);
        else return false;
    }

}
