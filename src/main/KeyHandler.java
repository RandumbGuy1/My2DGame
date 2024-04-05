package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public final Vector2 WAInput = new Vector2(0, 0);
    public final Vector2 SDInput = new Vector2(0, 0);
    public boolean Jumping = false;
    public boolean Interacting = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) WAInput.Y = 1;
        if (code == KeyEvent.VK_S) SDInput.Y = -1;
        if (code == KeyEvent.VK_A) WAInput.X = 1;
        if (code == KeyEvent.VK_D) SDInput.X = -1;

        if (code == KeyEvent.VK_SPACE) {
            Jumping = true;
        }

        if (code == KeyEvent.VK_F) {
            Interacting = true;
        }
    }

    public void ResetTapKeys() {
        Jumping = false;
        Interacting = false;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) WAInput.Y = 0;
        if (code == KeyEvent.VK_S) SDInput.Y = 0;
        if (code == KeyEvent.VK_A) WAInput.X = 0;
        if (code == KeyEvent.VK_D) SDInput.X = 0;
    }

    public Vector2 getDirectionalInput() {
        return WAInput.add(SDInput);
    }
}
