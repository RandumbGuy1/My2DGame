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
        if (other == this) return new Vector2(0, 0);

        boolean verticalIntersection = Position.Y < other.Position.Y + other.Scale.Y && Position.Y + Scale.Y > other.Position.Y;
        boolean horizontalIntersection = Position.X + Scale.X > other.Position.X && Position.X < other.Position.X + other.Scale.X;

        if (!horizontalIntersection || !verticalIntersection) return new Vector2(0, 0);

        double upIntersection = -Position.Y + (other.Position.Y + other.Scale.Y);
        double downIntersection = other.Position.Y - (Position.Y + Scale.Y);

        double rightIntersection = other.Position.X - (Position.X + Scale.X);
        double leftIntersection = -Position.X + (other.Position.X + other.Scale.X);

        double[] intersections = { upIntersection, downIntersection, rightIntersection, leftIntersection };
        int smallestIntersection = indexOfSmallestAbs(intersections);

        return switch (smallestIntersection) {
            case 0 -> new Vector2(0, upIntersection);
            case 1 -> new Vector2(0, downIntersection);
            case 2 -> new Vector2(rightIntersection, 0);
            case 3 -> new Vector2(leftIntersection, 0);
            default -> new Vector2(0, 0);
        };
    }

    public static int indexOfSmallestAbs(double[] array) {
        if (array.length == 0)
            return -1;

        int index = 0;
        double min = Math.abs(array[index]);

        for (int i = 1; i < array.length; i++){
            if (Math.abs(array[i]) <= min){
                min = Math.abs(array[i]);
                index = i;
            }
        }
        return index;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawRect((int) Position.X, (int) Position.Y, (int) Scale.X, (int) Scale.Y);
    }
}
