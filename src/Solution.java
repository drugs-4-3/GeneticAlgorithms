import java.io.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Solution {

    private int[] data;
    private int dimension;

    public Solution(int[] data) {
        this.data = data;
        this.dimension = data.length;
    }

    public static Solution getRandomSolution(int dimension) {
        return new Solution(getRandIntArr(dimension));
    }

    public static int[] getRandIntArr(int dimension) {
        int[] data = new int[dimension];
        int index = 0;
        while (index < dimension) {
            int rand = getRandomInt(1, dimension);
            data[index] = rand;
            if (!intArrayContains(data, rand, index)) {
                index++;
            }
        }
        return data;
    }

    private static boolean intArrayContains(int[] arr, int key, int pos) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == key && i != pos) {
                return true;
            }
        }
        return false;
    }

    private static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public Solution(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int dimension;
            int[] data;

            String[] line = bufferedReader.readLine().trim().split("\\s+");
            dimension = Integer.valueOf(line[0]);
            data = new int[dimension];

            line = bufferedReader.readLine().trim().split("\\s+");
            for (int i = 0; i < dimension; i++ ) {
                data[i] = Integer.valueOf(line[i]);
            }
            this.dimension = dimension;
            this.data = data;

        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }

    public int[] getData() {
        return data;
    }

    public int getDimension() {
        return dimension;
    }

}
