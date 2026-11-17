package festival;
import java.util.List;
public final class SampleData {
    private SampleData() {}
    public static FestivalCatalog create() {
        return new FestivalCatalog(List.of(
            new Movie(3, "Тихий берег", 90, List.of(7, 7)),
            new Movie(1, "Северный свет", 120, List.of(8, 8)),
            new Movie(6, "Письмо домой", 7, List.of()),
            new Movie(2, "Последний трамвай", 20, List.of(10, 2)),
            new Movie(5, "Летний дождь", 90, List.of(9, 3)),
            new Movie(4, "Рокки Бальбоа", 120, List.of(8, 6))
        ));
    }
}
