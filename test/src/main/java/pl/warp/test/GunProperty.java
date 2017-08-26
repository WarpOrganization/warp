package pl.warp.test;

import pl.warp.engine.audio.AudioManager;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.Property;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.texture.Texture2DArray;

/**
 * @author Hubertus
 *         Created 25.01.17
 */
public class GunProperty extends Property {

    public static final String GUN_PROPERTY_NAME = "gunProperty";

    private boolean isTriggered = false;
    private int cooldownTime;
    private Component root;
    private Mesh bulletMesh;
    private Texture2DArray explosionSpritesheet;
    private Texture2D bulletTexture;
    private AudioManager audioManager;

    public GunProperty(int cooldownTime, Component root, Mesh bulletMesh, Texture2DArray explosionSpritesheet, Texture2D bulletTexture, AudioManager audioManager) {
        super(GUN_PROPERTY_NAME);
        this.cooldownTime = cooldownTime;
        this.root = root;
        this.bulletMesh = bulletMesh;
        this.explosionSpritesheet = explosionSpritesheet;
        this.bulletTexture = bulletTexture;
        this.audioManager = audioManager;
    }

    public int getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(int cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public boolean isTriggered() {
        return isTriggered;
    }

    public void setTriggered(boolean triggered) {
        isTriggered = triggered;
    }

    public void setRoot(Component root) {
        this.root = root;
    }

    public Component getRoot() {
        return root;
    }

    public void setBulletMesh(Mesh bulletMesh) {
        this.bulletMesh = bulletMesh;
    }

    public Mesh getBulletMesh() {
        return bulletMesh;
    }

    public Texture2DArray getExplosionSpritesheet() {
        return explosionSpritesheet;
    }

    public void setExplosionSpritesheet(Texture2DArray explosionSpritesheet) {
        this.explosionSpritesheet = explosionSpritesheet;
    }

    public Texture2D getBulletTexture() {
        return bulletTexture;
    }

    public void setBulletTexture(Texture2D bulletTexture) {
        this.bulletTexture = bulletTexture;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public void setAudioManager(AudioManager audioManager) {
        this.audioManager = audioManager;
    }
}
