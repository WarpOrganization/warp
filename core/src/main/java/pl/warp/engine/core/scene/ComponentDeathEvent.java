package pl.warp.engine.core.scene;

/**
 * @author Jaca777
 *         Created 2016-07-14 at 17
 */
public class ComponentDeathEvent extends Event {
    public static final String COMPONENT_DEATH_EVENT_NAME = "componentDeath";

    private Component victim;

    public ComponentDeathEvent(Component victim) {
        super(COMPONENT_DEATH_EVENT_NAME);
        this.victim = victim;
    }

    public Component getVictim() {
        return victim;
    }
}
