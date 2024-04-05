package generation;

import main.BoxCollider;
import main.GamePanel;
import main.Vector2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] Tiles;
    int[][] mapTileNumbers;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        int tileCount = 6;
        Tiles = new Tile[tileCount];
        mapTileNumbers = new int[gamePanel.MaxWorldCol][gamePanel.MaxWorldRow];

        try {
            for (int i = 0; i < tileCount; i++) {
                BufferedImage pre = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                        ("/tiles/" + padLeft(Integer.toString(i), "0", 3) + ".png"))));
                BufferedImage post = new BufferedImage(gamePanel.TileSize, gamePanel.TileSize, pre.getType());
                Graphics2D g2 = post.createGraphics();
                g2.drawImage(pre, 0, 0, gamePanel.TileSize, gamePanel.TileSize, null);
                g2.dispose();

                if (i == 3 || i == 4) {
                    Tiles[i] = new Tile(post, true);
                    continue;
                }

                Tiles[i] = new Tile(post, false);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        loadMap("/maps/map01.txt");
    }

    public void loadMap(String mapPath) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(mapPath);
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;

            while (col < gamePanel.MaxWorldCol && row < gamePanel.MaxWorldRow) {
                String line = bufferedReader.readLine();

                while (col < gamePanel.MaxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt((numbers[col]));
                    mapTileNumbers[col][row] = num;
                    if (Tiles[mapTileNumbers[col][row]].CollisionEnabled)
                        new BoxCollider(new Vector2(col * gamePanel.TileSize, row * gamePanel.TileSize), new Vector2(gamePanel.TileSize, gamePanel.TileSize));
                    col++;
                }

                if (col == gamePanel.MaxWorldCol) {
                    col = 0;
                    row++;
                }
            }

            bufferedReader.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String padLeft(String toPad, String padding, int amount) {
        if (amount < toPad.length()) return toPad;

        return padding.repeat(amount - toPad.length()) + toPad;
    }

    public void draw(Graphics2D g2, Vector2 offset) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (row < gamePanel.MaxWorldRow) {
            Tile tile = Tiles[mapTileNumbers[col][row]];
            g2.drawImage(tile.Image, x - (int) offset.X, y - (int) offset.Y, null);

            col++;
            x += gamePanel.TileSize;

            if (col >= gamePanel.MaxWorldCol) {
                col = 0;
                x = 0;
                row++;
                y += gamePanel.TileSize;
            }
        }
    }
}
