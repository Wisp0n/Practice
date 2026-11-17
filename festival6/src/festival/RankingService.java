package festival;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public final class RankingService {
    private final RankingStrategy strategy;
    public RankingService(RankingStrategy strategy) {
        this.strategy = java.util.Objects.requireNonNull(strategy);
    }
    public String ruleName() { return strategy.name(); }
    public List<RankedMovie> rank(List<Movie> movies) {
        List<RankedMovie> rows = new ArrayList<>();
        for (Movie movie : movies) {
            if (!movie.hasRatings()) {
                continue;
            }
            rows.add(new RankedMovie(movie, strategy.score(movie))); 
        }
        rows.sort(Comparator.comparingDouble(RankedMovie::score).reversed()
                .thenComparingInt(row -> row.movie().getId()));
        return rows;
    }
}
