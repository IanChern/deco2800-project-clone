package deco2800.spooky.util;

public class Cube {
    private float x = 0;
    private float y = 0;
    private float z = 0;

    private Cube(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static float cubeDistance(Cube a, Cube b) {
        return distance(a.x, a.y, a.z, b.x, b.y, b.z);
    }
    
    private static float distance(float ax, float ay, float az, float bx, float by, float bz) {
        return (Math.abs(ax - bx) + Math.abs(ay - by) + Math.abs(az - bz)) / 2.0f;
    }

    public static Cube oddqToCube(float q, float r) {
        float x = q;
        float y = r - (q - Math.abs(q % 2))/2f;
        float z = -x-y;
        return new Cube(x, y, z);
    }
    
    public String toString() {
    	return "[" + x + " " + y + " " + z + "]";
    }
}
