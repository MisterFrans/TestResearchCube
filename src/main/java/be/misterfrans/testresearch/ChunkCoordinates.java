package be.misterfrans.testresearch;

import java.util.Objects;

public class ChunkCoordinates
{
    private final int x;

    private final int z;

    private final int hashCode;

    public ChunkCoordinates(int x, int z) {
        this.x = x;
        this.z = z;
        this.hashCode = Objects.hash(this.x, this.z);
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }

    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true;
        }

        if(!(other instanceof ChunkCoordinates otherCoordinates))
        {
            return false;
        }

        return (x == otherCoordinates.getX() && z == otherCoordinates.getZ());
    }

    public int hashCode()
    {
        return hashCode;
    }
}
