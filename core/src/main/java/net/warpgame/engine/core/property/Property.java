package net.warpgame.engine.core.property;

import net.warpgame.engine.core.component.Component;

/**
 * @author Jaca777
 *         Created 2016-06-25 at 18
 */
public abstract class Property {

    private Component owner;
    private boolean enabled = false;
    private boolean triggerStateEvents = false;

    public void init(){ }
    public void setOwner(Component owner) {
        if (this.owner != null) throw new IllegalStateException("Property can't have two owners.");
        else {
            this.owner = owner;
            enable();
        }
    }

    public Component getOwner() {
        return owner;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setTriggerStateEvents(boolean triggerStateEvents) {
        this.triggerStateEvents = triggerStateEvents;
    }

    public void enable() {
        this.enabled = true;
        if (triggerStateEvents) owner.triggerEvent(new PropertyEnabledEvent(this));
    }

    public void disable() {
        this.enabled = false;
        if (triggerStateEvents) owner.triggerEvent(new PropertyDisabledEvent(this));
    }

    /**
     * Returns generated ID of instance's event type.
     * Method is generated at runtime.
     */
    public int getType() {
        String msg = String.format("Engine runtime was unable to generate the getTypeId method for %s class", getClass().getName());
        throw new UnsupportedOperationException(msg);
    }

    /**
     * Returns generated ID of event type.
     * Method is generated and inlined at runtime.
     */
    public static int getTypeId(Class<? extends Property> propertyClass){
        String msg = String.format("Engine runtime was unable to inline type ID for %s class", propertyClass.getName());
        throw new UnsupportedOperationException(msg);
    }
}
