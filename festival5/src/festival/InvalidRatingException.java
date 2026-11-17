package festival;
public class InvalidRatingException extends Exception {
    private static final long serialVersionUID = 1L;
    public InvalidRatingException(int rating) {
        super("Оценка должна быть от 1 до 10: " + rating);
    }
}
