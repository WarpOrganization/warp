package net.warpgame.engine.graphicstest;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.IdOf;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.OwnerProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author MarconZet
 * Created 26.11.2019
 */
public class DragonScript extends Script {
    private Logger logger = LoggerFactory.getLogger(DragonScript.class);

    @OwnerProperty(@IdOf(TransformProperty.class))
    TransformProperty transformProperty;

    public DragonScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onUpdate(int delta) {
        transformProperty.rotateLocalY((float)Math.PI*delta/10000);
    }
}
