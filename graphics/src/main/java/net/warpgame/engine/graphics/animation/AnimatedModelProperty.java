package net.warpgame.engine.graphics.animation;

import net.warpgame.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2018-06-24 at 17
 */
public class AnimatedModelProperty extends Property {

    private AnimatedModel animatedModel;

    public AnimatedModelProperty(AnimatedModel animatedModel) {
        this.animatedModel = animatedModel;
    }

    public AnimatedModel getAnimatedModel() {
        return animatedModel;
    }
}
