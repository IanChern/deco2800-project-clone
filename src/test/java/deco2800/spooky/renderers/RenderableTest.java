package deco2800.spooky.renderers;

import org.junit.Test;

import static org.junit.Assert.*;

public class RenderableTest {
    private static final float GROUND = 0f;
    private static final float STANDING_BACKGROUND = 1f;
    private static final float STANDING = 2f;
    private static final float STANDING_FOREGROUND = 3f;
    private static final float FLYING = 4f;

    private class TestRenderable implements Renderable {

        private float renderHeight;
        private float renderDepth;

        private TestRenderable(float depth, float renderHeight) {
            this.renderDepth = depth;
            this.renderHeight = renderHeight;
        }


        @Override
        public String getTexture() {
            return null;
        }

        @Override
        public float getCol() {
            return 0;
        }

        @Override
        public float getRow() {
            return 0;
        }

        @Override
        public int getHeight() {
            return 0;
        }

        @Override
        public float getColRenderLength() {
            return 0;
        }

        @Override
        public float getRowRenderLength() {
            return 0;
        }

        public float getRenderDepth() {
            return this.renderDepth;
        }

        public float getRenderHeight() {
            return this.renderHeight;
        }
    }

    public int isSame(TestRenderable a1, TestRenderable a2) {
        if(a1.getRenderDepth() == a2.getRenderDepth()
                && a1.getRenderHeight() == a2.getRenderHeight()) {
            return 0;
        } else {
            return -1;
        }
    }

    @Test
    public void reflexivityTest() {
        TestRenderable testRenderable = new TestRenderable(STANDING, 0f);
        assertEquals(0, isSame(testRenderable, testRenderable));
    }

    @Test
    public void symmetryTest() {
        //different layers
        TestRenderable testRenderable = new TestRenderable(STANDING, 1);
        TestRenderable testRenderable2 = new TestRenderable(GROUND, 1);
        assertEquals(-1, isSame(testRenderable, testRenderable2));
        assertEquals(-1, isSame(testRenderable2, testRenderable));
        //different positions
        testRenderable2 = new TestRenderable(STANDING, 2);
        assertEquals(-1, isSame(testRenderable, testRenderable2));
        assertEquals(-1, isSame(testRenderable2, testRenderable));
    }

    @Test
    public void symmetryTest1() {
        TestRenderable testRenderable = new TestRenderable(STANDING_FOREGROUND, FLYING);
        TestRenderable testRenderable2 = new TestRenderable(FLYING, STANDING_FOREGROUND);
        assertEquals(-1, isSame(testRenderable, testRenderable2));
        assertEquals(-1, isSame(testRenderable2, testRenderable));
    }

}