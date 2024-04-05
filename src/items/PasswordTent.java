package items;

import entity.Player;
import main.BoxCollider;
import main.GamePanel;
import main.TextInputFrame;
import main.Vector2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class PasswordTent extends Item {
    private double coolDownTime = 0;
    private String passwordStrength = "";
    private int dialogueSequence = 0;
    private final GamePanel gamePanel;
    private Font arial_40;
    public PasswordTent(BoxCollider collider, GamePanel gamePanel) {
        Name = "Password Tent";
        Position = collider.Position;
        Collider = collider;
        this.gamePanel = gamePanel;

        try {
            Image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(("/objects/tent.png"))));
        } catch (IOException ignore) {}

        arial_40 = new Font("Arial", Font.PLAIN, 15);
    }

    @Override
    public void draw(Graphics2D g2, Vector2 offset) {
        Position = Collider.Position;

        if (dialogueSequence == 2) {
            coolDownTime--;

            if (coolDownTime <= 0) {
                dialogueSequence = 0;
                gamePanel.playSFX("Unzip.wav");
            }
        }

        g2.drawImage(Image,(int) Position.X - (int) offset.X, (int) Position.Y - (int) offset.Y,
                (int) Collider.Scale.X, (int) Collider.Scale.Y, null);

        g2.setColor(Color.white);
        g2.drawString(Name, (int) Position.X - (int) offset.X - 16, (int) Position.Y - (int) offset.Y);

        g2.setFont(arial_40);
        String[] dialogues = {"", "\"Hello I judge your password. Type it in.\"", passwordStrength };
        g2.drawString(dialogues[dialogueSequence], (int) Position.X - (int) offset.X - 32, (int) Position.Y - (int) offset.Y + (gamePanel.TileSize * 2) + 16);
        g2.setColor(Color.black);
    }

    @Override
    public boolean CanUse() {
        return dialogueSequence == 0;
    }

    @Override
    public void UseItem(Player player) {
        gamePanel.playSFX("Unzip.wav");

        dialogueSequence = 1;
        TextInputFrame input = new TextInputFrame();
        input.TextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = input.TextField.getText();

                try {
                    Files.write(Paths.get("res/saved_password.txt"), (password + "\n").getBytes(), StandardOpenOption.APPEND);
                } catch (IOException i) { System.out.println("Password writer exception");}

                passwordStrength = passwordStrength(password);
                dialogueSequence = 2;
                coolDownTime = 240;
            }
        });
    }

    public String passwordStrength(String pass) {
        boolean containsLowerChar = false, containsUpperChar = false;
        boolean containsDigit = false, containsSpecialChar = false, minLength = false;
        String special_chars = "!(){}[]:;<>?,@#$%^&*+=_-~`|./'";
        String strength;
        char[] ch = pass.toCharArray();

        for(int i = 0; i < pass.length(); i++){
            if(Character.isLowerCase(ch[i])){
                containsLowerChar = true;
            }
            if(Character.isUpperCase(ch[i])){
                containsUpperChar = true;
            }
            if(Character.isDigit(ch[i])){
                containsDigit = true;
            }
            if(special_chars.contains(String.valueOf(ch[i]))){
                containsSpecialChar = true;
            }
        }
        if (pass.length() >= 12){
            minLength = true;
        }

        if(minLength && containsDigit && containsUpperChar && containsSpecialChar && containsLowerChar){
            strength = "Your password " + pass + " very strong, homer god battle come.";
        } else if (minLength && ((containsUpperChar && containsLowerChar) || containsDigit || containsSpecialChar )) {
            strength = "Your password " + pass + " is mid";
        }else{
            strength = "Never write passwords again.";
        }
        return strength;
    }
}
