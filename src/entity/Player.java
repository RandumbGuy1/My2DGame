package entity;

import main.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    private final GamePanel gamePanel;
    private final KeyHandler keyHandler;
    private final RigidBody rb;
    private final BoxCollider collider;

    public enum Direction {
        Up,
        Down,
        Left,
        Right
    }

    public Direction FacingDirection = Direction.Up;
    public BufferedImage Up1, Up2, Down1, Down2, Left1, Left2, Right1, Right2;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        try {
            Up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(("/player/boy_up_1.png"))));
            Up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(("/player/boy_up_2.png"))));

            Down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(("/player/boy_down_1.png"))));
            Down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(("/player/boy_down_2.png"))));

            Left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(("/player/boy_left_1.png"))));
            Left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(("/player/boy_left_2.png"))));

            Right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(("/player/boy_right_1.png"))));
            Right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(("/player/boy_right_2.png"))));

        } catch (IOException e) {
            e.printStackTrace();
        }

        collider = new BoxCollider(Position, new Vector2(gamePanel.TileSize, gamePanel.TileSize));
        rb = new RigidBody(1f, 0.95f, collider);
    }

    double spriteCount = 0;
    int spriteIndex = 0;
    @Override
    public void update(double deltaTime) {
        if (keyHandler.WAInput.X > 0) {
            FacingDirection = Direction.Left;
        }
        if (keyHandler.SDInput.X < 0) {
            FacingDirection = Direction.Right;
        }

        Vector2 inputDir = keyHandler.getDirectionalInput();
        inputDir.Y = 0;
        Move(inputDir, deltaTime);

        if (keyHandler.Jumping) {
            rb.Velocity.Y = -300;
        }

        if (inputDir.getSqrLength() == 0) return;

        spriteCount += deltaTime;
        if (spriteCount > 0.1f) {
            spriteIndex++;
            spriteCount = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2, Vector2 offset) {
        BufferedImage image = switch (FacingDirection) {
            case Up -> (spriteIndex % 2 == 0 ? Up1 : Up2);
            case Down -> (spriteIndex % 2 == 0 ? Down1 : Down2);
            case Left -> (spriteIndex % 2 == 0 ? Left1 : Left2);
            case Right -> (spriteIndex % 2 == 0 ? Right1 : Right2);
        };

        g2.drawImage(image, (int) Position.X - (int) offset.X, (int) Position.Y - (int) offset.Y, gamePanel.TileSize, gamePanel.TileSize, null);
    }

    public void Move(Vector2 amount, double deltaTime) {
        double speed = 1000 * 1.5;
        rb.Force.X += amount.X * -speed * deltaTime;
        rb.Force.Y += amount.Y * -speed * deltaTime;

        Position = rb.Position;
    }
}
