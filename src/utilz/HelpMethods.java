package utilz;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.*;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import main.Game;
import entities.Doggy;
import entities.Doggy2;

public class HelpMethods {

    // Check if an entity can move to a specified position
    public static boolean CanMoveHere(double x, double y, double width, double height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData) && !IsSolid(x + width, y + height, lvlData)
                && !IsSolid(x + width, y, lvlData) && !IsSolid(x, y + height, lvlData)) {
            return true;
        }
        return false;
    }

    // Check if a specific position is solid
    private static boolean IsSolid(double x, double y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }

        int xIndex = (int) (x / Game.TILES_SIZE);
        int yIndex = (int) (y / Game.TILES_SIZE);

        return IsTileSolid(xIndex, yIndex, lvlData);
    }

    // Check if a specific tile is solid
    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];

        // Use constants instead of literal values
        if (value >= SPRITE_THRESHOLD || value < 0 || value != 11) {
            return true;
        }
        return false;
    }

    // Get the X position of an entity next to a wall
    public static float GetEntityXPosNextToWall(Rectangle hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.getX() / Game.TILES_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.getWidth());
            return tileXPos + xOffset - 1;
        } else {
            // Left
            return currentTile * Game.TILES_SIZE;
        }
    }

    // Get the Y position of an entity under a roof or above a floor
    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.getY() / Game.TILES_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitbox.getHeight());
            return tileYPos + yOffset - 1;
        } else {
            // Jumping
            return currentTile * Game.TILES_SIZE;
        }
    }

    // Check if an entity is on the floor
    public static boolean IsEntityOnFloor(Rectangle hitbox, int[][] lvlData) {
        if (!IsSolid(hitbox.getX(), (hitbox.getY() + hitbox.getHeight() + 1), lvlData)
                && !IsSolid((hitbox.getX() + hitbox.getWidth()), (hitbox.getY() + hitbox.getHeight() + 1), lvlData)) {
            return false;
        }

        return true;
    }

    // Check if a specific floor exists
    public static boolean IsFloor(Rectangle hitbox, float xSpeed, int[][] lvlData) {
        if (xSpeed > 0) {
            return IsSolid(hitbox.getX() + hitbox.getWidth() + xSpeed, hitbox.getY() + hitbox.getHeight() + 1, lvlData);
        } else {
            return IsSolid(hitbox.getX() + xSpeed, hitbox.getY() + hitbox.getHeight() + 1, lvlData);
        }
    }

    // Check if all tiles within a range are walkable
    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i <= xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData) || !IsTileSolid(xStart + i, y + 1, lvlData)) {
                return false;
            }
        }
        return true;
    }

    // Check if the line of sight is clear between two entities
    public static boolean IsSightClear(int[][] lvlData, Rectangle enemyBox, Rectangle playerBox, int yTile) {
        int firstXTile = (int) (enemyBox.getX() / Game.TILES_SIZE);

        int secondXTile;
        if (IsSolid(playerBox.getX(), playerBox.getY() + playerBox.getHeight() + 1, lvlData)) {
            secondXTile = (int) (playerBox.getX() / Game.TILES_SIZE);
        } else {
            secondXTile = (int) ((playerBox.getX() + playerBox.getWidth()) / Game.TILES_SIZE);
        }

        if (firstXTile > secondXTile) {
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
        } else {
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
        }
    }

    // Convert an image into level data
    public static int[][] GetLevelData(Image img) {
        int[][] lvlData = new int[(int) img.getHeight()][(int) img.getWidth()];

        PixelReader pixelReader = img.getPixelReader();

        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = pixelReader.getColor(i, j);
                int value = (int) (color.getRed() * 255);

                // Use the constant SPRITE_THRESHOLD instead of the literal value 48
                if (value >= SPRITE_THRESHOLD) {
                    value = 0;
                }

                lvlData[j][i] = value;
            }
        }

        return lvlData;
    }

    // Get a list of Doggy entities from an image
    public static ArrayList<Doggy> GetDogs(Image img) {
        ArrayList<Doggy> list = new ArrayList<>();

        PixelReader pixelReader = img.getPixelReader();

        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = pixelReader.getColor(i, j);
                int value = (int) (color.getGreen() * 255);
                if (value == DOGGY) {
                    list.add(new Doggy(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                }
            }
        }
        return list;
    }

    // Get a list of Doggy2 entities from an image
    public static ArrayList<Doggy2> GetDogs2(Image img) {
        ArrayList<Doggy2> list = new ArrayList<>();

        PixelReader pixelReader = img.getPixelReader();

        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = pixelReader.getColor(i, j);
                int value = (int) (color.getGreen() * 255);
                if (value == DOGGY2) {
                    list.add(new Doggy2(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                }
            }
        }
        return list;
    }

    // Get the spawn position of the player from an image
    public static Point2D GetPlayerSpawn(Image img) {
        PixelReader pixelReader = img.getPixelReader();

        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = pixelReader.getColor(i, j);
                int value = (int) (color.getGreen() * 255);
                if (value == 100) {
                    return new Point2D(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
                }
            }
        }
        return new Point2D(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
    }
}
