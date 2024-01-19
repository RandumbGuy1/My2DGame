package main;
import entity.Player;
import generation.Tile;
import generation.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;
    public final int TileSize = originalTileSize * scale;

    public final int MaxScreenCol = 40;
    public final int MaxScreenRow = 22;
    public final int ScreenWidth = TileSize * MaxScreenCol;
    public final int ScreenHeight = TileSize * MaxScreenRow;

    int fps = 60;
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler(this);
    TileManager tileManager = new TileManager(this);
    Player player = new Player(this, keyHandler);

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

        //Spawn Floor
        new BoxCollider(new Vector2(0, TileSize * (MaxScreenRow - 1)), new Vector2(TileSize * MaxScreenCol, TileSize));
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    BoxCollider[] boxesCollider = {
            new BoxCollider(new Vector2(TileSize * MaxScreenCol * 0.5, TileSize * MaxScreenRow * 0.5), new Vector2(TileSize, TileSize)),
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
    }

    public Vector2 ScreenOffset = new Vector2(0, 0);

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        ScreenOffset.X = (int) player.Position.X - TileSize * ((double) MaxScreenCol / 2);

        tileManager.draw(g2, ScreenOffset);
        player.draw(g2, ScreenOffset);

        for (BoxCollider other : BoxCollider.AllColliders) other.draw(g2, ScreenOffset);

        g2.dispose();
    }
}
