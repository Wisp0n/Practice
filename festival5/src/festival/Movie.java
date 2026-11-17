package festival;
import java.util.ArrayList;
import java.util.List;
public final class Movie {
    private final int id;
    private final String title;
    private final int duration;
    private final List<Integer> ratings = new ArrayList<>();
    public Movie(int id, String title, int duration, List<Integer> initial) {
        if (id <= 0 || title == null || title.isBlank() || duration <= 0)
            throw new IllegalArgumentException("Некорректный фильм");
        for (int rating : initial)
            if (rating < 1 || rating > 10)
                throw new IllegalArgumentException("Некорректные начальные оценки");
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.ratings.addAll(initial);
    }
    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getDuration() { return duration; }
    public List<Integer> getRatings() { return List.copyOf(ratings); }
    public boolean hasRatings() { return !ratings.isEmpty(); }
    public double averageRating() {
        if (ratings.isEmpty()) return 0.0; 
        int sum = 0;
        for (int rating : ratings) {
            sum += rating;
        }
        return (double) sum / ratings.size();
    }
    public void addRating(int rating) throws InvalidRatingException {
        if (rating < 1 || rating > 10) {
            throw new InvalidRatingException(rating); 
        }
        ratings.add(rating);
    }
}
