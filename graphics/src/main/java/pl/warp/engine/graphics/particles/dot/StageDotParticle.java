package pl.warp.engine.graphics.particles.dot;

import org.joml.Vector3f;

/**
 * @author Jaca777
 *         Created 2017-02-04 at 13
 */
public class StageDotParticle extends DotParticle {
    private ParticleStage[] stages;
    private float progressPerStage;

    public StageDotParticle(Vector3f position, Vector3f velocity, float scale, float rotation, int totalTimeToLive, int timeToLive, ParticleStage[] stages) {
        super(position, velocity, scale, rotation, totalTimeToLive, timeToLive);
        this.stages = stages;
        this.progressPerStage = 1.0f / (stages.length - 1);
    }

    @Override
    public ParticleStage getStage() {
        float progress = 1.0f - (getTimeToLive() / (float) getTotalTimeToLive());
        int stage = (int) ((stages.length - 1) * progress);
        if(stage == stages.length - 1) return stages[stage];
        return interpolateStage(progress, stage);
    }

    private ParticleStage tempStage = new ParticleStage();

    protected ParticleStage interpolateStage(float progress, int stage) {
        float stageProgress = (progress - progressPerStage * stage) / progressPerStage;
        ParticleStage currentStage = stages[stage];
        ParticleStage nextStage = stages[stage + 1];
        tempStage.setColor(interpolateValues(currentStage.getColor().x, nextStage.getColor().x, stageProgress),
                interpolateValues(currentStage.getColor().y, nextStage.getColor().y, stageProgress),
                interpolateValues(currentStage.getColor().z, nextStage.getColor().z, stageProgress),
                interpolateValues(currentStage.getColor().w, nextStage.getColor().w, stageProgress));
        tempStage.setGradient(interpolateValues(currentStage.getGradient(), nextStage.getGradient(), stageProgress));
        return tempStage;
    }


    private float interpolateValues(float a, float b, float i) {
        return a * (1.0f - i) + b * i;
    }

}
