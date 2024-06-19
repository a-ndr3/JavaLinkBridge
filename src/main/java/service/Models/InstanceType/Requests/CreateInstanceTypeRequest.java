package service.Models.InstanceType.Requests;

public record CreateInstanceTypeRequest(Long parentInstanceId, String name) {
}
