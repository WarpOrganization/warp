package net.warpgame.servertest.client.scripts;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.ContextService;
import net.warpgame.engine.input.Input;
import net.warpgame.engine.net.NetComponentRegistry;
import net.warpgame.engine.physics.RigidBodyConstructor;
import net.warpgame.servertest.ShotEvent;
import net.warpgame.servertest.client.BulletCreator;

import static java.awt.event.KeyEvent.VK_CONTROL;


/**
 * @author Hubertus
 * Created 14.08.2018
 */
public class GunScript extends Script {

    private static final int GUN_COOLDOWN = 200;

    private int cooldown = 0;
    private RigidBodyConstructor rigidBodyConstructor;
    @ContextService
    BulletCreator bulletCreator;
    @ContextService
    private Input input;

    @ContextService
    private NetComponentRegistry componentRegistry;

    public GunScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onUpdate(int delta) {
        if (cooldown <= 0) {
            if (input.isKeyDown(VK_CONTROL)) shoot();
        } else cooldown -= delta;
    }

    private void shoot() {
        cooldown = GUN_COOLDOWN;
        Component bullet = bulletCreator.create(getOwner());
        getOwner().triggerEvent(new ShotEvent(bullet.getId()));
    }
}
