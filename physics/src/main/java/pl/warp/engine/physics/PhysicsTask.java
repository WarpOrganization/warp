package pl.warp.engine.physics;

import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import org.apache.log4j.Logger;
import pl.warp.engine.core.execution.task.EngineTask;

/**
 * @author Hubertus
 *         Created 7/4/16
 */

public class PhysicsTask extends EngineTask {

    private static Logger logger = Logger.getLogger(PhysicsTask.class);

    @Override
    protected void onInit() {
        logger.info("initializing physics");
        new SharedLibraryLoader().load("gdx");
        Bullet.init();
    }

    @Override
    protected void onClose() {
    }


    @Override
    public void update(int delta) {
    }
}
