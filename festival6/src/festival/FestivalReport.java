package festival;
import java.util.List;
import java.util.Locale;
public final class FestivalReport {
    public void print(List<RankedMovie> rows, String ruleName) {
        System.out.println("Правило: " + ruleName);
        System.out.println("Фильмы без оценок не участвуют в рейтинге");
        if (rows.isEmpty()) System.out.println("Пока нет оценённых фильмов");
        for (RankedMovie row : rows)
            System.out.printf(Locale.ROOT, "%d | %s | %.2f%n",
                row.movie().getId(), row.movie().getTitle(), row.score());
    }
}
