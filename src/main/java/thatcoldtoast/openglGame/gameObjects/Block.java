package thatcoldtoast.openglGame.gameObjects;

import thatcoldtoast.openglGame.Main;
import thatcoldtoast.openglGame.graphics.shapes.Cube;

public class Block {
    Cube cube;
    public boolean air = false;//Math.random() * 10 > 5;
    public String blockType;

    int x, y, z;

    public Block(int x_, int y_, int z_) {
        x = x_;
        y = y_;
        z = z_;

        cube = new Cube();
        cube.create(x, y, z);
    }

    public void update(boolean[] bools)
    {
        if(blockType.equals("wood")) { //Replace all this with the Texture Atlas
//            Main.texture.create("/textures/wood.png");
//            Main.texture.bind();
            cube.draw(bools);
        } else if(blockType.equals("stone")) {
//            Main.texture.create("/textures/checker.png");
//            Main.texture.bind();
            cube.draw(bools);
        } else if(blockType.equals("checker")) {
//            Main.texture.create("/textures/checker.png");
//            Main.texture.bind();
            cube.draw(bools);
        } else if(blockType.equals("dirt")) {
//            Main.texture.create("/textures/checker.png");
//            Main.texture.bind();
            cube.draw(bools);
        } else if(blockType.equals("air")) {
        }
    }

    public void destroy()
    {
        cube.destroy();
    }
}