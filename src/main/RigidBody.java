package main;

import java.util.ArrayList;

public class RigidBody {
    public Vector2 Position;
    public Vector2 Velocity = new Vector2(0, 0);
    public Vector2 Force = new Vector2(0, 0);
    public static double Gravity = 9.8f * 0;
    public static ArrayList<RigidBody> AllRigidBodies = new ArrayList<>();
    public final double Mass;
    public final double Friction;
    public BoxCollider Collider;

    public RigidBody(double mass, double friction, BoxCollider collider) {
        this.Mass = mass;
        this.Friction = friction;
        Collider = collider;
        Collider.SetDynamic(this);
        Position = Collider.Position;

        AllRigidBodies.add(this);
    }

    public void update(double deltaTime) {
        Force = Force.add(new Vector2(0, Gravity * Mass));
        Velocity = Velocity.add(Force.divide(Mass));
        Velocity = Velocity.multiply(Friction);

        Position = Position.add(Velocity.multiply(deltaTime));

        Force = new Vector2(0, 0);
    }
}
