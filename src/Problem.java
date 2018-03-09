import java.io.*;
import java.util.HashMap;

public class Problem {

    private int dimension;
    private int[][] flow;
    private int[][] distance;
    public HashMap<String, Integer> cache = new HashMap<String, Integer>();

    public int double_seen = 0;

    public Problem(int dim, int[][] flow, int[][] dist) {

        this.dimension = dim;

        // copy arrays
        for(int i = 0; i < dim; i++) {
            for(int j = 0; j < dim; j++) {
                this.flow[i][j] = flow[i][j];
                this.distance[i][j] = dist[i][j];
            }
        }
    }

    public Problem(String fileName) {

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // read dimension of problem
            int dimension = Integer.valueOf(bufferedReader.readLine().trim());
            int[][] dist = new int[dimension][dimension];
            int[][] flow = new int[dimension][dimension];

            bufferedReader.readLine(); // skip empty line

            for (int i = 0; i < dimension; i++) {
                String[] line = bufferedReader.readLine().trim().split("\\s+");
                for (int j = 0; j < dimension; j++) {
                    dist[i][j] = Integer.valueOf(line[j]);
                }
            }
            bufferedReader.readLine(); // skip empty line
            for (int i = 0; i < dimension; i++) {
                String[] line = bufferedReader.readLine().trim().split("\\s+");
                for (int j = 0; j < dimension; j++) {
                    flow[i][j] = Integer.valueOf(line[j]);
                }
            }
            bufferedReader.close();

            this.dimension = dimension;
            this.distance = dist;
            this.flow = flow;
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
            ex.printStackTrace();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            ex.printStackTrace();
        }
    }

    public int getDimension() {
        return this.dimension;
    }

    public int[][] getFlow() {
        return this.flow;
    }

    public int[][] getDistance() {
        return this.distance;
    }



    public int getFitness(Solution s) {

        if (!cache.containsKey(s.getHash())) {
            int dim = s.getDimension();
            int[] data = s.getData();
            int fitness = 0;
            if (dim == this.dimension) {
                for (int i = 0; i < dim; i++) {
                    // apply int j = 0 and results will be as in http://anjos.mgi.polymtl.ca/qaplib/inst.html#HRW
                    for (int j = i+1; j < dim; j++) {
                        int val1 = data[i] - 1;
                        int val2 = data[j] - 1;
                        fitness += distance[i][j]*flow[val1][val2];
                    }
                }
                cache.put(s.getHash(), fitness);
            }
            else {
                fitness = -1;
            }
            return fitness;
        }
        else {
            double_seen++;
            return cache.get(s.getHash());
        }
    }

    public static void main(String[] args) {

//        System.out.println(new File(".").getAbsoluteFile());

        Problem problem = new Problem("problems/problem.txt");
        Solution solution = new Solution("solutions/solution.txt");

        int[][] dist = problem.getDistance();
        int[][] flow = problem.getFlow();
        int dim = problem.getDimension();

        System.out.printf("\nDISTANCE: \n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                System.out.printf(dist[i][j] + " ");
            }
            System.out.printf("\n");
        }
        System.out.printf("\nFLOW: \n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                System.out.printf(flow[i][j] + " ");
            }
            System.out.printf("\n");
        }

        int fitness = 0;
        try {
            fitness = problem.getFitness(solution);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Fitness is: " + fitness);

        System.out.println("Finished!");
    }
}
