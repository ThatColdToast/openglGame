package thatcoldtoast.openglGame.world;

import thatcoldtoast.openglGame.graphics.shapes.Cube;

public class Block {
    Cube cube;
    public boolean air = false;//Math.random() * 10 > 5;
    public String blockType = "dirt";

    int x, y, z;

    public Block(int x_, int y_, int z_, String blockType_) {
        x = x_;
        y = y_;
        z = z_;
        blockType = blockType_;

        cube = new Cube();

        if(blockType.equals("wood")) { //Replace all this with the Texture Atlas
            cube.create(x, y, z, 1);
        } else if(blockType.equals("stone")) {
            cube.create(x, y, z, 3);
        } else if(blockType.equals("checker")) {
            cube.create(x, y, z, 2);
        } else if(blockType.equals("dirt")) {
            cube.create(x, y, z, 0);
        } else {
            cube.create(x, y, z, 4);
        }
    }

    public void update(boolean[] bools)
    {
        if(!blockType.equals("air"))
            cube.draw(bools);
    }

    public void destroy()
    {
        cube.destroy();
    }
}