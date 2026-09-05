package core;
import java.util.*;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import tileengine.TETile;
import tileengine.Tileset;

import static utils.RandomUtils.uniform;

public class WorldGenerator {
    private int WIDTH;
    private int HEIGHT;

    private long SEED;
    private Random RANDOM;

    private TETile[][] world;
    private List<Room> rooms = new ArrayList<>();

    public WorldGenerator(int width, int height, long seed) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.SEED = seed;
        this.RANDOM = new Random(SEED);
        this.world = new TETile[WIDTH][HEIGHT];
    }

    public TETile[][] generate() {
        fillWithNothing();
        generateAllRooms();
        connectAllRooms();
        buildWalls();
        return this.world;
    }

    private void fillWithNothing() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    private void generateAllRooms() {
        int roomCount = uniform(RANDOM, (HEIGHT * WIDTH) / 210, (HEIGHT * WIDTH) / 150);

        for (int i = 0; i < roomCount; i++) {
            int roomX = uniform(RANDOM, 1, WIDTH - 2);  // 1 ~ WIDTH - 3
            int roomY = uniform(RANDOM, 1, HEIGHT - 1); // 1 ~ HEIGHT - 2

            int roomWidth = uniform(RANDOM, 3, (WIDTH + HEIGHT) / 10);
            int roomHeight = uniform(RANDOM, 3, (WIDTH + HEIGHT) / 10);

            Room newRoom = new Room(new Position(roomX, roomY), roomWidth, roomHeight);

            if (newRoom.isOutOfBounds(WIDTH, HEIGHT) || newRoom.isOverlapping(rooms)) {
                i--;
                continue;
            }

            rooms.add(newRoom);
            drawRoom(newRoom);
        }
    }

    private void drawRoom(Room room) {
        Position bottomLeft = room.bottomLeft();
        int width = room.width();
        int height = room.height();

        for (int xOffset = 0; xOffset < width; xOffset++) {
            for (int yOffset = 0; yOffset < height; yOffset++) {
                world[bottomLeft.x() + xOffset][bottomLeft.y() + yOffset] = Tileset.FLOOR;
            }
        }
    }

    private void connectAllRooms() {
        int n = rooms.size();

        // Map each room to a number
        HashMap<Room, Integer> roomsMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            roomsMap.put(rooms.get(i), i);
        }

        int edgeCount = n - 1;
        int totalEdges = (n * (n - 1)) / 2;

        // Add all edges to the priority queue
        PriorityQueue<Edge> edges = new PriorityQueue<>(totalEdges);
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++){
                edges.add(new Edge(rooms.get(i), rooms.get(j)));
            }
        }

        WeightedQuickUnionUF roomDisjointSet = new WeightedQuickUnionUF(n);
        ArrayList<Edge> shortestEdges = new ArrayList<>();

        int validEdgeCount = 0;

        while (!edges.isEmpty()) {
            if (validEdgeCount == edgeCount) {
                break;
            }

            Edge e = edges.poll(); // get and delete the shortest edge

            int indexOfRoomA = roomsMap.get(e.a());
            int indexOfRoomB = roomsMap.get(e.b());

            // If two rooms are not connected
            if (roomDisjointSet.find(indexOfRoomA) != roomDisjointSet.find(indexOfRoomB)) {
                roomDisjointSet.union(indexOfRoomA, indexOfRoomB);
                shortestEdges.add(e);
                validEdgeCount++;
            }
        }

        for (Edge e : shortestEdges) {
            connectRooms(e.a(), e.b());
        }
    }

    private void drawHorizontalHallway(int x1, int x2, int y) {
        int min = Math.min(x1, x2);
        int max = Math.max(x1, x2);
        for (int x = min; x <= max; x++) {
            world[x][y] = Tileset.FLOOR;
        }
    }

    private void drawVerticalHallway(int y1, int y2, int x) {
        int min = Math.min(y1, y2);
        int max = Math.max(y1, y2);
        for (int y = min; y <= max; y++) {
            world[x][y] = Tileset.FLOOR;
        }
    }

    /** Connect Rooms A and B Horizontally if possible.
     *  Return true to indicate success. */
    private boolean tryConnectRoomsHorizontal(Room a, Room b) {
        int bottomA = a.bottomLeft().y();
        int bottomB = b.bottomLeft().y();
        int topA = a.topLeft().y();
        int topB = b.topLeft().y();

        if (topB <= topA && topB >= bottomA) {
            int y = uniform(RANDOM, Math.max(bottomA, bottomB), topB + 1);
            drawHorizontalHallway(a.getCenter().x(), b.getCenter().x(), y);
            return true;
        } else if (topA <= topB && topA >= bottomB) {
            int y = uniform(RANDOM, Math.max(bottomB, bottomA), topA + 1);
            drawHorizontalHallway(a.getCenter().x(), b.getCenter().x(), y);
            return true;
        }
        return false;
    }

    /** Connect Rooms A and B Vertically if possible.
     *  Return true to indicate success. */
    private boolean tryConnectRoomsVertical(Room a, Room b) {
        int leftA = a.bottomLeft().x();
        int rightA = a.bottomRight().x();
        int leftB = b.bottomLeft().x();
        int rightB = b.bottomRight().x();

        if (leftB <= rightA && leftB >= leftA) {
            int x = uniform(RANDOM, leftB, Math.min(rightA, rightB) + 1);
            drawVerticalHallway(a.getCenter().y(), b.getCenter().y(), x);
            return true;
        } else if (leftA <= rightB && leftA >= leftB) {
            int x = uniform(RANDOM, leftA, Math.min(rightA, rightB) + 1);
            drawVerticalHallway(a.getCenter().y(), b.getCenter().y(), x);
            return true;
        }
        return false;
    }

    private void connectRoomsLShape(Room a, Room b) {
        int ax = a.getCenter().x();
        int bx = b.getCenter().x();
        int ay = a.getCenter().y();
        int by = b.getCenter().y();

        if (RANDOM.nextBoolean()) {
            drawHorizontalHallway(ax, bx, ay);
            drawVerticalHallway(ay, by, bx);
        } else {
            drawHorizontalHallway(ax, bx, by);
            drawVerticalHallway(ay, by, ax);
        }
    }

    private void connectRooms(Room a, Room b) {
        if (!tryConnectRoomsHorizontal(a, b) && !tryConnectRoomsVertical(a, b)) {
            connectRoomsLShape(a, b);
        }
    }

    private void buildWalls() {
        for (int x = 1; x < WIDTH - 1; x++) {
            for (int y = 1; y < HEIGHT - 1; y++) {
                if (world[x][y] == Tileset.FLOOR) {
                    for (int xOffset = -1; xOffset <= 1; xOffset++) {
                        for (int yOffset = -1; yOffset <= 1; yOffset++) {
                            if (world[x + xOffset][y + yOffset] == Tileset.NOTHING) {
                                world[x + xOffset][y + yOffset] = Tileset.WALL;
                            }
                        }
                    }
                }
            }
        }
    }
}