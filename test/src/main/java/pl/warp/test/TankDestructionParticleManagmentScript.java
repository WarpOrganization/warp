package pl.warp.test;

import pl.warp.engine.core.scene.NameProperty;
import pl.warp.engine.graphics.particles.ParticleEmitterProperty;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.script.GameScript;

/**
 * Created by Marcin on 05.03.2017.
 * If you want tank to play destruction particle scheme, make him load this script
 */
public class TankDestructionParticleManagmentScript extends GameScript<GameComponent> {

    private GameComponent tankMain;

    private ParticleEmitterProperty [] emitters;
    private int stage;

    private final int FINAL_STAGE;
    private static final int SAFE_GUARD = Integer.MAX_VALUE;

    private int time;
    private int [] startTime;
    private int [] endTime;
    private int nextToActivate;
    private int nextToKill;

    public TankDestructionParticleManagmentScript(GameComponent owner) {
        super(owner);
        FINAL_STAGE = 3;
    }

    @Override
    protected void init() {
        tankMain = this.getOwner();
        time = 0;
        emitters = new ParticleEmitterProperty[3];
        startTime = new int[]{0, 4000, 10000, SAFE_GUARD};
        endTime = new int[]{4000, 10000, Integer.MAX_VALUE, SAFE_GUARD};
        nextToActivate = 0;
        nextToKill = 0;
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
    }

    @Override
    protected void update(int delta) {
        time += delta;
        if(startTime[nextToActivate] < time){
            emitters[nextToActivate++].getSystem().setEmit(true);
        }
        if(endTime[nextToKill] > time){
            emitters[nextToKill++].getSystem().setEmit(false);
        }

    }
}
