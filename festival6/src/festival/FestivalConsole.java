package festival;
import java.util.List;
public final class FestivalConsole {
    public void run(FestivalCatalog catalog, boolean best) {
        RankingStrategy strategy = best ? new BestRatingStrategy() : new AverageRatingStrategy();
        RankingService service = new RankingService(strategy);
        printRanking(service.rank(catalog.getMovies()), service.ruleName());
    }
    public void printRanking(List<RankedMovie> rows, String ruleName) {
        new FestivalReport().print(rows, ruleName);
    }
}
