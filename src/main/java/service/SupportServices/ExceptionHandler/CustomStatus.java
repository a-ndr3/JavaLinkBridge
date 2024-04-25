package service.SupportServices.ExceptionHandler;

public enum CustomStatus {
    JavaClassNotFound(460),
    JsonMappingWritingException(461),
    JsonMappingReadingException(462),
    DesignSpaceIsNotReachable(503);

    private final int statusCode;

    CustomStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
