import java.util.Random;

class Randomizer {
    public static void main(String[] args) {
        Random random = new Random();
        int number = random.nextInt(4) + 1; 
        System.out.println(number);
    }
}
