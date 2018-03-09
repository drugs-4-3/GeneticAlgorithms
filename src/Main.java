import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {

        System.out.println("Checking for optimal solution");
        System.out.println("Using random search:");

        int iterations = 200;

        Problem p = new Problem("problems/problem.txt");


        RandomSearch rs= new RandomSearch(p, iterations);
        Solution randS = rs.getSolution();

//        Greedy greedy = new Greedy(p, iterations); // greedy from random solution
        Greedy greedy = new Greedy(p, randS, iterations); // gready optimizing previously found solution
        Solution greedySol = greedy.getSolution();

        // improved greeedy
//        Greedy greedyImp = new Greedy(p, iterations2, true); // gready optimizing previously found solution
//        Solution greedySolImp = greedyImp.getSolution();


        int pop_size = 200;
        int ga_iter = 200;
        long t1 = System.currentTimeMillis();
        GeneticAlgorithm ga = new GeneticAlgorithm(p, ga_iter, pop_size, 1, GeneticAlgorithm.SELECTION_ROULETTE);
        long t2 = System.currentTimeMillis();
        long time_exec = t2 - t1;


        System.out.printf("FINISHED! \n\n");

        System.out.printf(rs.getResultString());
        System.out.printf("\n\n");

        System.out.printf(greedy.getResultString());
        System.out.printf("\n\n");


        System.out.printf(ga.getResultString());
        System.out.printf("\n\n");

        System.out.printf("Genetic algorithm execution took " + time_exec + " miliseconds. \n");

//        System.out.printf(greedyImp.getResultString());

    }

    private static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
