package pl.micede.personalapi.utils.exception;

public class HabitNotFoundException extends RuntimeException{
    public HabitNotFoundException(String message) {
        super(message);
    }
}
