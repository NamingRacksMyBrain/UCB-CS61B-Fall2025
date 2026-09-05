package core;

import tileengine.TERenderer;

public class Main {
    public static void main(String[] args) {

        TERenderer ter = new TERenderer();
        int WIDTH = 120;
        int HEIGHT = 60;
        int SEED = 1504;
        ter.initialize(WIDTH, HEIGHT);

        WorldGenerator wg = new WorldGenerator(WIDTH, HEIGHT, SEED);
        ter.renderFrame(wg.generate());
    }
}
