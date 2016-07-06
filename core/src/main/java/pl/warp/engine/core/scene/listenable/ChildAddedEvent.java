package pl.warp.engine.core.scene.listenable;

import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Event;

/**
 * @author Jaca777
 *         Created 2016-07-07 at 00
 */
public class ChildAddedEvent extends Event {
    public static final String CHILD_ADDED_EVENT_NAME = "childAddedEvent";

    private Component addedChild;

    public ChildAddedEvent(Component addedChild) {
        super(CHILD_ADDED_EVENT_NAME);
        this.addedChild = addedChild;
    }

    public Component getAddedChild() {
        return addedChild;
    }
}
