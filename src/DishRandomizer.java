import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DishRandomizer {
    // Dish cool down currently set to 7 days.
    private static final long COOL_DOWN = 604800000;

    private static final String FILE_PATH = "./data/Dishes.csv";

    public static class Dish {
        String name;
        long lastTimeStamp;
        int position;

        public Dish(String name, long lastTimeStamp, int position) {
            this.name = name;
            this.lastTimeStamp = lastTimeStamp;
            this.position = position;
        }

        public static List<Dish> fromLines(List<String> lines) {
            List<Dish> dishes = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                String[] line = lines.get(i).split(",");
                dishes.add(new Dish(line[0], Long.valueOf(line[1]), i));
            }
            return dishes;
        }
    }

    public static void main (String[] args) {
        List<String> lines = readLines(FILE_PATH);
        List<Dish> dishes = Dish.fromLines(lines).stream()
                .filter(a->System.currentTimeMillis() - a.lastTimeStamp > COOL_DOWN).collect(Collectors.toList());

        if(dishes.isEmpty()) {
            System.out.println("We run out of dishes :(");
            return;
        }

        Random rand = new Random();
        Dish dish = dishes.get(rand.nextInt(dishes.size()));

        System.out.println(String.format("Today we're going to have %s!", dish.name));

        lines.set(dish.position, dish.name+","+System.currentTimeMillis());

        updateFile(FILE_PATH, lines);
    }

    private static void updateFile(String fileName, List<String> lines) {
        Path path = Paths.get(fileName);
        Charset charset = Charset.forName("UTF-8");

        try {
            Files.write(path, lines, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
