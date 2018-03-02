import java.util.concurrent.ThreadLocalRandom;

public class Greedy {

    private Problem problem;
    private int iterations;
    private Solution solution;

    public Greedy(Problem problem, int iterations) {
        this.problem = problem;
        this.iterations = iterations;

        solution = Solution.getRandomSolution(problem.getDimension());
        Solution solution2;

        for (int i = 0; i < iterations; i++) {
            int fitness1 = problem.getFitness(solution);
            solution2 = new Solution(swap(
                    solution.getData(),
                    getRandomInt(0, solution.getData().length - 1),
                    getRandomInt(0, solution.getData().length - 1)));

            int fitness2 = problem.getFitness(solution2);
            if (fitness2 < fitness1) {
                solution = solution2;
            }
        }
    }

    public Greedy(Problem p, Solution s, int iterations) {
        this.problem = p;
        this.solution = s;
        this.iterations = iterations;


        Solution solution2;
        for (int i = 0; i < iterations; i++) {
            int fitness1 = problem.getFitness(solution);
            solution2 = new Solution(swap(
                    solution.getData(),
                    getRandomInt(0, solution.getData().length - 1),
                    getRandomInt(0, solution.getData().length - 1)));

            int fitness2 = problem.getFitness(solution2);
            if (fitness2 < fitness1) {
                solution = solution2;
            }
        }
    }

    public Greedy(Problem p, int iterations, boolean better) {
        this.problem = p;
        this.iterations = iterations;

        solution = Solution.getRandomSolution(problem.getDimension());
        int fitness = p.getFitness(solution);
        Solution solution2;

        int dim = p.getDimension();

        for (int j = 0; j < dim; j++) {
            for (int k = j + 1; k < dim; k++) {
                solution2 = new Solution(swap(solution.getData(), j, k));
                int fit = p.getFitness(solution2);
                if (fit < fitness) {
                    solution = solution2;
                    j = 0;
                    k = j+1;
                }
            }
        }
//
//        for (int i = 0; i < iterations; i++) {
//
//            for (int j = 0; j < dim; j++) {
//                for (int k = j + 1; k < dim; k++) {
//                    solution2 = new Solution(swap(solution.getData(), j, k));
//                    int fit = p.getFitness(solution2);
//                    if (fit < fitness) {
//                        solution = solution2;
//                        j = 0;
//                        k = j+1;
//                    }
//                }
//            }
//        }
    }

    public static  int[] swap(int[] data, int index1, int index2) {


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

    private static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public Problem getProblem() {
        return problem;
    }

    public Solution getSolution()
    {
        return this.solution;
    }

}
