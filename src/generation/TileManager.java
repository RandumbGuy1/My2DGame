package generation;

import main.BoxCollider;
import main.GamePanel;
import main.Vector2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tiles;
    int[][] mapTileNumbers;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        int tileCount = 6;
        tiles = new Tile[tileCount];
        mapTileNumbers = new int[gamePanel.MaxScreenCol][gamePanel.MaxScreenRow];

        try {
            for (int i = 0; i < tileCount; i++) {
                tiles[i] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                        ("/tiles/" + padLeft(Integer.toString(i), "0", 3) + ".png")))), false);
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

            while (col < gamePanel.MaxScreenCol && row < gamePanel.MaxScreenRow) {
                String line = bufferedReader.readLine();

                while (col < gamePanel.MaxScreenCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt((numbers[col]));
                    mapTileNumbers[col][row] = num;
                    col++;
                }

                if (col == gamePanel.MaxScreenCol) {
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

        while (row < gamePanel.MaxScreenRow) {
            g2.drawImage(tiles[mapTileNumbers[col][row]].Image, x - (int) offset.X, y - (int) offset.Y, gamePanel.TileSize, gamePanel.TileSize, null);
            col++;
            x += gamePanel.TileSize;

            if (col >= gamePanel.MaxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gamePanel.TileSize;
            }
        }
    }
}
