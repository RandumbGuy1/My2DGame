package main;
import entity.Entity;
import entity.Player;
import generation.TileManager;
import items.Item;
import items.PasswordChest;
import items.PasswordTent;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;
    public final int TileSize = originalTileSize * scale;

    public final int MaxScreenCol = 40;
    public final int MaxScreenRow = 22;
    public final int ScreenWidth = TileSize * MaxScreenCol;
    public final int ScreenHeight = TileSize * MaxScreenRow;

    public final int MaxWorldCol = 96;
    public final int MaxWorldRow = 74;

    int fps = 60;
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler(this);
    TileManager tileManager = new TileManager(this);
    Player player = new Player(this, keyHandler);
    Audio audio = new Audio();
    UI ui = new UI(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.setFocusable(true);

        //Spawn boxes from list
        for (BoxCollider bc : boxesCollider) {
            new RigidBody(1f, 0.98f, bc);
        }

        new RigidBody(1f, 0.98f, chest.Collider);

        playMusic("Tentakeel Outpost.wav");
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    BoxCollider[] boxesCollider = {
            new BoxCollider(new Vector2(TileSize * MaxScreenCol * 0.4, TileSize * MaxScreenRow * 0.4), new Vector2(TileSize, TileSize), tileManager.Tiles[3].Image, false),
    };

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta > 1) {
                update(1f / fps);
                repaint();
                delta--;
            }
        }
    }

    public void update(double deltaTime) {
        player.update(deltaTime);

        for (RigidBody rb : RigidBody.AllRigidBodies) {
            rb.update(deltaTime);

            for (BoxCollider other : BoxCollider.AllColliders) {
                Vector2 resolveDir = rb.Collider.collisionResolution(other);
                rb.Velocity = rb.Velocity.add(resolveDir);
                rb.Position = rb.Position.add(resolveDir);
                rb.Collider.Position = rb.Position;

                if (other.Rb != null) other.Rb.Velocity = other.Rb.Velocity.add(resolveDir.multiply(-1));
            }
        }

        keyHandler.ResetTapKeys();
    }

    public Vector2 ScreenOffset = new Vector2(0, 0);
    public PasswordChest chest = new PasswordChest(
            new BoxCollider(new Vector2(TileSize * 32, TileSize * 32), new Vector2(TileSize, TileSize)), this);
    public PasswordTent tent = new PasswordTent(
            new BoxCollider(new Vector2(TileSize * 36, TileSize * 32), new Vector2(TileSize * 2, TileSize * 2)), this);

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        ScreenOffset.Y = (int) player.Position.Y - TileSize * ((double) MaxScreenRow / 2);
        ScreenOffset.X = (int) player.Position.X - TileSize * ((double) MaxScreenCol / 2);

        tileManager.draw(g2, ScreenOffset);

        for (Entity e : Entity.AllEntities) {
            e.draw(g2, ScreenOffset);
        }

        ui.draw(g2);

        g2.dispose();
    }

    public void playMusic(String key) {
        audio.setFile(key);
        audio.play();
        audio.loop();
    }

    public void playSFX(String key) {
        audio.setFile(key);
        audio.play();
    }

    public void stopMusic() {
        audio.stop();
    }
}
