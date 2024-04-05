package items;

import entity.Player;
import main.BoxCollider;
import main.GamePanel;
import main.Vector2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class PasswordTent extends Item {
    private GamePanel gamePanel;
    public PasswordTent(BoxCollider collider, GamePanel gamePanel) {
        Name = "Password Tent";
        Position = collider.Position;
        Collider = collider;
        this.gamePanel = gamePanel;

        try {
            Image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(("/objects/tent.png"))));
        } catch (IOException ignore) {}
    }

    @Override
    public void draw(Graphics2D g2, Vector2 offset) {
        Position = Collider.Position;

        g2.drawImage(Image,(int) Position.X - (int) offset.X, (int) Position.Y - (int) offset.Y,
                (int) Collider.Scale.X, (int) Collider.Scale.Y, null);

        g2.setColor(Color.white);
        g2.drawString(Name, (int) Position.X - (int) offset.X - 16, (int) Position.Y - (int) offset.Y);
        g2.setColor(Color.black);
    }

    @Override
    public boolean CanUse() {
        return true;
    }

    @Override
    public void UseItem(Player player) {
        gamePanel.playSFX("Unzip.wav");
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
            strength = "Strong";
        } else if (minLength && ((containsUpperChar && containsLowerChar) || containsDigit || containsSpecialChar )) {
            strength = "Medium";
        }else{
            strength = "Weak";
        }
        return strength;
    }
}
