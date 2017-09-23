package pl.warp.engine.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import org.apache.log4j.Logger;
import pl.warp.engine.core.context.annotation.Service;
import pl.warp.engine.core.execution.task.EngineTask;

/**
 * @author Hubertus
 * Created 7/4/16
 */

@Service
public class PhysicsTask extends EngineTask {

    private static Logger logger = Logger.getLogger(PhysicsTask.class);

    private PhysicsWorld mainWorld;

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
        mainWorld.getDynamicsWorld().stepSimulation(delta / 1000f, 4, 1 / 60f);
    }


    public void createPhysicsWorld() {
        btCollisionConfiguration collisionConfig = new btDefaultCollisionConfiguration();
        btDispatcher dispatcher = new btCollisionDispatcher(collisionConfig);
        btBroadphaseInterface broadphase = new btDbvtBroadphase();
        btConstraintSolver constraintSolver = new btSequentialImpulseConstraintSolver();

        btDynamicsWorld dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
        dynamicsWorld.setGravity(new Vector3(0, 0, 0));
        mainWorld = new PhysicsWorld(dynamicsWorld);
    }

    public PhysicsWorld getMainWorld() {
        return mainWorld;
    }
}
