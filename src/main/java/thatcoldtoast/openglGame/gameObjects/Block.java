package thatcoldtoast.openglGame.gameObjects;

import thatcoldtoast.openglGame.Main;
import thatcoldtoast.openglGame.graphics.shapes.Cube;

public class Block {
    Cube cube;
    public boolean air = false;//Math.random() * 10 > 5;
    public String blockType;

    public Block(int x, int y, int z) {
        cube = new Cube();
        cube.create(x, y, z);
    }

    public void update(boolean[] bools)
    {
        if(blockType.equals("wood")) { //Replace all this with the Texture Atlas
            Main.texture.create("/textures/wood.png");
            Main.texture.bind();
            cube.draw(bools);
        }
        if(blockType.equals("checker")) {
            Main.texture.create("/textures/checker.png");
            Main.texture.bind();
            cube.draw(bools);
        }

        if(blockType.equals("air")) {
        }
    }

    public void destroy()
    {
        cube.destroy();
    }
}