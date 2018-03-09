import java.io.*;
import java.util.LinkedList;
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
            int rand = MyRandom.getRandomInt(1, dimension);
            data[index] = rand;
            if (!intArrayContains(data, rand, index)) {
                index++;
            }
        }
        return data;
    }

    private int[] swap(int[] data, int index1, int index2) {

        // copy array
        int[] result = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[i];
        }

        int temp = data[index1];
        result[index1] = data[index2];
        result[index2] = temp;
        return result;
    }

    private static boolean intArrayContains(int[] arr, int key, int pos) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == key && i != pos) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns pseudo random integer from range [min, max] (inclusive)
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandomInt(int min, int max) {
        if (max < min) {
            int temp = min;
            min = max;
            max = temp;
        }
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

    public void mutate() {
        int rand1 = MyRandom.getRandomInt(0, dimension - 1);
        int rand2 = MyRandom.getRandomInt(0, dimension - 1);
        // two different positions
        while (rand1 == rand2) {
            rand2 = MyRandom.getRandomInt(0, dimension - 1);
        }
        this.data = swap(data, rand1, rand2);
    }

    public int[] getData() {
        return data;
    }

    public int getDimension() {
        return dimension;
    }

    public void reapir() {

        // find missing values
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 1; i <= dimension; i++) {
            if (!intArrayContains(data, i, -1)) {
                list.add(i);
            }
        }

        // insert missing values instead of duplicates
        for (int i = 0; i < dimension; i++) {
            if (intArrayContains(data, data[i], i)) {
                data[i] = list.pop();
            }
        }
    }

}
