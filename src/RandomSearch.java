public class RandomSearch {

    private Problem problem;
    private int iterations;
    private Solution solution;

    public RandomSearch(Problem problem, int iterations) {
        this.problem = problem;
        this.iterations = iterations;


        // generating solution
        solution = Solution.getRandomSolution(problem.getDimension());
        Solution bestSolution = solution;
        int bestFitness = problem.getFitness(solution);

        for (int i = 0; i < iterations; i++) {
            solution = Solution.getRandomSolution(problem.getDimension());
            int fitness = problem.getFitness(solution);
            if (fitness == -1) {
                System.out.println("ERROR!!!");
            }
            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestSolution = solution;
            }
            solution = bestSolution;
        }
    }

    public Problem getProblem() {
        return problem;
    }

    public Solution getSolution()
    {
        return this.solution;
    }

    public String getResultString() {
        StringBuilder result = new StringBuilder();
        result.append("RANDOM SOLUTION: \n");
        for(int i = 0; i < getProblem().getDimension(); i++) {
            result.append(getSolution().getData()[i] + " ");
        }
        result.append("\n ");
        result.append("RANDOM SOLUTION FITNESS: " + getProblem().getFitness(getSolution()));
        return result.toString();
    }
}
