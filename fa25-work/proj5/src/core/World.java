package core;
import tileengine.TETile;

public class World {
    private TETile[][] grid;

    private Position avatarPosition;
    private int score;

    public World(TETile[][] grid) {
        this.grid = grid;
    }

    public TETile[][] getGrid() {
        return grid;
    }

    public void moveAvatar(int dx, int dy) {

    }

    public void setAvatarPosition(Position a) {
        avatarPosition = a;
    }
}