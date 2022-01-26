package be.misterfrans.testresearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TestResearch
{
    public static void main(String[] args)
    {
        /*
        50000 cubes generated in a world of 100000 x 100000
        100000 coordinates searched
         */
        ArrayList<Cube> list = new ArrayList<>();
        HashMap<Integer, HashMap<Integer, ArrayList<Cube>>> hashMapComposed = new HashMap<>();
        HashMap<ChunkCoordinates, ArrayList<Cube>> hashMapSimple = new HashMap<>();
        ArrayList<Location> toLook = new ArrayList<>();

        System.out.println("Generation of test data");
        long startGen = System.currentTimeMillis();
        Random random = new Random();
        for(int i = 0; i < 50000; i++)
        {
            int x = random.nextInt(100000)-50000;
            int y = random.nextInt(255);
            int z = random.nextInt(100000)-50000;
            Cube cube = new Cube(x, y, z, x + 9, y + 9, z + 9);
            list.add(cube);
            for(int chunkX = cube.getChunkX1(); chunkX <= cube.getChunkX2(); chunkX++)
            {
                for(int chunkZ = cube.getChunkZ1(); chunkZ <= cube.getChunkZ2(); chunkZ++)
                {
                    hashMapComposed.computeIfAbsent(chunkX, s -> new HashMap<>()).computeIfAbsent(chunkZ, s -> new ArrayList<>()).add(cube);
                    ChunkCoordinates chunkCoordinates = new ChunkCoordinates(chunkX, chunkZ);
                    hashMapSimple.computeIfAbsent(chunkCoordinates, s -> new ArrayList<>()).add(cube);
                }
            }
        }

        for(int i = 0; i < 100000; i++)
        {
            toLook.add(new Location(random.nextInt(100000)-50000, random.nextInt(255), random.nextInt(100000)-50000));
        }
        System.out.println("End of the generation. Duration: "+(System.currentTimeMillis()-startGen)+" ms");
        System.out.println("Start of the test");

        int cubesFound = 0;
        long startList = System.currentTimeMillis();
        for(Location location :toLook)
        {
            for(Cube cube :list)
            {
                if(cube.isInside(location.getX(), location.getY(), location.getZ()))
                {
                    cubesFound++;
                    break;
                }
            }
        }

        System.out.println("Search time with the list method: "+(System.currentTimeMillis()-startList)+" ms and "+cubesFound+" cubes found");

        cubesFound = 0;
        long startHashMapComposed = System.currentTimeMillis();
        for(Location location :toLook)
        {
            int chunkX = location.getChunkX();
            int chunkZ = location.getChunkZ();
            if(!hashMapComposed.containsKey(chunkX))
            {
                continue;
            }

            if(!hashMapComposed.get(chunkX).containsKey(chunkZ))
            {
                continue;
            }


            for(Cube cube :hashMapComposed.get(chunkX).get(chunkZ))
            {
                if(cube.isInside(location.getX(), location.getY(), location.getZ()))
                {
                    cubesFound++;
                    break;
                }
            }
        }

        System.out.println("Search time with the hashmapcomposed method: "+(System.currentTimeMillis()-startHashMapComposed)+" ms and "+cubesFound+" cubes found");

        cubesFound = 0;
        long startHashMapComposedBis = System.currentTimeMillis();
        for(Location location :toLook)
        {
            HashMap<Integer, ArrayList<Cube>> resX;
            if((resX = hashMapComposed.get(location.getChunkX())) == null)
            {
                continue;
            }

            ArrayList<Cube> l;

            if((l = resX.get(location.getChunkZ())) == null)
            {
                continue;
            }

            for(Cube cube :l)
            {
                if(cube.isInside(location.getX(), location.getY(), location.getZ()))
                {
                    cubesFound++;
                    break;
                }
            }
        }

        System.out.println("Search time with the hashmapcomposed bis method: "+(System.currentTimeMillis()-startHashMapComposedBis)+" ms and "+cubesFound+" cubes found");

        cubesFound = 0;
        long startHashMapSimple = System.currentTimeMillis();
        for(Location location :toLook)
        {
            ChunkCoordinates chunkCoordinates = new ChunkCoordinates(location.getChunkX(), location.getChunkZ());
            if(!hashMapSimple.containsKey(chunkCoordinates))
            {
                continue;
            }

            for(Cube cube :hashMapSimple.get(chunkCoordinates))
            {
                if(cube.isInside(location.getX(), location.getY(), location.getZ()))
                {
                    cubesFound++;
                    break;
                }
            }
        }

        System.out.println("Search time with the hashmapsimple method: "+(System.currentTimeMillis()-startHashMapSimple)+" ms and "+cubesFound+" cubes found");

        cubesFound = 0;
        long startHashMapSimpleBis = System.currentTimeMillis();
        for(Location location :toLook)
        {
            ArrayList<Cube> l;
            if((l = hashMapSimple.get(new ChunkCoordinates(location.getChunkX(), location.getChunkZ()))) == null)
            {
                continue;
            }

            for(Cube cube :l)
            {
                if(cube.isInside(location.getX(), location.getY(), location.getZ()))
                {
                    cubesFound++;
                    break;
                }
            }
        }

        System.out.println("Search time with the hashmapsimple bis method: "+(System.currentTimeMillis()-startHashMapSimpleBis)+" ms and "+cubesFound+" cubes found");
    }
}
