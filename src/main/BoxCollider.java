package main;

import entity.Entity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BoxCollider extends Entity {
    public Vector2 Scale;
    public static ArrayList<BoxCollider> AllColliders = new ArrayList<>();

    public BoxCollider(Vector2 position, Vector2 scale) {
        Position = position;
        Scale = scale;

        AllColliders.add(this);
    }

    public Vector2 collisionResolution(BoxCollider other) {
        boolean horizontalIntersection = Position.X < other.Position.X + other.Scale.X && Position.X + Scale.X > other.Position.X;
        boolean verticalIntersection = Position.Y < other.Position.Y + other.Scale.Y && Position.Y + Scale.Y > other.Position.Y;

        if (!horizontalIntersection || !verticalIntersection) {
            System.out.println("Not Intersecting");
            return new Vector2(0, 0);
        }

        System.out.println("Intersecting");
        return new Vector2(0, 0);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawRect((int) Position.X, (int) Position.Y, (int) Scale.X, (int) Scale.Y);
    }
}
