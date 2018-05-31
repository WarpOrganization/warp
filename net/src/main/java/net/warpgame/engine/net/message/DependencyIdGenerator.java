package net.warpgame.engine.net.message;

/**
 * @author Hubertus
 * Created 31.05.2018
 */
public class DependencyIdGenerator {
    private int eventDependencyIdCounter = 0;

    int getNextDependencyId(){
        eventDependencyIdCounter++;
        return eventDependencyIdCounter;
    }
}
