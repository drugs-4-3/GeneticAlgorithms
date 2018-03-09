import java.util.Random;

public class MyRandom {

    private static Random random = new Random(123123123);


    public static int getRandomInt(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }
}
