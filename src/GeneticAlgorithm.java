import java.util.LinkedList;

public class GeneticAlgorithm {

    public static int SELECTION_ROULETTE = 1;
    public static int SELECTION_TOURNAMENT = 2;


    private Problem problem;
    private Solution solution;

    private int iterations;
    private int pop_size;
    private int selection_type;
    private int multiplier = 1000; // arbitrary
    private int mutation_percentage;
    private Solution[] population;


    public GeneticAlgorithm(Problem problem, int iterations, int pop_size, int mutation_percentage, int selection_type) {
        this.problem = problem;
        this.iterations = iterations;
        this.pop_size = pop_size;
        this.selection_type = selection_type;
        this.mutation_percentage = mutation_percentage;

        population = generateRandomPopulation();
        for (int i = 0; i < iterations; i++) {
            population = performRouletteSelection(population);
            population = mutatePopulation(population);
        }

        int best_index = 0;
        int best_fitness = problem.getFitness(population[best_index]);

        for (int i = 0; i < pop_size; i++) {
            int cur_fitness = problem.getFitness(population[i]);
            if (cur_fitness < best_fitness) {
                best_index = i;
                best_fitness = cur_fitness;
            }
        }
        solution = population[best_index];

        System.out.println("best index: " + best_index);
    }

    private Solution[] generateRandomPopulation() {

        Solution[] pop = new Solution[pop_size];
        for (int i = 0; i < pop_size; i++) {
            pop[i] = Solution.getRandomSolution(problem.getDimension());
        }
        return pop;
    }

    private Solution[] performSelection(Solution[] solutions) {

        if (this.selection_type == SELECTION_ROULETTE) {
            return performRouletteSelection(solutions);
        } else  {
            return performRouletteSelection(solutions);
        }
    }

    private Solution[] performRouletteSelection(Solution[] solutions) {

        LinkedList<Solution> pool = new LinkedList<>();
        Solution[] result = new Solution[pop_size];

        for (Solution s: solutions) {
            if (problem.getFitness(s) == 0) {
                System.out.println("tutaj");
            }
            int times_appearing = (multiplier*100/problem.getFitness(s));
            for (int i = 0; i < times_appearing; i++) {
                pool.add(s);
            }
        }
        int length = pool.size();

        for (int i = 0; i < pop_size; i++) {
            int random1 = MyRandom.getRandomInt(0, length - 1);
            int random2 = MyRandom.getRandomInt(0, length - 1);
            Solution parent1 = pool.get(random1);
            Solution parent2 = pool.get(random2);
            result[i] = combineSolutions(parent1, parent2);
        }
        return result;
    }

    private Solution combineSolutions(Solution parent1, Solution parent2) {
        int dim = parent1.getDimension();
        int border = MyRandom.getRandomInt(0, dim - 1);
        int[] child_data = new int[dim];
        for (int i = 0; i < border; i++) {
            child_data[i] = parent1.getData()[i];
        }
        for (int i = border; i < dim; i++) {
            child_data[i] = parent2.getData()[i];
        }
        Solution result = new Solution(child_data);
        result.reapir();
        return result;
    }

    private Solution[] mutatePopulation(Solution[] solutions) {

        for (Solution s: solutions) {
            int rand = MyRandom.getRandomInt(0, 99);
            if (rand < mutation_percentage) {
                s.mutate();
            }
        }
        return solutions;
    }

    public Solution getSolution() {
        return this.solution;
    }

    public Problem getProblem() {
        return this.problem;
    }

    public String getResultString() {
        StringBuffer result = new StringBuffer();
        result.append("GENETIC ALGORITHM SOLUTION: \n");
        for(int i = 0; i < getProblem().getDimension(); i++) {
            result.append(getSolution().getData()[i] + " ");
        }
        result.append("\n ");
        result.append("GENETIC ALGORITHM SOLUTION FITNESS: " + getProblem().getFitness(getSolution()));
        return result.toString();
    }
}
