package pl.warp.test;

import pl.warp.engine.core.property.NameProperty;
import pl.warp.engine.game.script.GameScriptWithInput;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.graphics.particles.ParticleEmitterProperty;
import pl.warp.engine.game.scene.GameComponent;

import java.awt.event.MouseEvent;

/**
 * Created by Marcin on 05.03.2017.
 */
public class PlayerFireEffectsControl extends GameScriptWithInput {

    private GameComponent mainBarrel;
    private GameComponent secondBarrel;

    private ParticleEmitterProperty emitterProperties[][];

    private int [] startTime;
    private int [] endTime;

    private int nextToActivate;
    private int nextToKill;

    private boolean playerFire;
    private int timeFromFire;

    private int activeBarrel;

    public PlayerFireEffectsControl(GameComponent owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        mainBarrel = (GameComponent) this.getOwner();
        emitterProperties = new ParticleEmitterProperty[2][2];
        playerFire = false;
        int n = mainBarrel.getChildrenNumber();
        for(int i = 0; i<n; i++){
            GameComponent temp = mainBarrel.getChild(i);
            if(temp.hasProperty(NameProperty.NAME_PROPERTY_NAME)){
                switch (((NameProperty) temp.getProperty(NameProperty.NAME_PROPERTY_NAME)).getComponentName()) {
                    case "effect 1":
                        emitterProperties[0][0] = temp.getProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME);
                        break;
                    case "effect 2":
                        emitterProperties[0][1] = temp.getProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME);
                        break;
                    case "player tank barrel fake":
                        secondBarrel = temp;
                        break;
                }
            }
        }
        n = secondBarrel.getChildrenNumber();
        for(int i = 0; i<n; i++) {
            GameComponent temp = mainBarrel.getChild(i);
            if (temp.hasProperty(NameProperty.NAME_PROPERTY_NAME)) {
                switch (((NameProperty) temp.getProperty(NameProperty.NAME_PROPERTY_NAME)).getComponentName()) {
                    case "effect 1":
                        emitterProperties[1][0] = temp.getProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME);
                        break;
                    case "effect 2":
                        emitterProperties[1][1] = temp.getProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME);
                        break;
                }
            }
        }
    }

    @Override
    public void onUpdate(int delta) {
        if(super.getInputHandler().wasMouseButtonPressed(MouseEvent.BUTTON1)){
            playerFire = true;
            activeBarrel = secondBarrel.getProperty(RenderableMeshProperty.MESH_PROPERTY_NAME).isEnabled()? 1 : 0;
            timeFromFire = 0;
        }
        if(playerFire){
            timeFromFire += delta;
            if(startTime[nextToActivate] < timeFromFire){
                emitterProperties[activeBarrel][nextToActivate++].getSystem().setEmit(true);
            }
            if(endTime[nextToKill] > timeFromFire){
                emitterProperties[activeBarrel][nextToKill++].getSystem().setEmit(false);
            }
        }
    }
}
