package items;

import entity.Player;
import main.BoxCollider;
import main.GamePanel;
import main.Vector2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Objects;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class PasswordChest extends Item {
    private GamePanel gamePanel;
    private boolean opened = false;
    private double cooldownTime = 0;
    private String generatedPassword = "";
    private BufferedImage openImage;
    public PasswordChest(BoxCollider collider, GamePanel gamePanel) {
        Name = "Password Chest";
        Position = collider.Position;
        Collider = collider;
        this.gamePanel = gamePanel;

        try {
            Image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(("/objects/chest.png"))));
            openImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(("/objects/chest_opened.png"))));
        } catch (IOException ignore) {}
    }

    @Override
    public void draw(Graphics2D g2, Vector2 offset) {
        Position = Collider.Position;
        cooldownTime--;

        if (cooldownTime < 0 && opened) {
            opened = false;
            gamePanel.playSFX("MC Chest Close.wav");
        }

        g2.drawImage(opened ? openImage : Image,(int) Position.X - (int) offset.X, (int) Position.Y - (int) offset.Y,
                (int) Collider.Scale.X, (int) Collider.Scale.Y, null);

        g2.setColor(Color.white);
        g2.drawString(Name, (int) Position.X - (int) offset.X - 16, (int) Position.Y - (int) offset.Y);
        g2.drawString(generatedPassword, (int) Position.X - (int) offset.X, (int) Position.Y - (int) offset.Y + gamePanel.TileSize + 16);
        g2.setColor(Color.black);
    }

    @Override
    public boolean CanUse() {
        return !opened;
    }

    @Override
    public void UseItem(Player player) {
        gamePanel.playSFX("MC Chest Open.wav");
        opened = true;
        cooldownTime = 30;
        generatedPassword = generatePassword(10);

        StringSelection stringSelection = new StringSelection(generatedPassword);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public String generatePassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }
}
