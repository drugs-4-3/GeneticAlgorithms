public class Main {

    public static void main(String[] args) {

        System.out.println("Checking for optimal solution");
        System.out.println("Using random search:");

        int iterations = 1000000;

        Problem p = new Problem("problems/problem.txt");
        RandomSearch rs= new RandomSearch(p, iterations);
        Solution randS = rs.getSolution();

        System.out.printf("FINISHED! \n");

        System.out.printf("BEST SOLUTION: \n");
        for(int i = 0; i < p.getDimension(); i++) {
            System.out.printf(randS.getData()[i] + " ");
        }
        System.out.printf("\n");
        System.out.printf("FITNESS: " + p.getFitness(randS));
    }
}
