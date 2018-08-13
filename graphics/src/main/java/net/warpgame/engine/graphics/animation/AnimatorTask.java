package net.warpgame.engine.graphics.animation;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jaca777
 * Created 2018-06-24 at 17
 */
@Service
@Profile("graphics")
@RegisterTask(thread = "graphics")
public class AnimatorTask extends EngineTask {

    private Set<Animator> animators = new HashSet<>();

    public void addAnimator(Animator animator) {
        animators.add(animator);
    }


    @Override
    protected void onInit() {

    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(int delta) {
        for(Animator animator : animators) {
            animator.update(delta);
        }
    }
}
