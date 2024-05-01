package service.Models.InstanceType.InstanceTypeController.Requests;

public record CreateInstanceTypeRequest(Long parentInstanceId, String name) {
}
