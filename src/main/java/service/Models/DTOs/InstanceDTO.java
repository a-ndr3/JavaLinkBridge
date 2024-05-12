package service.Models.DTOs;

public class InstanceDTO extends BaseDTO {

    public InstanceDTO(Long id) {
        super(id);
    }

    public InstanceDTO(Long id, String name) {
        super(id,name);
    }
}
