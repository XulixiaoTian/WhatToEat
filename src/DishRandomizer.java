import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DishRandomizer {
    public class Dish {
        String name;
        long lastMade;
        long position;
        public Dish (String name, long lastMade, long position) {
            this.name = name;
            this.lastMade = lastMade;
            this.position = position;
        }
    }

    public static void main (String[] args) {
        List<String> dishes = readLines("./data/Dishes.csv");

        Random rand = new Random();
        int value = rand.nextInt(dishes.size());

        System.out.println(String.format("Today we're going to have %s !", dishes.get(value)));
    }


    private static List<String> readLines (String fileName) {
        Path path = Paths.get(fileName);
        Charset charset = Charset.forName("UTF-8");

        List<String> lines = new ArrayList<>();

        try {
            lines = Files.readAllLines(path, charset);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

}
