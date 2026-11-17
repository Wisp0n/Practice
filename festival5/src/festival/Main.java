package festival;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
public final class Main {
    private Main() {}
    public static void main(String[] args) {
        FestivalCatalog catalog = SampleData.create();
        Charset inputCharset = Charset.forName(System.getProperty("stdin.encoding", Charset.defaultCharset().name()));
        try (Scanner input = new Scanner(System.in, inputCharset)) {
            while (true) {
                System.out.println("1 Каталог | 2 Поиск | 3 Длительность | 4 Оценка | 0 Выход");
                if (!input.hasNextLine()) return;
                String action = input.nextLine().strip();
                try {
                    switch (action) {
                        case "0" -> { return; }
                        case "1" -> show(catalog.getMovies());
                        case "2" -> {
                            System.out.println("Часть названия:");
                            if (!input.hasNextLine()) return;
                            show(catalog.searchByTitle(input.nextLine()));
                        }
                        case "3" -> show(catalog.sortedByDuration());
                        case "4" -> {
                            int id = readInt(input, "ID фильма:");
                            int rating = readInt(input, "Оценка 1..10:");
                            handleRating(catalog, id, rating);
                        }

                        default -> System.out.println("Неизвестная команда");
                    }
                } catch (UnsupportedOperationException e) {
                    System.out.println(e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка ввода: " + e.getMessage());
                }
            }
        }
    }
    private static int readInt(Scanner input, String prompt) {
        System.out.println(prompt);
        if (!input.hasNextLine()) throw new IllegalArgumentException("Ввод завершён");
        try { return Integer.parseInt(input.nextLine().strip()); }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Нужно целое число");
        }
    }
    public static void handleRating(FestivalCatalog catalog, int id, int rating) {
        try {
            catalog.addRating(id, rating);
            System.out.println("Оценка добавлена");
        } catch (InvalidRatingException e) {
            System.out.println("Оценка отклонена: " + e.getMessage());
        }
    }
    public static void show(List<Movie> movies) {
        if (movies.isEmpty()) System.out.println("Фильмы не найдены");
        for (Movie movie : movies)
            System.out.printf(Locale.ROOT, "%d | %s | %d мин | %s%n",
                movie.getId(), movie.getTitle(), movie.getDuration(),
                movie.hasRatings() ? String.format(Locale.ROOT, "%.2f", movie.averageRating()) : "нет оценок");
    }
}
