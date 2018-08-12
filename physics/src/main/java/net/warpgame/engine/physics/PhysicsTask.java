package net.warpgame.engine.physics;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.physics.raytester.RayTestSolver;
import org.apache.log4j.Logger;

/**
 * @author Hubertus
 * Created 7/4/16
 */

@Service
@RegisterTask(thread = "physics")
@Profile("fullPhysics")
public class PhysicsTask extends EngineTask {

    private static Logger logger = Logger.getLogger(PhysicsTask.class);

    private PhysicsWorld mainWorld;
    private RigidBodyRegistry rigidBodyRegistry;
    private RayTestSolver rayTestSolver;
    private ConstraintRegistry constraintRegistry;
    private ContactHandler contactHandler;
    private ComponentRegistry componentRegistry;

    public PhysicsTask(ComponentRegistry componentRegistry) {
        this.componentRegistry = componentRegistry;
        rigidBodyRegistry = new RigidBodyRegistry();
        rayTestSolver = new RayTestSolver(componentRegistry);
        constraintRegistry = new ConstraintRegistry();
    }

    @Override
    protected void onInit() {
        logger.info("initializing physics");
        new SharedLibraryLoader().load("gdx");
        Bullet.init();
        contactHandler = new ContactHandler(componentRegistry);
        createPhysicsWorld();
        rayTestSolver.setWorld(mainWorld);
    }

    @Override
    protected void onClose() {
        contactHandler.dispose();
    }

    @Override
    public void update(int delta) {
        rigidBodyRegistry.processBodies(mainWorld.getDynamicsWorld());
        constraintRegistry.update(mainWorld.getDynamicsWorld());
        mainWorld.getDynamicsWorld().stepSimulation(delta / 1000f, 4, 1 / 60f);
        rayTestSolver.update();
    }

    private void createPhysicsWorld() {
        btCollisionConfiguration collisionConfig = new btDefaultCollisionConfiguration();
        btDispatcher dispatcher = new btCollisionDispatcher(collisionConfig);
        btBroadphaseInterface broadPhase = new btDbvtBroadphase();
        btConstraintSolver constraintSolver = new btSequentialImpulseConstraintSolver();
        btDynamicsWorld dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadPhase, constraintSolver, collisionConfig);
        dynamicsWorld.setGravity(new Vector3(0, 0, 0));
        mainWorld = new PhysicsWorld(dynamicsWorld);
    }

    PhysicsWorld getMainWorld() {
        return mainWorld;
    }

    RigidBodyRegistry getRigidBodyRegistry() {
        return rigidBodyRegistry;
    }

    public RayTestSolver getRayTestSolver() {
        return rayTestSolver;
    }

    public ConstraintRegistry getConstraintRegistry() {
        return constraintRegistry;
    }
}
