package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    private final GamePanel gamePanel;
    public MouseHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        int x = e.getX() + (int) gamePanel.ScreenOffset.X;
        int y = e.getY() + (int) gamePanel.ScreenOffset.Y;

        //new RigidBody(10, 0.98,
                //new BoxCollider(new Vector2(x, y), new Vector2(gamePanel.TileSize, gamePanel.TileSize)));
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }
}
