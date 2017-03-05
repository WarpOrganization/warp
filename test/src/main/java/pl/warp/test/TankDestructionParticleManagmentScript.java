package pl.warp.test;

import pl.warp.engine.core.SyncEngineThread;
import pl.warp.engine.core.scene.NameProperty;
import pl.warp.engine.graphics.particles.ParticleEmitterProperty;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.script.GameScript;
import sun.rmi.runtime.Log;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Created by Marcin on 05.03.2017.
 * If you want tank to play destruction particle scheme, make him load this script
 */
public class TankDestructionParticleManagmentScript extends GameScript<GameComponent> {

    private GameComponent tankMain;

    private ParticleEmitterProperty [] emitters;
    private int stage;
    private final int MAX_STAGE;

    private int time;
    private int [] timers;

    public TankDestructionParticleManagmentScript(GameComponent owner) {
        super(owner);
        tankMain = owner;
        time = 0;
        stage = 0;
        MAX_STAGE = 3;
        emitters = new ParticleEmitterProperty[3];
        timers = new int[]{4000, 6000, Integer.MAX_VALUE};
    }

    @Override
    protected void init() {
        int n = tankMain.getChildrenNumber();
        for(int i = 0; i<n; i++){
            GameComponent temp = tankMain.getChild(i);
            if(temp.hasProperty(NameProperty.NAME_PROPERTY_NAME)){
                switch (((NameProperty) temp.getProperty(NameProperty.NAME_PROPERTY_NAME)).getComponentName()) {
                    case "particle 1":
                        emitters[0] = temp.getProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME);
                        break;
                    case "particle 2":
                        emitters[1] = temp.getProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME);
                        break;
                    case "particle 3":
                        emitters[2] = temp.getProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME);
                }
            }
        }
        emitters[0].getSystem().setEmit(true);
    }

    @Override
    protected void update(int delta) {
        time += delta;
        if(time > timers[stage]){
            emitters[stage++].getSystem().setEmit(false);
            if(stage > MAX_STAGE) {
                this.stop();
            }
            emitters[stage].getSystem().setEmit(true);
            time = 0;
        }
    }
}
