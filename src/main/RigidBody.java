package main;

public class RigidBody {
    public Vector2 Position = new Vector2(0, 0);
    public Vector2 Velocity = new Vector2(0, 0);
    public Vector2 Force = new Vector2(0, 0);
    public static double Gravity = 0f;
    private final double mass;
    private final double friction;
    BoxCollider collider;

    public RigidBody(double mass, double friction, BoxCollider collider) {
        this.mass = mass;
        this.friction = friction;
        this.collider = collider;
    }

    public void update(double deltaTime) {
        Force = Force.add(new Vector2(0, Gravity * mass));
        Velocity = Velocity.add(Force.divide(mass));
        Velocity = Velocity.multiply(friction);

        Position = Position.add(Velocity.multiply(deltaTime));

        Force = new Vector2(0, 0);

        for (BoxCollider other : BoxCollider.AllColliders) {
            Vector2 resolve = other.collisionResolution(other);
        }
    }
}
