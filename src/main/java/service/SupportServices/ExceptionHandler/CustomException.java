package service.SupportServices.ExceptionHandler;

public class CustomException extends RuntimeException {
    private final CustomStatus customStatus;

    public CustomException(String message, CustomStatus customStatus) {
        super(message);
        this.customStatus = customStatus;
    }

    public CustomStatus getCustomStatus() {
        return customStatus;
    }
}
