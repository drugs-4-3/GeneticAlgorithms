import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class GeneticAlgorithm {

    public static final int SELECTION_ROULETTE = 1;
    public static final int SELECTION_TOURNAMENT = 2;


    private Problem problem;
    private Solution solution;

    private int iterations;
    private int pop_size;
    private int selection_type;
    private int multiplier = 1000; // arbitrary
    private int mutation_percentage;
    private int crossOverPropability;
    private Solution[] population;
    private int cur_best_fit = -1;


    public GeneticAlgorithm(Problem problem, int iterations, int pop_size, int mutation_percentage, int selection_type, int cop) {
        this.problem = problem;
        this.iterations = iterations;
        this.pop_size = pop_size;
        this.selection_type = selection_type;
        this.mutation_percentage = mutation_percentage;
        this.crossOverPropability = cop;

        BufferedWriter writer1 = null;
        BufferedWriter writer2;
        BufferedWriter writer3;

        try {
            writer1 = new BufferedWriter(new FileWriter("csv1.csv"));
            writer2 = new BufferedWriter(new FileWriter("csv2.csv"));
            writer3 = new BufferedWriter(new FileWriter("csv3.csv"));

            population = generateRandomPopulation();
            for (int i = 0; i < iterations; i++) {
                if (i%20 == 0) {
                    System.out.println(i);
                }

                population = performSelection(population);
                population = mutatePopulation(population);
                int best_fitness = getBestFitness();
                int cache_size = problem.cache.size();
                int medium_fitness = countMediumFitness() - 826;

//                if (i%100 == 0) {
//                    writer1.write(i + ";" + (best_fitness - 826) + "\n");
//                }
                writer1.write(i + ";" + (best_fitness - 826) + "\n");
                writer2.write(i + ";" + cache_size + "\n");
                writer3.write(i + ";" + medium_fitness + "\n");
//                System.out.println("Iteration: " + i + ", best_fitness: " + best_fitness + ", cache_size: " + cache_size);
            }
            writer1.close();
            writer2.close();
            writer3.close();

        } catch (IOException e) {
            e.printStackTrace();
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

        switch (this.selection_type) {
            case SELECTION_ROULETTE:
                return performRouletteSelection(solutions);
            case SELECTION_TOURNAMENT:
                return performTournamentSelection(solutions);
            default:
                return performRouletteSelection(solutions);
        }
    }

    private int getBestFitness(Solution[] solutions) {
        int best = problem.getFitness(solutions[0]);

        for (int i= 0; i < pop_size; i++) {
            int fit = problem.getFitness(solutions[i]);
            if (fit < best) {
                best = fit;
            }
        }
        return best;
    }

    private Solution[] performRouletteSelection(Solution[] solutions) {

        LinkedList<Solution> pool = new LinkedList<>();
        Solution[] result = new Solution[pop_size];

//        if (cur_best_fit == -1) {
//            cur_best_fit = getBestFitness(solutions);
//        }
//        int bestfit = cur_best_fit;


//        cur_best_fit = 820;
        for (Solution s: solutions) {

            int fitness = problem.getFitness(s);

//            if (bestfit < cur_best_fit) {
//                bestfit = fitness;
//            }(

//            fitness = fitness - cur_best_fit; // subtract optimal fitness

            double ratio = 1.0/(double)((fitness*fitness) + 1.0);
            int times_appearing = (int)(100000000.0*ratio);
            for (int i = 0; i < times_appearing; i++) {
                pool.add(s);
            }
        }

//        cur_best_fit = bestfit;

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

    private Solution[] performTournamentSelection(Solution[] solutions) {

        Solution[] newGeneration = new Solution[pop_size];
        for (int i = 0; i < pop_size; i++) {
            Solution s1 = getSolutionByTournament(pop_size/40);
            Solution s2 = getSolutionByTournament(pop_size/40);
            newGeneration[i] = combineSolutions(s1, s2);
        }
        return newGeneration;
    }

    private Solution getSolutionByTournament(int iterations) {

        Solution bestSolution = population[MyRandom.getRandomInt(0, pop_size - 1)];
        for (int i = 1; i < iterations; i++) {
            int random = MyRandom.getRandomInt(0, pop_size - 1);
            if (problem.getFitness(population[random]) < problem.getFitness(bestSolution)) {
                bestSolution = population[random];
            }
        }
        return bestSolution;
    }

    private Solution combineSolutions(Solution parent1, Solution parent2) {

        int rand = MyRandom.getRandomInt(0, 99);
        if (rand < crossOverPropability) {
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
        else {
            return new Solution(parent1.getData().clone());
        }
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

    private int getBestFitness() {
        int best_fitness = problem.getFitness(population[0]);
        for (int i = 1; i < pop_size; i++) {
            int current_fitness = problem.getFitness(population[i]);
            if (best_fitness > current_fitness) {
                best_fitness = current_fitness;
            }
        }
        return best_fitness;
    }

    private int countMediumFitness() {
        int sum = 0;
        for (int i = 0; i < pop_size; i++) {
            sum += problem.getFitness(population[i]);
        }
        return (int) ((double)(sum)/(double)(pop_size));
    }
}
