package festival;
public interface RankingStrategy {
    double score(Movie movie);
    String name();
}
