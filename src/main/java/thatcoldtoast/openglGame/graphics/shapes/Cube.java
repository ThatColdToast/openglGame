package thatcoldtoast.openglGame.graphics.shapes;

import thatcoldtoast.openglGame.gameObjects.Block;

public class Cube {
    Quad quadTop;
    Quad quadFront;
    Quad quadLeft;
    Quad quadRight;
    Quad quadBack;
    Quad quadBottom;

    public Cube() {
        quadTop = new Quad();
        quadFront = new Quad();
        quadLeft = new Quad();
        quadRight = new Quad();
        quadBack = new Quad();
        quadBottom = new Quad();
    }

    public void create(float xOff, float yOff, float zOff) {
        createQuads(
            new float[] {
                    0 + xOff, 1 + yOff, 1 + zOff //TLF
            },
            new float[] {
                    1 + xOff, 1 + yOff, 1 + zOff //TRF
            },
            new float[] {
                    0 + xOff, 0 + yOff, 1 + zOff //BLF
            },
            new float[] {
                    1 + xOff, 0 + yOff, 1 + zOff //BRF
            },
            new float[] {
                    0 + xOff, 1 + yOff, 0 + zOff //TLB
            },
            new float[] {
                    1 + xOff, 1 + yOff, 0 + zOff //TRB
            },
            new float[] {
                    0 + xOff, 0 + yOff, 0 + zOff //BLB
            },
            new float[] {
                    1 + xOff, 0 + yOff, 0 + zOff //BRB
            }
        );
    }

    public void createQuads(float[] TLF, float[] TRF, float[] BLF, float[] BRF, float[] TLB, float[] TRB, float[] BLB, float[] BRB) { //verts are named TLF (top, left, front) or BLB (bottom, left, back)
        quadTop.create(   TLF, TLB, TRF, TRB); //rotation from front
        quadFront.create( BLF, TLF, BRF, TRF); //the front facing side
        quadLeft.create(  BLB, TLB, BLF, TLF); //rotation from front
        quadRight.create( BRF, TRF, BRB, TRB); //rotation from front
        quadBack.create(  BRB, TRB, BLB, TLB); //rotation from front around vertical axis (twist)
        quadBottom.create(BLB, BLF, BRB, BRF); //rotation from front
    }

    public void draw(boolean[] bools) {
        if(bools[0])
            quadTop.draw();
        if(bools[1])
            quadFront.draw();
        if(bools[2])
            quadLeft.draw();
        if(bools[3])
            quadRight.draw();
        if(bools[4])
            quadBack.draw();
        if(bools[5])
            quadBottom.draw();
    }

    public void destroy() {
        quadTop.destroy();
        quadFront.destroy();
        quadLeft.destroy();
        quadRight.destroy();
        quadBack.destroy();
        quadBottom.destroy();
    }
}
