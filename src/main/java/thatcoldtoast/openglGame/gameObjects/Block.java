package thatcoldtoast.openglGame.gameObjects;

import thatcoldtoast.openglGame.graphics.shapes.Cube;

public class Block {
    Cube cube;
    public boolean air = true;//Math.random() * 10 > 5;

    public Block(int x, int y, int z) {
        cube = new Cube();
        cube.create(x, y, z);
    }

    public void update(boolean[] bools)
    {
        //if(!air)
            cube.draw(bools);
    }

    public void destroy()
    {
        cube.destroy();
    }
}