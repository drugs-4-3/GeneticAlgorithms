import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {

//        int dim = 5;
//        int [] rand = Solution.getRandIntArr(dim);
//        int[] rand2 = Greedy.swap(rand, getRandomInt(0, dim - 1), getRandomInt(0, dim - 1));
//
//        for (int i = 0; i < dim; i++) {
//            System.out.printf(rand[i] + " ");
//        }
//        System.out.printf("\n\n");
//        for (int i = 0; i < dim; i++) {
//            System.out.printf(rand2[i] + " ");
//        }

        System.out.println("Checking for optimal solution");
        System.out.println("Using random search:");

        int iterations = 50;
        int iterations2 = 10;

        Problem p = new Problem("problems/problem.txt");
        RandomSearch rs= new RandomSearch(p, iterations);
        Solution randS = rs.getSolution();

        //Greedy greedy = new Greedy(p, iterations); // greedy from random solution
        Greedy greedy = new Greedy(p, randS, iterations); // gready optimizing previously found solution
        Solution greedySol = greedy.getSolution();

        // improved greeedy
        Greedy greedyImp = new Greedy(p, iterations2, true); // gready optimizing previously found solution
        Solution greedySolImp = greedyImp.getSolution();


        System.out.printf("FINISHED! \n\n");

        System.out.printf("RANDOM SOLUTION: \n");
        for(int i = 0; i < p.getDimension(); i++) {
            System.out.printf(randS.getData()[i] + " ");
        }
        System.out.printf("\n");
        System.out.printf("RANDOM SOLUTION FITNESS: " + p.getFitness(randS));

        System.out.printf("\n\n");

        System.out.printf("GREEDY SOLUTION: \n");
        for(int i = 0; i < p.getDimension(); i++) {
            System.out.printf(greedySol.getData()[i] + " ");
        }
        System.out.printf("\n");
        System.out.printf("GREEDY SOLUTION FITNESS: " + p.getFitness(greedySol));

        System.out.printf("GREEDY IMPROVED SOLUTION: \n");
        for(int i = 0; i < p.getDimension(); i++) {
            System.out.printf(greedySolImp.getData()[i] + " ");
        }
        System.out.printf("\n");
        System.out.printf("GREEDY IMPROVED SOLUTION FITNESS: " + p.getFitness(greedySolImp));
    }

    private static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
