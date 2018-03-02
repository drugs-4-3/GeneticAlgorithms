public class Main {

    public static void main(String[] args) {

        System.out.println("Checking for optimal solution");
        System.out.println("Using random search:");

        int iterations = 1000000;

        Problem p = new Problem("problems/problem.txt");

        Solution s = Solution.getRandomSolution(p.getDimension());
        Solution bestSolution = s;
        int bestFitness = p.getFitness(s);

        for (int i = 0; i < iterations; i++) {
            s = Solution.getRandomSolution(p.getDimension());
            int fitness = p.getFitness(s);
            if (fitness == -1) {
                System.out.println("ERROR!!!");
            }
            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestSolution = s;
            }
        }

        System.out.printf("FINISHED! \n");

        System.out.printf("BEST SOLUTION: \n");
        for(int i = 0; i < p.getDimension(); i++) {
            System.out.printf(s.getData()[i] + " ");
        }
        System.out.printf("\n");
        System.out.printf("FITNESS: " + bestFitness);
    }
}
