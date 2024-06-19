package service.SupportServices.ExceptionHandler;

public enum CustomStatus {

    Success(201),
    JavaClassNotFound(460),
    JsonMappingWritingException(461),
    JsonMappingReadingException(462),
    DesignSpaceIsNotReachable(503),
    ErrorWhileCreatingInstance(463),
    ErrorWhileUpdatingInstance(465),
    ErrorWhileDeletingInstance(464),
    ErrorWhileExecutingServerCommand(466),
    ErrorWhileGettingData(467), ErrorWhileDeletingPropertyType(468), ErrorWhileDeletingInstanceType(469);

    private final int statusCode;

    CustomStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
