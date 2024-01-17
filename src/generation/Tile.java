package generation;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage Image;
    public boolean CollisionEnabled = false;

    public Tile(BufferedImage image, boolean collisionEnabled) {
        Image = image;
        CollisionEnabled = collisionEnabled;
    }
}
