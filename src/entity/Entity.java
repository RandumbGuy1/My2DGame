package entity;
import main.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class Entity {
    public static ArrayList<Entity> AllEntities = new ArrayList<>();
    public Vector2 Position = new Vector2(0, 0);

    public Entity() {
        AllEntities.add(this);
    }

    public void update(double deltaTime) {

    }
    public void draw(Graphics2D g2, Vector2 offset) {

    }
}
