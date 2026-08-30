package core;

import java.util.List;

// A room is a rectangle area of FLOOR
public class Room {
    private final Position bottomLeft;
    private final int width;
    private final int height;

    public Room(Position bottomLeft, int width, int height) {
        this.bottomLeft = bottomLeft;
        this.width = width;
        this.height = height;
    }

    public Position getCenter() {
        int xOffset = width / 2;
        int yOffset = height / 2;
        return new Position(bottomLeft.x + xOffset, bottomLeft.y + yOffset);
    }

    public boolean isOutOfBounds(int worldWidth, int worldHeight) {
        Room theWorld = new Room(new Position(1, 1), worldWidth - 2, worldHeight - 2);

        return !(theWorld.containsGrid(bottomRight()) && theWorld.containsGrid(bottomLeft)
                  && theWorld.containsGrid(topLeft()) && theWorld.containsGrid(topRight()));
    }

    public boolean isOverlapping(List<Room> rooms) {
        for (Room other : rooms) {
            if ((other.containsGrid(bottomLeft) || other.containsGrid(bottomRight())
               || other.containsGrid(topLeft()) || other.containsGrid(topRight()))) {
                return true;
            }
        }

        return false;
    }

    private boolean containsGrid(Position p) {
        return p.x >= bottomLeft.x && p.x < bottomLeft.x + width && p.y >= bottomLeft.y && p.y < bottomLeft.y + height;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public Position bottomLeft() {
        return bottomLeft;
    }

    public Position bottomRight() {
        return new Position(bottomLeft.x + width - 1, bottomLeft.y);
    }

    public Position topLeft() {
        return new Position(bottomLeft.x, bottomLeft.y + height - 1);
    }

    public Position topRight() {
        return new Position(bottomLeft.x + width - 1, bottomLeft.y + height - 1);
    }
}