package items;
import entity.Entity;
import entity.Player;
import main.BoxCollider;
import main.GamePanel;
import main.Vector2;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Item extends Entity {
    public static ArrayList<Item> AllItems = new ArrayList<>();
    public BufferedImage Image;
    public String Name;
    public BoxCollider Collider;

    public Item() {
        AllItems.add(this);
    }

    public boolean CanUse() {
        return true;
    }

    public void UseItem(Player player) {

    }
}
