package festival;
public final class AverageRatingStrategy implements RankingStrategy {
    @Override public double score(Movie movie) { return movie.averageRating(); }
    @Override public String name() { return "Средняя оценка"; }
}
