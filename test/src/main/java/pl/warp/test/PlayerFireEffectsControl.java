package pl.warp.test;

import pl.warp.engine.core.scene.NameProperty;
import pl.warp.engine.graphics.particles.ParticleEmitterProperty;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.script.GameScriptWithInput;

import java.awt.event.MouseEvent;

/**
 * Created by Marcin on 05.03.2017.
 */
public class PlayerFireEffectsControl extends GameScriptWithInput<GameComponent> {

    private GameComponent mainBarrel;
    private GameComponent secondBarrel;

    private ParticleEmitterProperty firstEffects[];
    private ParticleEmitterProperty secondEffects[];

    public PlayerFireEffectsControl(GameComponent owner) {
        super(owner);
        this.mainBarrel = owner;
        firstEffects = new ParticleEmitterProperty[2];
        secondEffects = new ParticleEmitterProperty[2];
    }

    @Override
    protected void init() {
        int n = mainBarrel.getChildrenNumber();
        for(int i = 0; i<n; i++){
            GameComponent temp = mainBarrel.getChild(i);
            if(temp.hasProperty(NameProperty.NAME_PROPERTY_NAME)){
                switch (((NameProperty) temp.getProperty(NameProperty.NAME_PROPERTY_NAME)).getComponentName()) {
                    case "effect 1":
                        firstEffects[0] = temp.getProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME);
                        break;
                    case "effect 2":
                        secondEffects[0] = temp.getProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME);
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
                        firstEffects[1] = temp.getProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME);
                        break;
                    case "effect 2":
                        secondEffects[1] = temp.getProperty(ParticleEmitterProperty.PARTICLE_EMITTER_PROPERTY_NAME);
                        break;
                }
            }
        }
    }

    @Override
    protected void update(int delta) {
        if(super.getInputHandler().wasMouseButtonPressed(MouseEvent.BUTTON1)){

        }
    }
}
