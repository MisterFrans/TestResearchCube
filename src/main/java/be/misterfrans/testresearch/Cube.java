package be.misterfrans.testresearch;

public class Cube
{
    private final int x1;
    private final int y1;
    private final int z1;
    private final int x2;
    private final int y2;
    private final int z2;

    public Cube(int x1, int y1, int z1, int x2, int y2, int z2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    public boolean isInside(int x, int y, int z)
    {
        if(x < x1 || x > x2) return false;
        if(y < y1 || y > y2) return false;
        return z >= z1 && z <= z2;
    }

    public int getChunkX1()
    {
        return x1 >> 4;
    }

    public int getChunkZ1()
    {
        return z1 >> 4;
    }

    public int getChunkX2()
    {
        return x2 >> 4;
    }

    public int getChunkZ2()
    {
        return z2 >> 4;
    }
}
