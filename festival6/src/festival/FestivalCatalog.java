package festival;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
public final class FestivalCatalog {
    private final List<Movie> movies;
    public FestivalCatalog(List<Movie> movies) {
        this.movies = new ArrayList<>(movies);
        if (this.movies.stream().map(Movie::getId).distinct().count() != movies.size())
            throw new IllegalArgumentException("Повторный ID");
    }
    public List<Movie> getMovies() { return List.copyOf(movies); }
    public Movie findById(int id) {
        for (Movie movie : movies) if (movie.getId() == id) return movie;
        throw new IllegalArgumentException("Нет фильма с ID " + id);
    }
    public List<Movie> searchByTitle(String query) {
        String needle = query.strip().toLowerCase(Locale.ROOT);
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies)
            if (movie.getTitle().toLowerCase(Locale.ROOT).contains(needle))
                result.add(movie);
        return result;
    }
    public List<Movie> sortedByDuration() {
        List<Movie> result = new ArrayList<>(movies);
        result.sort(Comparator.comparingInt(Movie::getDuration)
                .thenComparingInt(Movie::getId));
        return result;
    }
    public void addRating(int id, int rating) throws InvalidRatingException {
        findById(id).addRating(rating);
    }
}
