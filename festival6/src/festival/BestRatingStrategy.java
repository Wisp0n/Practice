package festival;
public final class BestRatingStrategy implements RankingStrategy {
    @Override public double score(Movie movie) {
        int best = 0; 
        for (int rating : movie.getRatings()) {
            if (rating > best) {
                best = rating;
            }
        }
        return best;
    }
    @Override public String name() { return "Лучшая оценка"; }
}
