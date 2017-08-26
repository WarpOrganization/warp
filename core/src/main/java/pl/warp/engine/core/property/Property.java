package pl.warp.engine.core.property;

import pl.warp.engine.core.component.Component;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 18
 */
public abstract class Property<T extends Component> {

    private T owner;
    private String name;
    private boolean enabled = false;
    private boolean triggerStateEvents = false;

    public Property() {
        this.name = getClass().getName();
    }

    public Property(String name) {
        this.name = name;
    }

    public void setOwner(T owner) {
        if (this.owner != null) throw new IllegalStateException("Property can't have two owners.");
        else {
            this.owner = owner;
            enable();
        }
    }

    protected T getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setTriggerStateEvents(boolean triggerStateEvents) {
        this.triggerStateEvents = triggerStateEvents;
    }

    public void enable() {
        this.enabled = true;
        if (triggerStateEvents) owner.triggerEvent(new PropertyEnabledEvent<>(this));
    }

    public void disable() {
        this.enabled = false;
        if (triggerStateEvents) owner.triggerEvent(new PropertyDisabledEvent<>(this));
    }
}
