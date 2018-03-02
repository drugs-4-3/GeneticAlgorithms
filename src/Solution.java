import java.io.*;


public class Solution {

    private int[] data;
    private int dimension;

    public Solution(int[] data) {
        this.data = data;
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
