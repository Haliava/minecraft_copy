package com.mygdx.game.control;


import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class Physics {
    public static btDynamicsWorld world;
    static MotionState motionState = new MotionState();
    private btCollisionConfiguration collisionConfiguration;
    private btDispatcher btDispatcher;
    private btBroadphaseInterface broadphaseInterface;
    private btConstraintSolver constraintSolver;

    public Physics() {
        Bullet.init();

        collisionConfiguration = new btDefaultCollisionConfiguration();
        btDispatcher = new btCollisionDispatcher(collisionConfiguration);
        broadphaseInterface = new btDbvtBroadphase();
        constraintSolver = new btSequentialImpulseConstraintSolver();

        world = new btDiscreteDynamicsWorld(btDispatcher, broadphaseInterface, constraintSolver, collisionConfiguration);
    }

    public static btRigidBody AddRigidBody(Array<Node> nodes, Matrix4 transform) {
        motionState.transform = transform;

        btCollisionShape shape;
        shape = Bullet.obtainStaticNodeShape(nodes);

        btRigidBody body = new btRigidBody(0, motionState, shape);
        world.addRigidBody(body);
        return body;
    }
}

class MotionState extends btMotionState {
    Matrix4 transform;

    @Override
    public void setWorldTransform(Matrix4 worldTrans) {
        transform.set(worldTrans);
    }

    @Override
    public void getWorldTransform(Matrix4 worldTrans) {
        worldTrans.set(transform);
    }
}
